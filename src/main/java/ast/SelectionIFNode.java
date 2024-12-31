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
    public String convert(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ifNodes.size(); i++){
            ASTNode node =  ifNodes.get(i);
            System.out.println(node.toString());
            if (i == 0){
                sb.append("if ");
                sb.append(this.getCondition().convert());
                sb.append(":\n\t");
            }
            else if(i == ifNodes.size() - 1){
                sb.append("else:\n\t");
                sb.append(ifNodes.get(i).convert());
                sb.append(":\n\t");
            }
            else{
                sb.append("else:\n\t");
                sb.append(ifNodes.get(i).convert());
                sb.append(":\n");
            }
        }
        return sb.toString();
    }
}
