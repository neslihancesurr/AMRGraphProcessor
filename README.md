# Custom AMR Graph Builder

This Java project is designed to construct **Abstract Meaning Representation (AMR) Graphs** by parsing a specific, custom "Penman-like" notation.

Unlike standard Penman notation (which uses parentheses for nesting), this project is designed to process data exported from **Google Sheets to CSV**. It relies on **indentation levels** (represented by empty CSV cells) to determine the parent-child relationships between concepts.

## 📂 Project Structure

* **`AMRGraph.java`**: The main data structure representing a complete graph (sentence). It holds a map of Nodes to Edges and allows for deep cloning.
* **`AMRNode.java`**: A Java Record representing a single concept (e.g., "run", "boy").
* **`AMREdge.java`**: Represents a directed edge between nodes, containing the target node and the relation label (e.g., "ARG0", "frequency").
* **`IndentNode.java`**: An intermediate helper record. It captures the raw data from the CSV (Name, Relation, and Indentation Depth) before the graph is constructed.
* **`FileReader.java`**: The core parsing logic. It reads the CSV, calculates indentation based on empty delimiters, and uses a stack-like logic to reconstruct the tree structure.
* **`Main.java`**: Entry point for testing manual graph creation or running the file reader.

## 📝 Input Format (The "Google Sheets" Notation)

The parser expects a **CSV file** (exported from Google Sheets). The logic separates graphs by headers and determines hierarchy via column indentation.

### 1. Header Line
Each new graph must start with a line containing the ID (ending in `.train` or `.test`) and the full sentence.
* **Format:** `ID, SENTENCE`
* **Example:** `0463.train, SÜREKLİ İLGİLENDİ .`

### 2. Node Lines
The lines following the header represent the nodes. The hierarchy is defined by how many empty commas precede the data.
* **Syntax:** `Concept:Relation` (The parser splits the string by `:`).
* **Logic:**
    * **Level 0:** The root of the graph.
    * **Level 1:** Direct children of the root.
    * **Level 2:** Children of the immediate Level 1 parent.

### Example CSV Structure

**Raw CSV View:**
```csv
0463.train, SÜREKLİ İLGİLENDİ .
2/ilgilendi,
,1/sürekli:frequency
,o:ARG0
```
### Spreadsheet View (Visual)

| Column A (ID/Root) | Column B (Lvl 1) | Column C (Lvl 2) | Note |
| :--- | :--- | :--- | :--- |
| **0463.train** | **SÜREKLİ İLGİLENDİ .** | | *Header Line* |
| `2/ilgilendi` | | | *Root Node (Indent 0)* |
| | `1/sürekli:frequency` | | *Child of ilgilendi (Indent 1)* |
| | `o:ARG0` | | *Child of ilgilendi (Indent 1)* |

**Resulting Logic:**
1.  The parser reads `2/ilgilendi` at **Indent 0**.
2.  It reads `1/sürekli` at **Indent 1** with relation `frequency`. It links `ilgilendi` -> `frequency` -> `sürekli`.
3.  It reads `o` at **Indent 1** with relation `ARG0`. It links `ilgilendi` -> `ARG0` -> `o`.

## ⚙️ How It Works (Parsing Logic)

The `FileReader.java` uses a depth-based reconstruction algorithm (`buildGraphNodes`):

1.  **Intermediate Parsing:** It reads the CSV line by line. It counts the empty strings in the split array to determine the `indent` integer. It parses the text to separate the **Concept** from the **Relation** (split by `:`) into an `IndentNode`.
2.  **Graph Construction:**
    * It iterates through the list of `IndentNodes`.
    * If the `nextNode` has an indent exactly **+1** greater than the `currentNode`, a direct edge is added.
    * If the `nextNode` has the same or smaller indent, the algorithm backtracks up the list to find the nearest node with `indent == nextNode.indent - 1` to find the correct parent.

## 🚀 Usage

### 1. Preparing the Data
Ensure your `.csv` file is located in the accessible directory (default in code is `files/amrtest7.csv`).

### 2. Running the Code
You can run the `FileReader` main method to parse a CSV and print the resulting graph structures to the console.

```java
// In FileReader.java
public static void main(String[] args) throws IOException {
    String csvFile = "files/amrtest7.csv";
    ArrayList<AMRGraph> graphs = processCSVFile(csvFile);
    for (AMRGraph graph : graphs) {
        graph.printGraph();
    }
}
