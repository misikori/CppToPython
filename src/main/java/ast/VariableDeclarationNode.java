package ast;

public class VariableDeclarationNode extends ASTNode {
    private String name;
    private String type;
    private String initValue;

    public VariableDeclarationNode(String name, String type, String initValue) {
        this.name = name;
        this.type = type;
        this.initValue = initValue;
    }

    public VariableDeclarationNode() {}

    @Override
    public String toString() {
        return "VariableDeclaration{" + "name='" + name + '\'' + ", type='" + type + '\'' + ", initValue='" + initValue + '\'' + '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getInitValue() {
        return initValue;
    }
}
