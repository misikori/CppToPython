package ast;

import java.beans.Expression;

public class LabeledStatement extends ASTNode {

    private String label;
    private ASTNode caseExpression;
    private ASTNode statement;

    public LabeledStatement() {
    }

    public String getLabel() {
        return label;
    }

    public ASTNode getConstantExpression() {
        return caseExpression;
    }

    public void setCaseExpression(ASTNode constantExpression) {
        this.caseExpression = constantExpression;
    }

    public ASTNode getStatement() {
        return statement;
    }

    public void setStatement(ASTNode statement) {
        this.statement = statement;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @Override
    public String toString() {
        return "LabeledStatement {" + "label=" + label + ", caseExpr=" + caseExpression + ", statement=" + statement + '}';
    }

    @Override
    public String convert() {
        return "";
    }
}
