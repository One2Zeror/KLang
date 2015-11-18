package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */
public class NumberNode implements ASTNode {
    private Number value;

    public NumberNode(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
