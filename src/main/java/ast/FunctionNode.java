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

    public String getReturn_value() {
        return return_value;
    }

    public String getName() {
        return name;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Function{"+"retun_value=" +return_value + ", name='" + name + '\'' + ", body=" + body + '}';
    }

    @Override
    public String convert() {
        StringBuilder sb = new StringBuilder();

        sb.append("deff ");
        sb.append(name);
        sb.append("(");
        //TODO add append for arguments
        sb.append(")");
        sb.append("->");
        sb.append(return_value);
        sb.append(":\n");

        for (ASTNode node : body) {
            sb.append("\t");
            sb.append(node.convert());
            sb.append("\n");
        }
        return sb.toString();
    }
}
