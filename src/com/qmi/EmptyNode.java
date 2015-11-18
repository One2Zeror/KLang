package com.qmi;

/**
 * Created by Administrator on 2015/11/18.
 */
public class EmptyNode implements ASTNode {

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
