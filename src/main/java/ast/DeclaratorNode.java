package ast;

import java.util.ArrayList;
import java.util.List;

public class DeclaratorNode extends ASTNode {

    private  String declaratorId;
    private  String type;
    private List<ParameterNode> parameters;

    public DeclaratorNode(String declaratorId, String type, List<ParameterNode> parameters) {
        this.declaratorId = declaratorId;
        this.type = type;
        this.parameters = parameters;
    }

    public DeclaratorNode() {

    }


    @Override
    public String toString() {
        return "Declarator{" + "declaratorName='" + declaratorId + '\'' + ", type='" + type + '\'' + ", parameters=" + parameters + '}';
    }

    public void setName(String s) {
        this.declaratorId = s;
    }

    public void setParams(ArrayList<ParameterNode> l) {
        this.parameters = l;
    }
}
