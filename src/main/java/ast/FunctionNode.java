package ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionNode extends ASTNode {

    private final String return_value;
    private final String name;
    private final List<ASTNode> body;

    public FunctionNode(String return_value,String name) {
        this.return_value = return_value;
        this.name = name;
        this.body = new ArrayList<>();
    }

    public void addBodyNode(ASTNode node) {
        body.add(node);
    }

    @Override
    public String toString() {
        return "Function{"+"retun_value=" +return_value + ", name='" + name + '\'' + ", body=" + body + '}';
    }
}
