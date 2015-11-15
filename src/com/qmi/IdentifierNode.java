package com.qmi;

/**
 * Created by Administrator on 2015/11/13.
 */
public class IdentifierNode implements ASTNode {
    private String value;

    public IdentifierNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
