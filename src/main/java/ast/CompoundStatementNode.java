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
    public String convert(int indent_level) {
        StringBuilder sb = new StringBuilder();
        String indent = "\t".repeat(indent_level);

        for (ASTNode statement : statements) {
            sb.append(indent)
                    .append(statement.convert(indent_level + 1))
                    .append("\n");
        }

        return sb.toString().trim(); // Remove the trailing newline
    }
}
