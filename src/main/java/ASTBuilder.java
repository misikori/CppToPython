import ast.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

// Assuming ASTNode and its subclasses have been defined elsewhere
public class ASTBuilder extends CPP14ParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitTranslationUnit(CPP14Parser.TranslationUnitContext ctx) {
        List<ASTNode> declarations = new ArrayList<>();

        if (ctx.declarationseq() != null) {
            for (CPP14Parser.DeclarationContext declaration : ctx.declarationseq().declaration()) {
                ASTNode node = visit(declaration);
                if (node != null) {
                    declarations.add(node);
                }
            }
        }

        return new TranslationUnitNode(declarations);
    }

    @Override
    public ASTNode visitFunctionDefinition(CPP14Parser.FunctionDefinitionContext ctx) {
        String functionName = extractFunctionName(ctx.declarator());
        String return_value = ctx.declSpecifierSeq().getText();

        FunctionNode functionNode = new FunctionNode(return_value, functionName);

        if (ctx.functionBody().compoundStatement() != null &&
                ctx.functionBody().compoundStatement().statementSeq() != null) {
            for (CPP14Parser.StatementContext stmt : ctx.functionBody().compoundStatement().statementSeq().statement()) {
                System.out.println(stmt.getText());
                ASTNode statementNode = visit(stmt);
                if (statementNode != null) {
                    functionNode.addBodyNode(statementNode);
                }
            }
        }

        return functionNode;
    }


    private String extractFunctionName(CPP14Parser.DeclaratorContext ctx) {


        CPP14Parser.NoPointerDeclaratorContext noPointerCtx = ctx.pointerDeclarator().noPointerDeclarator().noPointerDeclarator();
        System.out.println("Ovo " + noPointerCtx.getText());

        return noPointerCtx.getText();
    }




    @Override
    public ASTNode visitCompoundStatement(CPP14Parser.CompoundStatementContext ctx) {
        List<ASTNode> statements = new ArrayList<>();

        if (ctx.statementSeq() != null) {
            for (CPP14Parser.StatementContext statementContext : ctx.statementSeq().statement()) {
                ASTNode statementNode = visit(statementContext);
                if (statementNode != null) {
                    statements.add(statementNode);
                }
            }
        }

        return new CompoundStatementNode(statements);
    }

    @Override
    public ASTNode visitDeclaration(CPP14Parser.DeclarationContext ctx) {
        String type = null;
        String name = null;
        String initValue = null;

        if (ctx.blockDeclaration() != null) {
            return visitBlockDeclaration(ctx.blockDeclaration());
        }
        if (ctx.functionDefinition() != null) {
            System.out.println("Function definition encountered.");
            return visitFunctionDefinition(ctx.functionDefinition());
        }
        if (ctx.templateDeclaration() != null) {
            System.out.println("Template declaration encountered.");
            return null; // Replace with actual handling logic
        }
        if (ctx.explicitInstantiation() != null) {
            System.out.println("Explicit instantiation encountered.");
            return null;
        }

        if (ctx.explicitSpecialization() != null) {
            System.out.println("Explicit specialization encountered.");
            return null;
        }

        if (ctx.linkageSpecification() != null) {
            System.out.println("Linkage specification encountered.");
            return null;
        }

        if (ctx.namespaceDefinition() != null) {
            System.out.println("Namespace definition encountered.");
            return null;
        }

        if (ctx.emptyDeclaration_() != null) {
            System.out.println("Empty declaration encountered.");
            return null;
        }

        if (ctx.attributeDeclaration() != null) {
            System.out.println("Attribute declaration encountered.");
            return null;
        }
        System.out.println("Unknown declaration type.");
        return null;
    }


    @Override
    public ASTNode visitExpressionStatement(CPP14Parser.ExpressionStatementContext ctx) {
        return new ExpressionNode(ctx.expression().getText());
    }


}