package ast;

public class ExpressionNode extends ASTNode {
    private final String expression;

    public ExpressionNode(String expression) {

        System.out.println(expression);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Expression{" + "expression='" + expression + '\'' + '}';
    }
}
