import java.util.*;

public class AMRGraph {

    private final String index;
    private final String sentence;
    private final HashMap<AMRNode, ArrayList<AMREdge>> nodes;

    public AMRGraph(String index, String sentence) {
        this.nodes = new HashMap<>();
        this.index = index;
        this.sentence = sentence;
    }

    public AMRGraph(String index, String sentence, HashMap<AMRNode, ArrayList<AMREdge>> nodes) {
        this.nodes = nodes;
        this.index = index;
        this.sentence = sentence;
    }

    public String getIndex() {
        return index;
    }

    public String getSentence() {
        return sentence;
    }

    public void addEdge(String fromEntity, String toEntity, String relation) {
        AMRNode from = new AMRNode(fromEntity);
        if (!nodes.containsKey(from)) {
            nodes.put(from, new ArrayList<>());
        }
        AMRNode to = new AMRNode(toEntity);
        nodes.get(from).add(new AMREdge(to, relation));
    }

    public void printGraph() {
        System.out.println("Index: " + this.index);
        System.out.println("Sentence: " + this.sentence);
        for (AMRNode node : nodes.keySet()) {
            System.out.println(node + "'s children:");
            for (AMREdge edge : nodes.get(node)) {
                System.out.println(edge);
            }
        }
    }

    @Override
    protected AMRGraph clone() {
        return new AMRGraph(index, sentence, (HashMap<AMRNode, ArrayList<AMREdge>>) nodes.clone());
    }
}
