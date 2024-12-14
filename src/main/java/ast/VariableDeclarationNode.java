package ast;

public class VariableDeclarationNode extends ASTNode {

    private String type;
    private DeclaratorNode name;
    private ExpressionNode expression;

    public VariableDeclarationNode(DeclaratorNode name, String type, ExpressionNode expression) {
        this.name = name;
        this.type = type;
        this.expression = expression;
    }

    public VariableDeclarationNode() {}

    @Override
    public String toString() {
        return "VariableDeclaration{" + "name='" + name + '\'' + ", type='" + type + '\'' + ", expression='" + expression + '\'' + '}';
    }

    public void setName(DeclaratorNode name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInitValue(ExpressionNode expression) {
        this.expression = expression;
    }

    public DeclaratorNode getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ExpressionNode getInitValue() {
        return expression;
    }
}
