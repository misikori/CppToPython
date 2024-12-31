package ast;

import java.util.List;

public class VariableDeclarationNode extends ASTNode {

    private String type;
    private DeclaratorNode name;
    private ASTNode expression;

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

    @Override
    public String convert() {

        StringBuilder result = new StringBuilder();
        result.append(name.convert());
        result.append(" = ");

        //TODO add for different types of variables:

        if (type.equals("vector")) {
            result.append('[');
            ExpressionNode exp = (ExpressionNode) expression;
            List<ASTNode> children = exp.getChildren();
            for( int i = 0; i < children.size(); i += 1) {

                result.append(children.get(i).convert());
                if (i != children.size() - 1) {
                    result.append(", ");
                }
            }
            result.append(']');
        }
        else if (expression != null) {
            result.append(expression.convert());
        }
        return result.toString();
    }

    public void setName(DeclaratorNode name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInitValue(ASTNode expression) {
        this.expression = expression;
    }

    public DeclaratorNode getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ASTNode getInitValue() {
        return expression;
    }
}
