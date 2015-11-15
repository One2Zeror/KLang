package com.qmi;

import jdk.nashorn.internal.ir.Assignment;

import java.beans.Expression;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/12.
 */

//        ExpressionList      = (Expression Terminator)+
//        Expression          = Assignment | Operation
//        Assignment          = Identifier AssignmentOperator Operation
//        Operation           = Term (Operator Term)?
//        Term                = Number | Identifier
//        Number              = /\d+/
//        Identifier          = /[_a-zA-Z][_a-zA-Z0-9]*/
//        Operator            = /[+\-*\/]/
//        AssignementOperator = "="
//        Terminator          = ";"

public class Parser {
    private static ArrayList<Token> tokens;
    private static int curTokenPos = 0;

    public static void setTokens(ArrayList<Token> tokens) {
        Parser.tokens = tokens;
    }

    public static ASTNode parse() {
        return parseExpressionListNode();
    }

    private static ASTNode parseExpressionListNode() {
        ArrayList<ASTNode> expressionNodes = new ArrayList<>();
        while (curTokenPos < tokens.size()) {
            expressionNodes.add(parseExpressionNode());
            parseTerminal(TokenType.TERMINATOR);
        }
        return new ExpressionListNode(expressionNodes);
    }

    private static ASTNode parseExpressionNode() {
        if (tokens.get(curTokenPos + 1).getType() == TokenType.ASSIGN) {
            return parseAssignmentNode();
        }
        return parseOperationNode();
    }

    private static ASTNode parseAssignmentNode() {
        String varible = parseTerminal(TokenType.IDENTIFIER);
        parseTerminal(TokenType.ASSIGN);
        ASTNode operationNode = parseOperationNode();
        return new AssignmentNode(varible, operationNode);
    }

    private static OperationNode parseOperationNode() {
        ASTNode leftTermNode = parseTermNode();
        if (checkTerminal(TokenType.TERMINATOR)) {
            return new OperationNode(leftTermNode);
        }
        String op = parseTerminal(TokenType.OPERATOR);
        ASTNode rightNode = parseTermNode();
        return new OperationNode(leftTermNode, rightNode, op);
    }

    private static ASTNode parseTermNode() {
        if (tokens.get(curTokenPos).getType() == TokenType.NUMBER) {
            return new NumberNode(Double.valueOf(parseTerminal(TokenType.NUMBER)));
        }
        if (tokens.get(curTokenPos).getType() == TokenType.IDENTIFIER) {
            return new IdentifierNode(parseTerminal(TokenType.IDENTIFIER));
        }
        try {
            throw new Exception("TermNode not match");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseTerminal(TokenType identifier) {
        if (curTokenPos >= tokens.size()) {
            try {
                throw new Exception(String.format("Miss %s", identifier));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (tokens.get(curTokenPos).getType() != identifier) {
            try {
                throw new Exception("Terminal not match");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String varible = tokens.get(curTokenPos).getValue();
        curTokenPos++;
        return varible;
    }

    private static boolean checkTerminal(TokenType identifier) {
        return tokens.get(curTokenPos).getType() == identifier;
    }
}
