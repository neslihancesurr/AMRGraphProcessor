public record IndentNode(String name, String relation, int indent) {

    @Override
    public String toString() {
        return name + " " + relation + " " + indent;
    }
}
