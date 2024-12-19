package ast;

import java.util.ArrayList;
import java.util.List;

public class SelectionSwitchNode extends SelectionNode {

    List<ASTNode> cases = new ArrayList<>();

    public SelectionSwitchNode() {
    }
    public List<ASTNode> getCases() {
        return cases;
    }
    public void setCases(List<ASTNode> cases) {
        this.cases = cases;
    }

    public void addCase(ASTNode caseNode) {
        cases.add(caseNode);
    }
}
