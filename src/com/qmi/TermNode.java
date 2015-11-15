package com.qmi;

/**
 * Created by Administrator on 2015/11/15.
 */
public class TermNode implements ASTNode {
    private ASTNode leftNode;
    private ASTNode rightNode = null;
    private String  operation = "";

    public TermNode(ASTNode leftNode, ASTNode rightNode, String operation) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operation = operation;
    }

    public TermNode(ASTNode leftNode) {
        this.leftNode = leftNode;
    }

    public ASTNode getleftNode() {
        return leftNode;
    }

    public ASTNode getrightNode() {
        return rightNode;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
