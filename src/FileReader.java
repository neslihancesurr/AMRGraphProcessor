import java.io.*;
import java.util.*;

public class FileReader {

    public static ArrayList<AMRGraph> processCSVFile(String csvFile) throws IOException {
        String line;
        ArrayList<AMRGraph> allGraphs = new ArrayList<>();
        AMRGraph graph = null;
        ArrayList<IndentNode> indentNodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");
                if (parts.length != 0) {
                    if (line.contains(".train") || line.contains(".test")) {
                        if (!indentNodes.isEmpty()) {
                            try {
                                buildGraphNodes(indentNodes, graph);
                                //graph.printGraph();
                                allGraphs.add(graph.clone());
                                //System.out.println("empty");
                                //System.out.println("------------");
                            } catch (Exception e) {
                                System.out.println(graph + " not done.");
                            }
                        }
                        indentNodes = new ArrayList<>();
                        graph = new AMRGraph(parts[0].trim(), parts[1].trim());
                    } else if (graph != null) {
                        parseIndentNodes(parts, indentNodes);
                    }
                }
            }
        }
        return allGraphs;
    }


    private static void buildGraphNodes(List<IndentNode> indentNodes, AMRGraph graph) {
        int currentIndex = 0;
        int nextIndex = 1;
       while (nextIndex < indentNodes.size()) {
           IndentNode currentNode = indentNodes.get(currentIndex);
           IndentNode nextNode = indentNodes.get(nextIndex);
           if (nextNode.indent() == currentNode.indent() + 1) {
               graph.addEdge(currentNode.name(), nextNode.name(), nextNode.relation());
           } else if ((nextNode.indent() == currentNode.indent()) || (nextNode.indent() < currentNode.indent())) {
               int tmpIndex = nextIndex - 1;
               while (indentNodes.get(tmpIndex).indent() != nextNode.indent() - 1){
                   tmpIndex--;
               }
               //tmpIndex is the parent of the node
               graph.addEdge(indentNodes.get(tmpIndex).name(), nextNode.name(), nextNode.relation());
           }
           currentIndex++;
           nextIndex++;
       }
   }

    private static void parseIndentNodes(String[] parts, List<IndentNode> indentNodes ) {
        int index = 0;
        while (index < parts.length) {
            int indentCount = 0;
            while (index < parts.length && parts[index].isEmpty()) {
                indentCount++;
                index++;
            }
            if (index < parts.length) {
                String word = parts[index];
                if (word.contains(":")) {
                    String[] sep = word.split(":");
                    String name = sep.length > 0 ? sep[0] : "unknown";
                    String relation = sep.length > 1 ? sep[1] : "no relation";
                    indentNodes.add(new IndentNode(name, relation, indentCount));
                } else {
                    indentNodes.add(new IndentNode(word, "no relation", indentCount));
                }
                index++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String csvFile = "files/amrtest7.csv";
        ArrayList<AMRGraph> graphs = processCSVFile(csvFile);
        for (AMRGraph graph : graphs) {
            graph.printGraph();
        }
    }

    /**private static void findUnannotatedSentences(String csvFile) throws IOException {
        List<IndentNode> indentNodes = new ArrayList<>();
        String line;
        String title = " ";
        int totalCount = 0;

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");

                if (parts.length != 0) {
                    if (line.contains(".train")) {
                        title = parts[0] + " " + parts[1];
                    } else {
                        parseIndentNodes(parts, indentNodes);
                    }
                    continue;
                }

                int count = 0;

                for (IndentNode node: indentNodes) {
                    if (node.indent != 0 && Objects.equals(node.relation, "no relation")){
                        System.out.println(node.name + " has no relation");
                        count++;
                   }
                }
                if (count > 0){
                    System.out.println(count + " total unmarked words in sentence");
                    System.out.println(title);
                    System.out.println("-----------");
                    totalCount++;
                    }

                indentNodes = new ArrayList<>();
            }

            System.out.println("Total count of unannotated sentences: " + totalCount);
        }
    }**/
}
     // If indent is not zero and it has no relation, print as error


//Bir line'da birden çok kelime var mı?
//Indentation sıralaması mantıklı mı? Stack parantez örneğindeki gibi




