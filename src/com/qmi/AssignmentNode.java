package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */
public class AssignmentNode implements ASTNode {
    private String varible;
    private ASTNode operationNode;

    public AssignmentNode(String varible, ASTNode operationNode) {
        this.varible = varible;
        this.operationNode = operationNode;
    }

    public String getVarible() {
        return varible;
    }

    public ASTNode getOperationNode() {
        return operationNode;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
