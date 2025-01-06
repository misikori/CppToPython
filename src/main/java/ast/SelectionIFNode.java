package ast;

import java.util.ArrayList;
import java.util.List;

public class SelectionIFNode extends SelectionNode {

    private List<ASTNode> ifNodes = new ArrayList<>();

    public SelectionIFNode() {
        super();
    }

    public List<ASTNode> getIfNodes() {
        return ifNodes;
    }

    public void setIfNodes(List<ASTNode> ifNodes) {
        this.ifNodes = ifNodes;
    }

    public void addIfNode(ASTNode ifNode) {
        ifNodes.add(ifNode);
    }

    private String printIfNodes() {
        StringBuilder sb = new StringBuilder();
        for (ASTNode ifNode : ifNodes) {
            sb.append(ifNode.toString() + "\n");
        }
        return sb.toString();
    }
    @Override
    public String toString(){

        return "SelectionIFNode{ type:" + super.getType() + " condition: "+ super.getCondition() + " ifNodes: " + printIfNodes() + "}";
    }
    @Override

    public String convert(int indent_level) {
        // Create indentation string for the current level
        String indent = "\t".repeat(indent_level);
        StringBuilder sb = new StringBuilder();
        System.out.println(indent.length());
        for (int i = 0; i < ifNodes.size(); i++) {
            ASTNode node = ifNodes.get(i);

            if (i == 0) {
                // Handle the main 'if' condition
                sb.append(indent).append("if ");
                sb.append(this.getCondition().convert(indent_level + 1));
                sb.append(":\n");
            } else {
                // Handle the final 'else' block
                sb.append(indent).append("else:\n");
                sb.append(ifNodes.get(i).convert(indent_level + 1));
                sb.append("\n");
            }

        }

        return sb.toString().trim(); // Remove trailing newlines
    }


}
