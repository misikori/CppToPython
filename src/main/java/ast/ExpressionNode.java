package ast;

import java.util.ArrayList;
import java.util.List;

public class ExpressionNode extends ASTNode {
    private String type;
    private String operator;
    private List<ExpressionNode> children = new ArrayList<ExpressionNode>();
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addChildren(ExpressionNode child)
    {
        this.children.add(child);
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(type);
        if (operator != null) {
            str.append(" [operator: ").append(operator).append("]");
        }
        if (value != null) {
            str.append(" [value: ").append(value).append("]");
        }
        if (!children.isEmpty()) {
            str.append(" {children: ").append(children).append("}");
        }
        return str.toString();
    }
}
