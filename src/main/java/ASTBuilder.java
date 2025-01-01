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
        System.out.println("This is statement that im looking at:" + ctx.getText());
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
            //System.out.println("ExpressionStatement");
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
        System.out.println("Im in: ");
        if(ctx.Break() != null){
            //TODO add break;

        }else if(ctx.Return() != null){
            statement.setName("return");
            statement.setExpression(visitExpression(ctx.expression()));

        }else if(ctx.Continue() != null){
            // TODO add continue;

        }else if(ctx.Goto() != null){
            // TODO add Goto;

        }

        System.out.println("THIS IS STATEMENT: " + statement.toString());
        return statement;
    }

    @Override
    public ASTNode visitConstantExpression(CPP14Parser.ConstantExpressionContext ctx) {
        //TODO: fix this  constant expression parser
        ExpressionNode constatExpresion = new ExpressionNode();

        return constatExpresion;
    }

    private ASTNode visitLabeledStatement(CPP14Parser.LabeledStatementContext ctx, LabeledStatement statement) {

        //System.out.println("LabeledStatement");
        String label = ctx.Case() != null ? ctx.Case().getText() : ctx.Default().getText();
        statement.setLabel(label);
        statement.setStatement(visitStatement(ctx.statement()));
        if (ctx.constantExpression() != null) {
            statement.setCaseExpression(visitConstantExpression(ctx.constantExpression()));
        }
        return statement;
    }

    public ASTNode visitCondition(CPP14Parser.ConditionContext ctx) {
        //System.out.println("Condition encountered.");
        //System.out.println(ctx.getText());
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
                //System.out.println(stmt.getText());
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
        //System.out.println("Declarator id encountered.");
        return ctx.getText();
    }




    @Override
    public ASTNode visitCompoundStatement(CPP14Parser.CompoundStatementContext ctx) {
        List<ASTNode> statements = new ArrayList<>();
        System.out.println("This is what happends in compound statement");
        System.out.println(ctx.statementSeq().getText());
        if (ctx.statementSeq() != null) {
            for (CPP14Parser.StatementContext statementContext : ctx.statementSeq().statement()) {
                ASTNode statementNode = visitStatement(statementContext);
                if (statementNode != null) {
                    statements.add(statementNode);
                }
            }
        }
        System.out.println("This is list of statements  " + statements.getFirst());
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
            //System.out.println("Function definition encountered.");
            return visitFunctionDefinition(ctx.functionDefinition());
        }
        if (ctx.templateDeclaration() != null) {
            //System.out.println("Template declaration encountered.");
            return null; // Replace with actual handling logic
        }
        if (ctx.explicitInstantiation() != null) {
            //System.out.println("Explicit instantiation encountered.");
            return null;
        }

        if (ctx.explicitSpecialization() != null) {
            //System.out.println("Explicit specialization encountered.");
            return null;
        }

        if (ctx.linkageSpecification() != null) {
            //System.out.println("Linkage specification encountered.");
            return null;
        }

        if (ctx.namespaceDefinition() != null) {
            //System.out.println("Namespace definition encountered.");
            return null;
        }

        if (ctx.emptyDeclaration_() != null) {
            //System.out.println("Empty declaration encountered.");
            return null;
        }

        if (ctx.attributeDeclaration() != null) {
            //System.out.println("Attribute declaration encountered.");
            return null;
        }
        System.out.println("Unknown declaration type.");
        return null;
    }

    public void visitSimpleDeclaration(CPP14Parser.SimpleDeclarationContext ctx, VariableDeclarationNode variable) {
        if(ctx.declSpecifierSeq() != null) {
            visitDeclSpecifierSeq(ctx.declSpecifierSeq(), variable);
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
    public void visitDeclSpecifierSeq(CPP14Parser.DeclSpecifierSeqContext ctx, VariableDeclarationNode variable) {

        //TODO convert to list maybe ?
//        List<DeclaratorTypeNode> nodes = new ArrayList<>();
//        if (ctx.declSpecifier() != null) {
//            List<CPP14Parser.DeclSpecifierContext> list = ctx.declSpecifier();
//            for(CPP14Parser.DeclSpecifierContext elem : list) {
//
//                DeclaratorTypeNode node = new DeclaratorTypeNode();
//                nodes.add(visitDeclSpecifier(elem, node));
//            }
//        }
        DeclaratorTypeNode node = new DeclaratorTypeNode();
        visitDeclSpecifier(ctx.declSpecifier(0), node);
        //System.out.println("Declaration specifier list : " + node.toString());
        variable.setType(node.toString());
    }

    private DeclaratorTypeNode visitDeclSpecifier(CPP14Parser.DeclSpecifierContext ctx, DeclaratorTypeNode node) {

        if(ctx.typeSpecifier() != null) {
            visitTypeSpeficier(ctx.typeSpecifier(), node);
        }
        return null;
    }

    private void visitTypeSpeficier(CPP14Parser.TypeSpecifierContext ctx, DeclaratorTypeNode node) {


        if (ctx.trailingTypeSpecifier() != null) {
            visitTrailingTypeSpecifier(ctx.trailingTypeSpecifier(), node);
        }
    }

    private void visitTrailingTypeSpecifier(CPP14Parser.TrailingTypeSpecifierContext ctx, DeclaratorTypeNode node) {

        if (ctx.simpleTypeSpecifier() != null) {
            visitSimpleTypeSpecifier(ctx.simpleTypeSpecifier(), node);
        }
    }

    private void visitSimpleTypeSpecifier(CPP14Parser.SimpleTypeSpecifierContext ctx, DeclaratorTypeNode node) {
        //TODO this should be fixed MUST
        if(ctx.theTypeName() != null) {
            CPP14Parser.TheTypeNameContext typename = ctx.theTypeName();
            //System.out.println("This is simple type:");
            //System.out.println(typename.getText());
            if(typename != null) {
                if(ctx.theTypeName() != null) {
                    if(ctx.theTypeName().className() != null) {
                        if(ctx.theTypeName().className().simpleTemplateId() != null) {
                            if(ctx.theTypeName().className().simpleTemplateId().templateName() != null) {
                                node.setName(ctx.theTypeName().className().simpleTemplateId().templateName().getText());
                            }
                        }
                    }
                }

            }
        }
    }

    public ASTNode visitInitializer(CPP14Parser.InitializerContext ctx) {
        if (ctx.braceOrEqualInitializer() != null) {
            //TODO add if you have braces
            return visitInitializerClause(ctx.braceOrEqualInitializer().initializerClause());
        }
        return null;
    }

    public ASTNode visitInitializerClause(CPP14Parser.InitializerClauseContext ctx) {
        if (ctx.assignmentExpression() != null) {
            return visitAssignmentExpression(ctx.assignmentExpression());
        }
        else if (ctx.bracedInitList() != null) {
            return visitInitializerList(ctx.bracedInitList().initializerList());
        }
        return null;
    }

    public ASTNode visitInitializerList(CPP14Parser.InitializerListContext ctx) {

        ExpressionNode exp = new ExpressionNode();
        List<CPP14Parser.InitializerClauseContext> list = ctx.initializerClause();

        for(CPP14Parser.InitializerClauseContext elem : list){

            exp.addChildren(visitInitializerClause(elem));
        }
        return exp;
    }

    public ASTNode visitAssignmentExpression(CPP14Parser.AssignmentExpressionContext ctx) {
        //System.out.println("Assignment expression encountered.");

        if(ctx.conditionalExpression() != null) {
            return visitConditionalExpression(ctx.conditionalExpression());
        }
        else{
            ExpressionNode left = (ExpressionNode) visitLogicalOrExpression(ctx.logicalOrExpression());
            String operator = ctx.assignmentOperator().getText();
            ExpressionNode right = (ExpressionNode) visitInitializerClause(ctx.initializerClause());

            // return new AssignmentNode?
        }

        return null;
    }
    public ASTNode visitConditionalExpression(CPP14Parser.ConditionalExpressionContext ctx) {

        // logicalOr (? expr : assignExpr)?
        ExpressionNode exp = new ExpressionNode();
        if(ctx.logicalOrExpression() != null) {
            visitLogicalOrExpression(ctx.logicalOrExpression(), exp);
            return exp;
        }
        else{

            return visitAssignmentExpression(ctx.assignmentExpression());
        }
    }
    // [unqualifiedId [value: x]



    private  <T> void visitExpression_temp(
            List<T> list,
            String type,
            String value,
            ExpressionNode expression,
            BiConsumer<T, ExpressionNode> visitor
        ){
            //System.out.println(visitor);

            if (list.size() > 1){
                expression.setType(type);
                expression.setValue(value);
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
        String value = ctx.getText();
        visitExpression_temp(
                ctx.logicalAndExpression(),
                "LogicalOrExpression",
                value,
                expression,
                this::visitLogicalAndExpression
        );
    }

    private void visitLogicalAndExpression(CPP14Parser.LogicalAndExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.inclusiveOrExpression(),
                "LogicalAndExpression",
                value,
                expression,
                this::visitInclusiveOrExpression
        );
    }

    private void visitInclusiveOrExpression(CPP14Parser.InclusiveOrExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.exclusiveOrExpression(),
                "InclusiveOrExpression",
                value,
                expression,
                this::visitExclusiveOrExpression
        );
    }

    private void visitExclusiveOrExpression(CPP14Parser.ExclusiveOrExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.andExpression(),
                "ExclusiveOrExpression",
                value,
                expression,
                this::visitAndExpression
        );
    }

    private void visitAndExpression(CPP14Parser.AndExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.equalityExpression(),
                "AndExpression",
                value,
                expression,
                this::visitEqualityExpression
        );
    }

    private void visitEqualityExpression(CPP14Parser.EqualityExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.relationalExpression(),
                "EqualityExpression",
                value,
                expression,
                this::visitRelationalExpression
        );
    }

    private void visitRelationalExpression(CPP14Parser.RelationalExpressionContext ctx, ExpressionNode expression) {

        String value= "";

        if(!ctx.Less().isEmpty()){
            value = ctx.Less().getFirst().toString();
        }
        else if (!ctx.LessEqual().isEmpty()) {
            value = ctx.LessEqual().getFirst().toString();

        }
        else if(!ctx.GreaterEqual().isEmpty()){
            value = ctx.GreaterEqual().getFirst().toString();

        }
        else if(!ctx.Greater().isEmpty()){
            value = ctx.Greater().getFirst().toString();

        }

        visitExpression_temp(
                ctx.shiftExpression(),
                "RelationalExpression",
                value,
                expression,
                this::visitShiftExpression
        );
    }

    private void visitShiftExpression(CPP14Parser.ShiftExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.additiveExpression(),
                "ShiftExpression",
                value,
                expression,
                this::visitAdditiveExpression
        );
    }

    private void visitAdditiveExpression(CPP14Parser.AdditiveExpressionContext ctx, ExpressionNode expression) {

        String value = ctx.getText();
        visitExpression_temp(
                ctx.multiplicativeExpression(),
                "AdditiveExpression",
                value,
                expression,
                this::visitMultiplicativeExpression
        );
    }

    private void visitMultiplicativeExpression(CPP14Parser.MultiplicativeExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.pointerMemberExpression(),
                "MultiplicativeExpression",
                value,
                expression,
                this::visitPointerMemberExpression
        );
    }

    private void visitPointerMemberExpression(CPP14Parser.PointerMemberExpressionContext ctx, ExpressionNode expression) {
        String value = ctx.getText();
        visitExpression_temp(
                ctx.castExpression(),
                "PointerMemberExpression",
                value,
                expression,
                this::visitCastExpression
        );
    }

    private void visitCastExpression(CPP14Parser.CastExpressionContext ctx, ExpressionNode expression) {
        if (ctx.unaryExpression() != null) {
            visitUnaryExpression(ctx.unaryExpression(), expression);
        }
//        expression.setType("Cast Expression");
//        expression.setValue(ctx.getText());
    }


    private void visitUnaryExpression(CPP14Parser.UnaryExpressionContext ctx, ExpressionNode expression){
        if (ctx.postfixExpression() != null) {
            visitPostfixExpression(ctx.postfixExpression(), expression);
        }
    }

    private void visitPostfixExpression(CPP14Parser.PostfixExpressionContext ctx, ExpressionNode expression) {
        if (ctx.primaryExpression() != null) {
            visitPrimaryExpression(ctx.primaryExpression(), expression);
        }
    }

    private void visitPrimaryExpression(CPP14Parser.PrimaryExpressionContext ctx, ExpressionNode expression) {



        if (ctx.literal() != null) {
            //TODO for l in literal ..
            var l = new LiteralNode();
            l.setValue(ctx.getText());
//            expression.setValue(ctx.getText());
//            expression.setType("literal");
            expression.addChildren(l);
        }

//        if (ctx.expression() != null) {
//            visitExpression(ctx.expression());
//        }
//
//        if (ctx.idExpression() != null) {
//            visitIdExpression(ctx.idExpression(), expression);
//            // visitIdExpression..
//        }
    }

    private void visitIdExpression(CPP14Parser.IdExpressionContext ctx, ExpressionNode expression) {

        if (ctx.unqualifiedId() != null) {
            expression.setType("unqualifiedId");
            expression.setValue(ctx.unqualifiedId().getText());
        }
    }

//    @Override
//    public ASTNode visitExpressionStatement(CPP14Parser.ExpressionStatementContext ctx) {
//        return null;
//    }


}