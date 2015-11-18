package com.qmi;

/**
 * Created by Administrator on 2015/11/17.
 */
public class JumpStatementNode implements ASTNode {
    private String type;

    public JumpStatementNode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
