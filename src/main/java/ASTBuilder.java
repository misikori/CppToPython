import ast.*;
import org.antlr.v4.codegen.model.decl.Decl;
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
    public ASTNode visitStatement(CPP14Parser.StatementContext ctx) {

        if (ctx.declarationStatement() != null) {
            if(ctx.declarationStatement().blockDeclaration() != null) {
                return visitBlockDeclaration(ctx.declarationStatement().blockDeclaration());
            }
        }

        return null;
    }

    @Override
    public ASTNode visitFunctionDefinition(CPP14Parser.FunctionDefinitionContext ctx) {

        DeclaratorNode functionDeclaration = (DeclaratorNode) visit(ctx.declarator());
        String return_value = ctx.declSpecifierSeq().getText();

        FunctionNode functionNode = new FunctionNode(return_value, functionDeclaration.toString());

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

    @Override
    public ASTNode visitFunctionBody(CPP14Parser.FunctionBodyContext ctx) {

        if(ctx.constructorInitializer() != null) {
            // 0 or more constructor comprehension.
        }

        else if(ctx.compoundStatement() != null) {
            return visitCompoundStatement(ctx.compoundStatement());
        }

        else if(ctx.functionTryBlock() != null) {
            // ..
        }

        else{
            // return ctx.getText() into FunctionBody Node.
        }

        return null;
    }

    @Override
    public ASTNode visitDeclarator(CPP14Parser.DeclaratorContext ctx) {


        System.out.println("Declarator encountered.");
        DeclaratorNode declaratorNode = new DeclaratorNode();

        if(ctx.pointerDeclarator() != null) {
            ASTNode pointerDeclarator = visitPointerDeclarator(ctx.pointerDeclarator(), declaratorNode);
        }
        if(ctx.noPointerDeclarator() != null) {
            ASTNode noPointerDeclarator = visitNoPointerDeclarator(ctx.noPointerDeclarator(), declaratorNode);
        }
        return declaratorNode;
    }

    public ASTNode visitPointerDeclarator(CPP14Parser.PointerDeclaratorContext ctx, DeclaratorNode declaratorNode) {

        System.out.println("PointerDeclarator encountered.");

        if(ctx.pointerOperator() != null)
        {
            StringBuilder sb = new StringBuilder();
            for(var c : ctx.pointerOperator())
            {
                sb.append(c.getText());
            }
            declaratorNode.setPointer(sb.toString());
            // something with * operator.
        }

        if(ctx.noPointerDeclarator() != null)
        {
            visitNoPointerDeclarator(ctx.noPointerDeclarator(), declaratorNode);
        }

        return declaratorNode;
    }

    public ASTNode visitNoPointerDeclarator(CPP14Parser.NoPointerDeclaratorContext ctx, DeclaratorNode declaratorNode) {
        System.out.println("NoPointerDeclarator encountered.");

        if(ctx.noPointerDeclarator() != null) {
            visitNoPointerDeclarator(ctx.noPointerDeclarator(), declaratorNode);
        }
        if(ctx.parametersAndQualifiers() != null) {
            visitParametersAndQualifiers(ctx.parametersAndQualifiers(), declaratorNode);
        }
        if(ctx.declaratorid() != null) {
            declaratorNode.setName(processDeclaratorid(ctx.declaratorid()));
        }
        return declaratorNode;
    }

    public List<ParameterNode> visitParametersAndQualifiers(CPP14Parser.ParametersAndQualifiersContext ctx, DeclaratorNode declaratorNode) {

        CPP14Parser.ParameterDeclarationClauseContext clause = ctx.parameterDeclarationClause();
        CPP14Parser.ParameterDeclarationListContext list = ctx.parameterDeclarationClause().parameterDeclarationList();
        ArrayList<ParameterNode> l = new ArrayList<>();
        for(CPP14Parser.ParameterDeclarationContext elem : list.parameterDeclaration()) {

            String declarationSpec = elem.declSpecifierSeq().getText();
            DeclaratorNode declaration = (DeclaratorNode) visitDeclarator(elem.declarator());

            ParameterNode p = new ParameterNode(declarationSpec,declaration);
            l.add(p);
        }
        declaratorNode.setParams(l);
        return l;
    }

    public String processDeclaratorid(CPP14Parser.DeclaratoridContext ctx){
        System.out.println("Declarator id encountered.");
        return ctx.getText();
    }




    @Override
    public ASTNode visitCompoundStatement(CPP14Parser.CompoundStatementContext ctx) {
        List<ASTNode> statements = new ArrayList<>();

        if (ctx.statementSeq() != null) {
            for (CPP14Parser.StatementContext statementContext : ctx.statementSeq().statement()) {
                ASTNode statementNode = visitStatement(statementContext);
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
    public ASTNode visitSimpleDeclaration(CPP14Parser.SimpleDeclarationContext ctx) {

        VariableDeclarationNode decl = new VariableDeclarationNode();

        if (ctx.declSpecifierSeq() != null) {
            decl.setType(ctx.declSpecifierSeq().getText());
        }

        if(ctx.initDeclaratorList() != null) {
            for (var c : ctx.initDeclaratorList().initDeclarator()) {
                DeclaratorNode node = (DeclaratorNode) visitDeclarator(c.declarator());
                decl.setName(node.getDeclaratorId());
                decl.setInitValue(c.initializer().braceOrEqualInitializer().getText());
            }

        }

        return decl;
    }

    @Override
    public ASTNode visitBlockDeclaration(CPP14Parser.BlockDeclarationContext ctx) {

        if (ctx.simpleDeclaration() != null)
        {
            return visitSimpleDeclaration(ctx.simpleDeclaration());
        }

        return null;
    }


    @Override
    public ASTNode visitExpressionStatement(CPP14Parser.ExpressionStatementContext ctx) {
        return new ExpressionNode(ctx.expression().getText());
    }


}