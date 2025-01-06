package ast;

public class DeclaratorTypeNode extends ASTNode {

    String name = "";

    public DeclaratorTypeNode(String name) {
        this.name = name;
    }
    public DeclaratorTypeNode() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String convert(int indent_level) {
        return name;
    }
}
