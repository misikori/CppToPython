package ast;

public class LiteralNode extends ASTNode {

    private String value;

    public LiteralNode(String value) {
        this.value = value;
    }
    public LiteralNode(){ }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "LiteralNode{ " + value + " }";
    }

    @Override
    public String convert() {

        if(value.equals("std::cout"))
            return "print";
        if(value.equals("std::endl"))
            return "\\n";
        return value;
    }
}
