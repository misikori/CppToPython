package ast;

public class ParameterNode extends ASTNode {

    public String type;
    public DeclaratorNode name;

    public ParameterNode(String type, DeclaratorNode name) {
        this.name = name;
        this.type = type;
    }




    @Override
    public String toString() {
        return "ParameterNode{" + "type='" + type + '\'' + ", DeclarationNode=" + name.toString() + '}';
    }

    @Override
    public String convert(int indent_level) {

        StringBuilder sb = new StringBuilder();

        sb.append(type);
        sb.append(" : ");
        sb.append(name.getDeclaratorId());

        return sb.toString();
    }
}
