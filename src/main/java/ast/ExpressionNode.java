package ast;

import java.util.ArrayList;
import java.util.List;

public class ExpressionNode extends ASTNode {
    private String type;
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(type);
        str.append("{");
        if(value != null){
            str.append(" value: ").append(value);
        }
        if(!children.isEmpty()){
            str.append(" children:").append(children);
        }
        str.append("}");

        return str.toString();

    }
}
