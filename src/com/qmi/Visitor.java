package com.qmi;

/**
 * Created by Administrator on 2015/11/13.
 */
public interface Visitor {

    Object visitNode(Block block);

    Object visitNode(ForNode forNode);

    Object visitNode(WhileNode whileNode);

    Object visitNode(SelectionStatementNode selectionStatementNode);

    Object visitNode(JumpStatementNode jumpStatementNode);

    Object visitNode(IONode ioNode);

    Object visitNode(AssignmentNode assignmentNode);

    Object visitNode(OperationNode operationNode);

    Object visitNode(TermNode termNode);

    Object visitNode(IdentifierNode identifierNode);

    Object visitNode(NumberNode numberNode);

    Object visitNode(EmptyNode emptyNode);

}
