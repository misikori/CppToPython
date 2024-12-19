import ast.*;
import org.antlr.v4.codegen.model.decl.Decl;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

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

        if(ctx.compoundStatement() != null){

            return visitCompoundStatement(ctx.compoundStatement());
        }

        if (ctx.declarationStatement() != null) {

            VariableDeclarationNode variableDeclaration = new VariableDeclarationNode();
            visitDeclarationStatement(ctx.declarationStatement(), variableDeclaration);
            return variableDeclaration;
        }
        if (ctx.selectionStatement() != null) {

            CPP14Parser.SelectionStatementContext context = ctx.selectionStatement();

            String type = context.getStart().getText();
            ASTNode condition =  visitCondition(context.condition());

            if(type.equals("If") || type.equals("if")){

                SelectionIFNode ifNode = new SelectionIFNode();
                ifNode.setType(type);
                ifNode.setCondition(condition);
                for(var statement : context.statement()){

                    ASTNode tmp = visitStatement(statement);
                    ifNode.addIfNode(tmp);

                }

                return ifNode;
            }
            // SWITCH
            else{
                SelectionSwitchNode switchNode = new SelectionSwitchNode();
                switchNode.setType(type);
                switchNode.setCondition(condition);
                for(var statement : context.statement()){

                    ASTNode tmp = visitStatement(statement);
                    switchNode.addCase(tmp);
                }
                return switchNode;
            }

        }
        if (ctx.expressionStatement() != null) {
            System.out.println("ExpressionStatement");
            CPP14Parser.ExpressionStatementContext context = ctx.expressionStatement();
            CPP14Parser.ExpressionContext exp = context.expression();
            return visitExpression(exp);
        }

        if (ctx.labeledStatement() != null) {

            LabeledStatement  labeledStatement = new LabeledStatement();
            return visitLabeledStatement(ctx.labeledStatement(), labeledStatement);
        }
        if (ctx.jumpStatement() != null) {
            JumpStatement jumpStatement = new JumpStatement();

            return visitJumpStatement(ctx.jumpStatement(), jumpStatement);
        }
        return null;
    }

    private ASTNode visitJumpStatement(CPP14Parser.JumpStatementContext ctx, JumpStatement statement) {
        statement.setName(ctx.toString());
        return statement;
    }

    private ASTNode visitLabeledStatement(CPP14Parser.LabeledStatementContext ctx, LabeledStatement statement) {

        System.out.println("LabeledStatement");
        String  lable = ctx.getText();
        statement.setLabel(lable);
        statement.setBody(visitStatement(ctx.statement()));
        //TODO: add for Visit for ConstrantExprestion and  other Expreseion for conditional part of switch
        return statement;
    }

    @Override
    public ASTNode visitCondition(CPP14Parser.ConditionContext ctx){

       return visitExpression(ctx.expression());
    }
    public ASTNode visitExpression(CPP14Parser.ExpressionContext ctx) {

        return visitAssignmentExpression(ctx.assignmentExpression(0));
    }

    private void visitDeclarationStatement(CPP14Parser.DeclarationStatementContext ctx, VariableDeclarationNode variable) {

        if(ctx.blockDeclaration() != null) {
            visitBlockDeclaration(ctx.blockDeclaration(), variable);
        }
    }

    private void visitBlockDeclaration(CPP14Parser.BlockDeclarationContext ctx, VariableDeclarationNode variable) {
        if(ctx.simpleDeclaration() != null){
            visitSimpleDeclaration(ctx.simpleDeclaration(), variable);
        }
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

    public void visitParametersAndQualifiers(CPP14Parser.ParametersAndQualifiersContext ctx, DeclaratorNode declaratorNode) {


        CPP14Parser.ParameterDeclarationClauseContext clause = ctx.parameterDeclarationClause();
        if(clause != null) {
            visitParameterDeclarationClause(clause,declaratorNode);
        }
    }

    private void visitParameterDeclarationClause(CPP14Parser.ParameterDeclarationClauseContext clause, DeclaratorNode declaratorNode) {
        CPP14Parser.ParameterDeclarationListContext list = clause.parameterDeclarationList();
        if(list != null){
            ArrayList<ParameterNode> l = new ArrayList<>();
            for(CPP14Parser.ParameterDeclarationContext elem : list.parameterDeclaration()) {

                String declarationSpec = elem.declSpecifierSeq().getText();
                DeclaratorNode declaration = (DeclaratorNode) visitDeclarator(elem.declarator());

                ParameterNode p = new ParameterNode(declarationSpec,declaration);
                l.add(p);
            }
            declaratorNode.setParams(l);
        }

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

    public void visitSimpleDeclaration(CPP14Parser.SimpleDeclarationContext ctx, VariableDeclarationNode variable) {
        if(ctx.declSpecifierSeq() != null) {
            variable.setType(ctx.declSpecifierSeq().getText());
        }
        if(ctx.initDeclaratorList() != null) {
            for (var c : ctx.initDeclaratorList().initDeclarator()) {
                DeclaratorNode node = (DeclaratorNode) visitDeclarator(c.declarator());
                variable.setName(node);

                if(c.initializer() != null) {

                    var expression = visitInitializer(c.initializer());

                    variable.setInitValue(expression);
                }

            }
        }

    }

    public ASTNode visitInitializer(CPP14Parser.InitializerContext ctx) {
        System.out.println("Initializer encountered.");
        if (ctx.braceOrEqualInitializer() != null) {
            //TODO add if you have braces
            return visitInitializerClause(ctx.braceOrEqualInitializer().initializerClause());
        }
        return null;
    }

    public ASTNode visitInitializerClause(CPP14Parser.InitializerClauseContext ctx) {
        System.out.println("Initializer clause encountered.");
        if (ctx.assignmentExpression() != null) {
            return visitAssignmentExpression(ctx.assignmentExpression());
        }
        return null;
    }

    public ASTNode visitAssignmentExpression(CPP14Parser.AssignmentExpressionContext ctx) {
        System.out.println("Assignment expression encountered.");
        //TODO SHOULD CHANGE THIS
        if(ctx.conditionalExpression() != null) {

            ExpressionNode conditional = new ExpressionNode();
            visitConditionalExpression(ctx.conditionalExpression(),conditional);
            return conditional;

        }

        return null;
    }


    public void visitConditionalExpression(CPP14Parser.ConditionalExpressionContext ctx, ExpressionNode conditional) {

        System.out.println("Conditional expression encountered.");
        if(ctx.logicalOrExpression() != null) {
            visitLogicalOrExpression(ctx.logicalOrExpression(), conditional);
        }

        if(ctx.Question() != null) {
            var expression = new ExpressionNode();
//            return visitExpression(ctx.expression(), expression);
        }
    }
    private  <T> void visitExpression_temp(
            List<T> list,
            String type,
            ExpressionNode expression,
            BiConsumer<T, ExpressionNode> visitor
        ){
            //System.out.println(visitor);

            if (list.size() > 1){
                expression.setType(type);
                for (T item : list) {
                    ExpressionNode tmp = new ExpressionNode();
                    visitor.accept(item, tmp);
                    expression.addChildren(tmp);
                }
            }else{
                visitor.accept(list.getFirst(), expression);
            }

        }
    private void visitLogicalOrExpression(CPP14Parser.LogicalOrExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.logicalAndExpression(),
                "LogicalOrExpression",
                expression,
                this::visitLogicalAndExpression
        );
    }

    private void visitLogicalAndExpression(CPP14Parser.LogicalAndExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.inclusiveOrExpression(),
                "LogicalAndExpression",
                expression,
                this::visitInclusiveOrExpression
        );
    }

    private void visitInclusiveOrExpression(CPP14Parser.InclusiveOrExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.exclusiveOrExpression(),
                "InclusiveOrExpression",
                expression,
                this::visitExclusiveOrExpression
        );
    }

    private void visitExclusiveOrExpression(CPP14Parser.ExclusiveOrExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.andExpression(),
                "ExclusiveOrExpression",
                expression,
                this::visitAndExpression
        );
    }

    private void visitAndExpression(CPP14Parser.AndExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.equalityExpression(),
                "AndExpression",
                expression,
                this::visitEqualityExpression
        );
    }

    private void visitEqualityExpression(CPP14Parser.EqualityExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.relationalExpression(),
                "EqualityExpression",
                expression,
                this::visitRelationalExpression
        );
    }

    private void visitRelationalExpression(CPP14Parser.RelationalExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.shiftExpression(),
                "RelationalExpression",
                expression,
                this::visitShiftExpression
        );
    }

    private void visitShiftExpression(CPP14Parser.ShiftExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.additiveExpression(),
                "ShiftExpression",
                expression,
                this::visitAdditiveExpression
        );
    }

    private void visitAdditiveExpression(CPP14Parser.AdditiveExpressionContext ctx, ExpressionNode expression) {

        visitExpression_temp(
                ctx.multiplicativeExpression(),
                "AdditiveExpression",
                expression,
                this::visitMultiplicativeExpression
        );
    }

    private void visitMultiplicativeExpression(CPP14Parser.MultiplicativeExpressionContext ctx, ExpressionNode expression) {
        visitExpression_temp(
                ctx.pointerMemberExpression(),
                "MultiplicativeExpression",
                expression,
                this::visitPointerMemberExpression
        );
    }

    private void visitPointerMemberExpression(CPP14Parser.PointerMemberExpressionContext ctx, ExpressionNode expression) {

        visitExpression_temp(
                ctx.castExpression(),
                "PointerMemberExpression",
                expression,
                this::visitCastExpression
        );
    }

    private void visitCastExpression(CPP14Parser.CastExpressionContext ctx, ExpressionNode expression) {

        expression.setType("Cast Expression");
        expression.setType(ctx.getText());
    }


    @Override
    public ASTNode visitExpressionStatement(CPP14Parser.ExpressionStatementContext ctx) {
        return null;
    }


}