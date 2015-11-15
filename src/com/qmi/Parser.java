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
    private  ArrayList<Token> tokens;
    private  int curTokenPos;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.curTokenPos = 0;
    }

    public  ASTNode parse() {
        return parseExpressionListNode();
    }

    private  ASTNode parseExpressionListNode() {
        ArrayList<ASTNode> expressionNodes = new ArrayList<>();
        while (this.curTokenPos < this.tokens.size()) {
            expressionNodes.add(parseExpressionNode());
            parseTerminal(TokenType.TERMINATOR);
        }
        return new ExpressionListNode(expressionNodes);
    }

    private  ASTNode parseExpressionNode() {
        if (this.tokens.get(this.curTokenPos + 1).getType() == TokenType.ASSIGN) {
            return parseAssignmentNode();
        }
        return parseOperationNode();
    }

    private  ASTNode parseAssignmentNode() {
        String varible = parseTerminal(TokenType.IDENTIFIER);
        parseTerminal(TokenType.ASSIGN);
        ASTNode operationNode = parseOperationNode();
        return new AssignmentNode(varible, operationNode);
    }

    private  ASTNode parseOperationNode() {
        ASTNode leftNode = parseTermNode();
        if (isTerminal(TokenType.TERMINATOR)) {
            return new OperationNode(leftNode);
        }
        while (isTerminal("+") || isTerminal("-")) {
            String op = parseTerminal();
            ASTNode rightNode = parseTermNode();
            leftNode = new OperationNode(leftNode, rightNode, op);
        }
        return leftNode;
    }

    private  ASTNode parseTermNode() {
        ASTNode leftNode = parseFactorNode();
        if (isTerminal(TokenType.TERMINATOR)) {
            return new TermNode(leftNode);
        }
        while (isTerminal("*") || isTerminal("/") || isTerminal("%")) {
            String op = parseTerminal();
            ASTNode rightNode = parseFactorNode();
            leftNode = new TermNode(leftNode, rightNode, op);
        }
        return leftNode;
    }

    private  ASTNode parseFactorNode() {
        if (isTerminal(TokenType.NUMBER)) {
            return new NumberNode(Double.valueOf(parseTerminal()));
        }
        if (isTerminal(TokenType.IDENTIFIER)) {
            return new IdentifierNode(parseTerminal());
        }
        if (isTerminal("(")) {
            eatToken();
            ASTNode operationNode = parseOperationNode();
            if (isTerminal(")")) {
                eatToken();
                return operationNode;
            } else {
                throw new Error("You may miss )");
            }
        }

        throw new Error("FactorNode not match");
    }

    private  String lookAhead() {
        return this.tokens.get(this.curTokenPos + 1).getValue();
    }

    private  void eatToken() {
        this.curTokenPos++;
    }


    private  String parseTerminal() {
        String value = this.tokens.get(this.curTokenPos).getValue();
        this.curTokenPos++;
        return value;
    }


    private  String parseTerminal(TokenType identifier) {
        if (this.curTokenPos >= this.tokens.size()) {
            throw new Error(String.format("Miss %s", identifier));
        }
        if (this.tokens.get(this.curTokenPos).getType() != identifier) {
            throw new Error("Terminal not match");
        }
        String value = this.tokens.get(this.curTokenPos).getValue();
        this.curTokenPos++;
        return value;
    }

    private  boolean isTerminal(TokenType tokenType) {
        return this.tokens.get(this.curTokenPos).getType() == tokenType;
    }

    private  boolean isTerminal(String tokenValue) {
        return this.tokens.get(this.curTokenPos).getValue().equals(tokenValue);
    }

    private  boolean isTerminal(TokenType tokenType, String tokenValue) {
        return this.tokens.get(this.curTokenPos).getType() == tokenType &&
                this.tokens.get(this.curTokenPos).getValue().equals(tokenValue);
    }
}
