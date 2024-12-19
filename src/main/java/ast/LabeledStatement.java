package ast;

public class LabeledStatement extends ASTNode {

    private String label;
    private ASTNode condition;
    private ASTNode body;

    public LabeledStatement() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public void setCondition(ASTNode condition) {
        this.condition = condition;
    }

    public ASTNode getBody() {
        return body;
    }

    public void setBody(ASTNode body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "LabeledStatement {" + "label=" + label + ", condition=" + condition + ", body=" + body + '}';
    }
}
