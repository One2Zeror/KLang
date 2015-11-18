package com.qmi;

/**
 * Created by Administrator on 2015/11/17.
 */
public class ForNode implements ASTNode {
    private ASTNode init;
    private ASTNode condition;
    private ASTNode modify;
    private Block Body;

    public ForNode(ASTNode init, ASTNode condition, ASTNode modify, Block body) {
        this.init = init;
        this.condition = condition;
        this.modify = modify;
        Body = body;
    }

    public ASTNode getInit() {
        return init;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getModify() {
        return modify;
    }

    public Block getBody() {
        return Body;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
