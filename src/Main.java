import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // Read line
        // if it starts with number plus train etc. -> get the part before the comma and keep it the index variable
        // then, get the part after the comma and store it in the sentence variable.
        // move on to next line

        AMRGraph graph1 = new AMRGraph("0463.train", "SÜREKLİ İLGİLENDİ .");

        graph1.addEdge("2/ilgilendi", "1/sürekli", "frequency");
        graph1.addEdge("2/ilgilendi", "o", "ARG0");


        Map<String, AMRNode> nodesMap = graph1.nodes;

        graph1.printGraph();

        nodesMap.forEach((key, value) -> System.out.println(key + " => " + value));


    }
}