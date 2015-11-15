package com.qmi;

/**
 * Created by Administrator on 2015/11/13.
 */
public interface Visitor {

    Object visitNode(AssignmentNode assignmentNode);

    Object visitNode(ExpressionListNode expressionListNode);

    Object visitNode(IdentifierNode identifierNode);

    Object visitNode(NumberNode numberNode);

    Object visitNode(OperationNode operationNode);

}
