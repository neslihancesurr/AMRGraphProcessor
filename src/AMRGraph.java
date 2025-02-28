import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AMRGraph {
    String index;
    String sentence;
    Map<String, AMRNode> nodes;
    List<AMREdge> relationEdges;

    public AMRGraph(){
        this.nodes = new HashMap<>();
        this.relationEdges = new LinkedList<>();
        this.index = "not set";
        this.sentence = "not set";

    }
    public AMRGraph(String index ,String sentence){
        this.nodes = new HashMap<>();
        this.relationEdges = new LinkedList<>();
        this.index = index;
        this.sentence = sentence;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    public void setSentence(String sentence){
        this.sentence = sentence;
    }
    public AMRNode addNode(String entity){
        nodes.putIfAbsent(entity, new AMRNode(entity));
        return nodes.get(entity);
    }
    public void addEdge(String fromEntity, String toEntity, String relation){
        AMRNode from = addNode(fromEntity);
        AMRNode to = addNode(toEntity);
        relationEdges.add(new AMREdge(relation, from, to));
    }

    public void printGraph(){
        System.out.println("Index: " + index);
        System.out.println("Sentence: " + sentence);

        for (AMREdge edge: relationEdges) {
            System.out.println(edge);
        }

    }
}
