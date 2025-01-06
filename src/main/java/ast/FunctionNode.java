package ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionNode extends ASTNode {

    private final String return_value;
    private final String name;
    private final List<ASTNode> body;
    private List<ParameterNode> arguments;



    public FunctionNode(String return_value, String name) {
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

    public List<ParameterNode> getArguments() {
        return arguments;
    }

    public void setArguments(List<ParameterNode> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "Function{"+"retun_value=" +return_value + ", name='" + name + '\'' + ", body=" + body + '}';
    }

    @Override
    public String convert(int indent_level) {


        String indent = "\t".repeat(indent_level);
        StringBuilder sb = new StringBuilder();

        sb.append(indent).append("def ").append(name).append("(");
        if (arguments != null) {
            var size = arguments.size();
            for (var i = 0; i < size; i++) {
                sb.append(arguments.get(i).convert(indent_level));
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(")");

        if (return_value != null && !return_value.isEmpty()) {
            sb.append(" -> ").append(return_value);
        }

        sb.append(":\n");

        for (ASTNode node : body) {
            sb.append(node.convert(indent_level + 1)).append("\n");
        }
        return sb.toString().trim();
    }

}
