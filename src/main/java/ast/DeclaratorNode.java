package ast;

import java.util.ArrayList;
import java.util.List;

public class DeclaratorNode extends ASTNode {

    private  String declaratorId;
    private  String type;
    private List<ParameterNode> parameters;
    private String pointer;

    public DeclaratorNode(String declaratorId, String type, List<ParameterNode> parameters, String pointer) {
        this.declaratorId = declaratorId;
        this.type = type;
        this.parameters = parameters;
        this.pointer = pointer;
    }

    public DeclaratorNode() {

    }


    @Override
    public String toString() {
        var sb = new StringBuilder();

        if(this.pointer != null)
            sb.append(this.pointer);

        if (this.declaratorId != null)
            sb.append(this.declaratorId);

        if(this.type != null)
            sb.append(this.type);

        if(this.parameters != null) {
            sb.append("(");
            sb.append(this.parameters.toString());
            sb.append(")\n");
        }
        return sb.toString();
    }

    @Override
    public String convert() {
        //TODO fix for not basics :)
        return declaratorId;
    }

    public void setName(String s) {
        this.declaratorId = s;
    }

    public void setParams(ArrayList<ParameterNode> l) {
        this.parameters = l;
    }

    public void setPointer(String s) {
        this.pointer = s;
    }

    public String getType() {
        return type;
    }

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public String getDeclaratorId() {
        return declaratorId;
    }
}
