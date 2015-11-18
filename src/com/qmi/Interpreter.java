package com.qmi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/12.
 */

public class Interpreter implements Visitor {
    private Map<String, Object> contex = new HashMap<>();

    public void interpret(Block block) {
        block.parseValue(this);
    }

    @Override
    public Object visitNode(Block block) {
        for (ASTNode statement : block.getStatements()) {
            statement.parseValue(this);
        }
        return null;
    }

    @Override
    public Object visitNode(ForNode forNode) {
        for (forNode.getInit().parseValue(this); isTrue(forNode.getCondition().parseValue(this)); forNode.getModify().parseValue(this)) {
            for (ASTNode statement : forNode.getBody().getStatements()) {
                if (statement instanceof JumpStatementNode) {
                    if (((JumpStatementNode) statement).getType().equals("continue")) {
                        break;
                    }
                    if (((JumpStatementNode) statement).getType().equals("break")) {
                        return null;
                    }
                    if (((JumpStatementNode) statement).getType().equals("return")) {
                        return null;
                    }
                }
                statement.parseValue(this);
            }
        }
        return null;
    }

    @Override
    public Object visitNode(WhileNode whileNode) {
        while (isTrue(whileNode.getCondition().parseValue(this))) {
            for (ASTNode statement : whileNode.getBody().getStatements()) {
                if (statement instanceof JumpStatementNode) {
                    if (((JumpStatementNode) statement).getType().equals("continue")) {
                        break;
                    }
                    if (((JumpStatementNode) statement).getType().equals("break")) {
                        return null;
                    }
                    if (((JumpStatementNode) statement).getType().equals("return")) {
                        return null;
                    }
                }
                statement.parseValue(this);
            }
        }
        return null;
    }

    @Override
    public Object visitNode(SelectionStatementNode selectionStatementNode) {
        if (isTrue(selectionStatementNode.getCondition().parseValue(this))) {
            selectionStatementNode.getTrueStatement().parseValue(this);
        } else {
            selectionStatementNode.getFalseStatement().parseValue(this);
        }
        return null;
    }

    @Override
    public Object visitNode(JumpStatementNode jumpStatementNode) {
        return null;
    }

    @Override
    public Object visitNode(IONode ioNode) {
        System.out.println(ioNode.getContentNode().parseValue(this));
        return null;
    }

    @Override
    public Object visitNode(AssignmentNode assignmentNode) {
        Object value = assignmentNode.getOperationNode().parseValue(this);
        this.contex.put(assignmentNode.getVarible(), value);
        return value;
    }

    @Override
    public Object visitNode(OperationNode operationNode) {
        double leftValue = (double) operationNode.getleftNode().parseValue(this);
        if (operationNode.getOperation().equals("")) {
            return leftValue;
        }
        double rightValue = (double) operationNode.getrightNode().parseValue(this);
        switch (operationNode.getOperation()) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            default:
                try {
                    Env.abort("Unsupported operator");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    public Object visitNode(TermNode termNode) {
        double leftValue = (double) termNode.getleftNode().parseValue(this);
        if (termNode.getOperation().equals("")) {
            return leftValue;
        }
        double rightValue = (double) termNode.getrightNode().parseValue(this);
        switch (termNode.getOperation()) {
            case "*":
                return leftValue * rightValue;
            case "/":
                return leftValue / rightValue;
            case "%":
                return leftValue % rightValue;
            default:
                Env.abort("Unsupported operator");
        }
        return null;
    }

    @Override
    public Object visitNode(IdentifierNode identifierNode) {
        if (this.contex.containsKey(identifierNode.getValue())) {
            return this.contex.get(identifierNode.getValue());
        }
        Env.abort(String.format("Uninitialized Varible: %s ", identifierNode.getValue()));
        return null;
    }

    @Override
    public Object visitNode(NumberNode numberNode) {
        return numberNode.getValue();
    }

    @Override
    public Object visitNode(EmptyNode emptyNode) {
        return null;
    }

    private boolean isTrue(Object o) {
        return o == Boolean.TRUE || (double) o != 0;
    }

}
