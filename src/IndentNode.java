public class IndentNode {
    String name;
    String relation;
    int indent;

    public IndentNode(String name, String relation, int indent){
        this.name = name;
        this.indent = indent;
        this.relation = relation;
    }

    @Override
    public String toString() {
        return  name + " " +  relation + " " + indent;
    }
}
