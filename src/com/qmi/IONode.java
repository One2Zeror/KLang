package com.qmi;

/**
 * Created by Administrator on 2015/11/17.
 */
public class IONode implements ASTNode {
    private ASTNode contentNode;

    public IONode(ASTNode contentNode) {
        this.contentNode = contentNode;
    }

    public ASTNode getContentNode() {
        return contentNode;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
