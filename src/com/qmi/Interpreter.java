package com.qmi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/12.
 */

public class Interpreter implements Visitor{
    private Map<String, Object> contex = new HashMap<>();

    public ArrayList<Object> interpret(ExpressionListNode expressionListNode) {
        ArrayList<Object> interpretResult = (ArrayList<Object>) expressionListNode.parseValue(this);
        return interpretResult;
    }

    @Override
    public Object visitNode(ExpressionListNode expressionListNode) {
        ArrayList<Object> expressionResults = new ArrayList<>();
        for (ASTNode astNode : expressionListNode.getExpressionNodes()) {
            expressionResults.add(astNode.parseValue(this));
        }
        return expressionResults;
    }

    @Override
    public Object visitNode(AssignmentNode assignmentNode) {
        Object value = assignmentNode.getOperationNode().parseValue(this);
        this.contex.put(assignmentNode.getVarible(), value);
        return value;
    }

    @Override
    public Object visitNode(OperationNode operationNode) {
        double leftValue = (double) operationNode.getLeftTermNode().parseValue(this);
        if (operationNode.getOperation() == "") {
            return leftValue;
        }
        double rightValue = (double) operationNode.getRightTermNode().parseValue(this);
        switch (operationNode.getOperation()) {
            case "+" : return leftValue + rightValue;
            case "-" : return leftValue - rightValue;
            case "*" : return leftValue * rightValue;
            case "/" : return leftValue / rightValue;
            case "%" : return leftValue % rightValue;
            default:
                try {
                    throw new Exception("Unsupported operator");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    public Object visitNode(IdentifierNode identifierNode) {
        if (this.contex.containsKey(identifierNode.getValue())) {
            return this.contex.get(identifierNode.getValue());
        }
        try {
            throw new Exception(String.format("No such varible: %s", identifierNode.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitNode(NumberNode numberNode) {
        return numberNode.getValue();
    }

}
