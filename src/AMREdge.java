public class AMREdge {

    private final AMRNode to;
    private final String relation;


    public AMREdge(AMRNode to, String relation) {
        this.relation = relation;
        this.to = to;
    }

    public AMRNode getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Relation: " + relation + ", To: " + to.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AMREdge other) {
            return this.relation.equals(other.relation) && this.to.equals(other.to);
        }
        return false;
    }
}
