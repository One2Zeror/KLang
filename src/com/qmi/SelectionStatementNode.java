package com.qmi;

/**
 * Created by Administrator on 2015/11/17.
 */
public class SelectionStatementNode implements ASTNode {
    private ASTNode condition;
    private ASTNode trueStatement;
    private ASTNode falseStatement;

    public SelectionStatementNode(ASTNode condition, ASTNode trueStatement, ASTNode falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getTrueStatement() {
        return trueStatement;
    }

    public ASTNode getFalseStatement() {
        return falseStatement;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
