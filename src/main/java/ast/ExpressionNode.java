package ast;

import java.util.ArrayList;
import java.util.List;

public class ExpressionNode extends ASTNode {
    private String type;
    private String operator;
    private List<ASTNode> children = new ArrayList<ASTNode>();
    private String value;

    public ExpressionNode() {
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ExpressionNode(List<ASTNode> children, String type) {
        this.children = children;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addChildren(ASTNode child)
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
        if (type != null) {
            str.append(type);
        }
        if (operator != null) {
            str.append(" [operator: ").append(operator).append("]");
        }
        if (value != null) {
            str.append(" [value: ").append(value).append("]");
        }
        if (!children.isEmpty()) {
            str.append(" [children: ").append(children).append("]");
        }
        return str.toString();
    }

    @Override
    public String convert(int indent_level) {

        StringBuilder str = new StringBuilder();

        if(type != null && type.equals("AdditiveExpression")){
            for(int i=0; i<children.size(); i++){
                str.append(children.get(i).convert(indent_level));
                str.append(" ");
                if(i ==children.size()-1){
                    str.append(" ");
                }else{
                    str.append(value).append(" ");

                }
            }
            return str.toString();
        }
        if(type != null && type.equals("RelationalExpression")){

            for(int i=0; i<children.size(); i++){
                str.append(children.get(i).convert(indent_level));
                str.append(" ");
                if(i == children.size()-1){
                    str.append("");
                }else{
                    str.append(value).append(" ");
                }
            }
            return str.toString();
        }
        if(type != null && type.equals("ShiftExpression")){

            if(children.getFirst().convert(indent_level).trim().equals("print")){
                str.append(children.getFirst().convert(indent_level));
                str.append("(");
                for(int i=1; i<children.size(); i++){
                    str.append(children.get(i).convert(indent_level));
                }
                str.append(")");
            }
            return str.toString();
        }
        if(type != null && type.equals("PostfixExpression")){
            for(int i=0; i<children.size(); i++){
                str.append(children.get(i).convert(indent_level));
            }
            //TODO fix this shit pls
            str.append(this.getValue());
            return str.toString();
        }
        for (ASTNode child : children) {
            str.append(child.convert(indent_level+1));
        }
        return str.toString();
    }
}
