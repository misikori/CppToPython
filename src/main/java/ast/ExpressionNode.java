package ast;

import java.util.List;

public class ExpressionNode extends ASTNode {
    private String type;
    private List<ExpressionNode> children;

    public ExpressionNode() {
    }

    public List<ExpressionNode> getChildren() {
        return children;
    }

    public void setChildren(List<ExpressionNode> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ExpressionNode(List<ExpressionNode> children, String type) {
        this.children = children;
        this.type = type;
    }

    public void addChildren(ExpressionNode child)
    {
        this.children.add(child);
    }

    @Override
    public String toString() {
        return type;
    }
}
