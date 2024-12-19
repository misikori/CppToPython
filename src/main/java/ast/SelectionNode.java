package ast;


public class SelectionNode extends ASTNode {

    private String type;
    private ASTNode condition;




    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public ASTNode getCondition() {
        return condition;
    }
    public void setCondition(ASTNode condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "SelectionNode{ type=" + type + ", condition=" + condition + '}';
    }
}
