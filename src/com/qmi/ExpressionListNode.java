package com.qmi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/12.
 */
public class ExpressionListNode implements ASTNode {
    private ArrayList<ASTNode> expressionNodes;

    public ExpressionListNode(ArrayList<ASTNode> expressionNodes) {
        this.expressionNodes = expressionNodes;
    }

    public ArrayList<ASTNode> getExpressionNodes() {
        return expressionNodes;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
