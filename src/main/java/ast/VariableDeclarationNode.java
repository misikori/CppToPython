package ast;

public class VariableDeclarationNode extends ASTNode {
    private final String name;
    private final String type;
    private final String initValue;

    public VariableDeclarationNode(String name, String type, String initValue) {
        this.name = name;
        this.type = type;
        this.initValue = initValue;
    }

    @Override
    public String toString() {
        return "VariableDeclaration{" + "name='" + name + '\'' + ", type='" + type + '\'' + ", initValue='" + initValue + '\'' + '}';
    }
}
