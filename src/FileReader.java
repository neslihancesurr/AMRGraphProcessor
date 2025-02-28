import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static void main(String[] args) throws IOException {
        String csvFile = "src/Amrtest.csv";
        String line;
        List<AMRGraph> allGraphs = new ArrayList<>();
        AMRGraph graph = null;
        List<IndentNode> indentNodes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(csvFile))){

            while ((line = br.readLine()) != null){
                line = line.trim();
                String[] parts = line.split(",");

               if (parts.length != 0){
                   if (line.contains(".train")){
                    graph = new AMRGraph();
                    String index = parts[0].trim();
                    String sentence = parts[1].trim();
                    graph.setIndex(index);
                    graph.setSentence(sentence);
                } else if (graph != null) {
                    parseIndentNodes(parts, indentNodes);
                }
                   continue;
               }

               if (graph != null){
                   buildGraphNodes(indentNodes, graph);
                   graph.printGraph();
                   allGraphs.add(graph);
                   graph = null; // Reset for next sentence
                   indentNodes = new ArrayList<>();
                   System.out.println("empty");
                   System.out.println("------------");
                }
            }
        }
    }

   private static void buildGraphNodes(List<IndentNode> indentNodes, AMRGraph graph){
       graph.addNode(indentNodes.get(0).name);

        int currentIndex = 0;
        int nextIndex = 1;
       while (nextIndex < indentNodes.size()) {
           IndentNode currentNode = indentNodes.get(currentIndex);
           IndentNode nextNode = indentNodes.get(nextIndex);


           if (nextNode.indent == currentNode.indent+1){
               graph.addEdge(currentNode.name, nextNode.name, nextNode.relation);
           } else if ((nextNode.indent == currentNode.indent) || (nextNode.indent < currentNode.indent)) {
               int tmpIndex = nextIndex-1;
               while (indentNodes.get(tmpIndex).indent != nextNode.indent-1){
                   tmpIndex--;
               }
               //tmpIndex is the parent of the node
               graph.addEdge(indentNodes.get(tmpIndex).name, nextNode.name, nextNode.relation);
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
                    indentNodes.add(new IndentNode(sep[0], sep[1], indentCount));
                } else {
                    indentNodes.add(new IndentNode(word, "no relation", indentCount));
                }
                index++;
            }
        }
    }


}
