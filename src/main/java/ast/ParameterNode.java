package ast;

public class ParameterNode extends ASTNode {

    String type;
    DeclaratorNode name;

    public ParameterNode(String type, DeclaratorNode name) {
        this.name = name;
        this.type = type;
    }




    @Override
    public String toString() {
        return "ParameterNode{" + "type='" + type + '\'' + ", DeclarationNode=" + name.toString() + '}';
    }

    @Override
    public String convert() {
        return "";
    }
}
