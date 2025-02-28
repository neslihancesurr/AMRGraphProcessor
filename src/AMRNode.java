public class AMRNode {
    String entity;

    public AMRNode(String entity){
        this.entity = entity;
    }

    @Override
    public String toString() {
        return entity;
    }
}
