public class AMREdge {

    AMRNode from;
    AMRNode to;
    String relation;


    public AMREdge(String relation, AMRNode from, AMRNode to){
        this.relation = relation;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from + " --(" + relation + ")--> " + to;
    }
}
