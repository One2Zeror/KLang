package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */
interface ASTNode {
    public Object parseValue(Visitor visitor);
}
