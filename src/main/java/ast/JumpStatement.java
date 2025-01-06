package ast;

public class JumpStatement  extends  ASTNode {

    private String name;
    private ASTNode expression;

    public JumpStatement() {
    }
    public JumpStatement(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ASTNode getExpression() {
        return expression;
    }
    public void setExpression(ASTNode expression) {
        this.expression = expression;
    }
    @Override
    public String toString() {
        return "";
    }

    @Override
    public String convert(int indent_level) {
        //System.out.println("converter is called jump statement");
        StringBuilder res = new StringBuilder();
        if (name != null) {
            res.append(name);
            res.append(" ");
        }
        if (expression != null) {
            res.append(expression.convert(indent_level));
        }
        res.append("\n");
        return res.toString();
    }
}
