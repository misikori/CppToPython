package ast;

import java.util.List;
import java.util.stream.Collectors;

public class CompoundStatementNode extends ASTNode {
    private final List<ASTNode> statements;

    public CompoundStatementNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "CompoundStatement{" + statements + "}";
    }

    @Override
    public String convert() {
        return statements.stream().map(ASTNode::convert).collect(Collectors.joining("\n"));
    }
}
