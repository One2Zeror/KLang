package com.qmi;

/**
 * Created by Administrator on 2015/11/17.
 */
public class WhileNode implements ASTNode {
    private ASTNode condition;
    private Block body;

    public WhileNode(ASTNode condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
