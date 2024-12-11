package ast;

import java.util.List;

public class TranslationUnitNode extends ASTNode {
    private final List<ASTNode> declarations;

    public TranslationUnitNode(List<ASTNode> declarations) {
        this.declarations = declarations;
    }

    @Override
    public String toString() {
        return "TranslationUnit{" + declarations + "}";
    }
}
