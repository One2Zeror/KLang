package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */
public class OperationNode implements ASTNode{
    private ASTNode leftTermNode;
    private ASTNode rightTermNode = null;
    private String  operation = "";

    public OperationNode(ASTNode leftTermNode, ASTNode rightTermNode, String operation) {
        this.leftTermNode = leftTermNode;
        this.rightTermNode = rightTermNode;
        this.operation = operation;
    }

    public OperationNode(ASTNode leftTermNode) {
        this.leftTermNode = leftTermNode;
    }

    public ASTNode getLeftTermNode() {
        return leftTermNode;
    }

    public ASTNode getRightTermNode() {
        return rightTermNode;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
