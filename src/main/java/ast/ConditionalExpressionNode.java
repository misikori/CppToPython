package ast;

public class ConditionalExpressionNode extends ASTNode{

    private ExpressionNode condition;
    private ExpressionNode thenBranch;
    private ExpressionNode elseBranch;

    public ConditionalExpressionNode() {
    }

    public ConditionalExpressionNode(ExpressionNode condition, ExpressionNode elseBranch, ExpressionNode thenBranch) {
        this.condition = condition;
        this.elseBranch = elseBranch;
        this.thenBranch = thenBranch;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ExpressionNode getElseBranch() {
        return elseBranch;
    }

    public ExpressionNode getThenBranch() {
        return thenBranch;
    }

    @Override
    public String toString() {
        return "";
    }
}
