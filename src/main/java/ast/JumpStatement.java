package ast;

public class JumpStatement  extends  ASTNode {

    private String name;

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

    @Override
    public String toString() {
        return "";
    }
}
