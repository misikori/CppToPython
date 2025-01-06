package ast;

import java.util.List;

public class TranslationUnitNode extends ASTNode {
    private final List<ASTNode> declarations;

    public TranslationUnitNode(List<ASTNode> declarations) {
        this.declarations = declarations;
    }

    public List<ASTNode> getDeclarations() {
        return declarations;
    }
    @Override
    public String toString() {
        return "TranslationUnit{" + declarations + "}";
    }

    @Override
    public String convert(int indent_level) {


        String indent = "\t".repeat(indent_level);

        StringBuilder sb = new StringBuilder();

        for (ASTNode declaration : declarations) {
            sb.append(indent)
                    .append(declaration.convert(indent_level))
                    .append("\n");
        }
        return sb.toString().trim();
    }
}
