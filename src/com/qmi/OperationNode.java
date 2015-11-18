package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */
public class OperationNode implements ASTNode {
    private ASTNode leftNode;
    private ASTNode rightNode = null;
    private String operation = "";

    public OperationNode(ASTNode leftNode, ASTNode rightNode, String operation) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operation = operation;
    }

    public OperationNode(ASTNode leftNode) {
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
