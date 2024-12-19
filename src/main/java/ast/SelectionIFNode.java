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
}
