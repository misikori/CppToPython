package ast;

import java.util.List;

public class CompoundStatementNode extends ASTNode {
    private final List<ASTNode> statements;

    public CompoundStatementNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "CompoundStatement{" + statements + "}";
    }
}
