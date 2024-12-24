package ast;

public class ConditionalExpressionNode extends ExpressionNode {
    private ExpressionNode condition;
    private ExpressionNode thenBranch;
    private ExpressionNode elseBranch;

    public ConditionalExpressionNode() {
        super(); // Initialize as an ExpressionNode
        setType("ConditionalExpression");
    }

    public ConditionalExpressionNode(ExpressionNode condition, ExpressionNode thenBranch, ExpressionNode elseBranch) {
        this();
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;

        // Add children for traversal purposes
        if (condition != null) addChildren(condition);
        if (thenBranch != null) addChildren(thenBranch);
        if (elseBranch != null) addChildren(elseBranch);
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public ExpressionNode getThenBranch() {
        return thenBranch;
    }

    public void setThenBranch(ExpressionNode thenBranch) {
        this.thenBranch = thenBranch;
    }

    public ExpressionNode getElseBranch() {
        return elseBranch;
    }

    public void setElseBranch(ExpressionNode elseBranch) {
        this.elseBranch = elseBranch;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("ConditionalExpression {");
        str.append("condition: ").append(condition);
        str.append(", thenBranch: ").append(thenBranch);
        str.append(", elseBranch: ").append(elseBranch);
        str.append("}");
        return str.toString();
    }
}
