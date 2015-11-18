package com.qmi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/12.
 */

//        KLang = StatementList
//        StatementList = (Statement)+
//        Statement =
//                    CompoundStatement
//                  | ExpressionStatement
//                  | SelectionStatement
//                  | LoopStatement
//                  | JumpStatement
//                  | IOStatement

//        CompoundStatement =
//                    '{' '}'
//                  | '{' StatementList '}'

//         ExpressionStatement = ';'  | Expression ';'
//
//         SelectionStatement =
//                  IF '(' Expression ')' '{' Statement '}' (ELSE Statement)*
//                  | SWITCH '(' Expression ')' Statement

//         LoopStatement =
//                  WHILE '(' Expression ')' statement
//                  | FOR '(' ExpressionStatement ExpressionStatement ')' Statement
//                  | FOR '(' ExpressionStatement ExpressionStatement Expression ')' Statement

//         JumpStatement =
//                  | CONTINUE ';'
//                  | BREAK ';'
//                  | RETURN ';'
//                  | RETURN expression ';'

//        IOStatement = PRINTLN Expression ';'(to be continued)

//        Expression          = Assignment | Operation
//        Assignment          = Identifier AssignmentOperator Operation
//        Operation           = Term ([+-] Term)?
//        Term                = Factor ([*/%] Factor)?
//        Factor                = Number | Identifier
//        Number              = /\d+/
//        Identifier          = /[_a-zA-Z][_a-zA-Z0-9]*/
//        Operator            = /[+\-*\/]/
//        AssignementOperator = "="
//        Terminator          = ";"

//        Statement             = IfStatement | WhileStatement
//        IfStatement           = 'if' '(' ConditionalExpression ')' Block
//        WhileStatement        = 'while' '(' ConditionalExpression ')' Block
//        ConditionalExpression = Expression ConditionalOperator Expression
//        Block                 = '{' ExpressionList '}'
//        ConditionalOperator   = /==|!=|<=|>=|<|>/


public class Parser {
    private ArrayList<Token> tokens;
    private int curTokenPos;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.curTokenPos = 0;
    }

    public ASTNode parse() {
        return parseStatementListNode();
    }

    private Block parseStatementListNode() {
        Block statementBlock = new Block();
        while (this.curTokenPos < this.tokens.size() && !isEOB(this.getToken())) {
            statementBlock.addStatement(parseStatementNode());
        }
        return statementBlock;
    }

    private ASTNode parseStatementNode() {
        if (isTerminal("{")) {
            return parseCompoundStatementNode();
        }
        if (isSelection(getToken())) {
            return parseSelectionStatementNode();
        }
        if (isLoop(getToken())) {
            return parseLoopStatementNode();
        }
        if (isJump(getToken())) {
            return parseJumpStatementNode();
        }
        if (isIO(getToken())) {
            return parseIONode();
        }
        return parseExpressionStatementNode();
    }

    private Block parseCompoundStatementNode() {
        parseTerminal("{");
        if (isTerminal("}")) {
            eatToken();
            return new Block();  //empty block
        }
        Block statementBlock = parseStatementListNode();
        parseTerminal("}");
        return statementBlock;
    }

    private ASTNode parseSelectionStatementNode() {
        parseTerminal("if");
        parseTerminal("(");
        ASTNode condition = parseExpressionNode();
        parseTerminal(")");
        ASTNode trueStatement = parseCompoundStatementNode();

        if (isTerminal("else")) {
            eatToken();  //"else"
            ASTNode falseStatement;
            if (isTerminal("if")) {
                falseStatement = parseSelectionStatementNode();
            } else {
                falseStatement = parseCompoundStatementNode();
            }
            return new SelectionStatementNode(condition, trueStatement, falseStatement);
        }
        return new SelectionStatementNode(condition, trueStatement, new EmptyNode());
    }

    private ASTNode parseLoopStatementNode() {
        if (isTerminal("while")) {
            return ParseWhileNode();
        }
        return parseForNode();
    }

    private ASTNode ParseWhileNode() {
        parseTerminal("while");
        parseTerminal("(");
        ASTNode condition = parseExpressionNode();
        parseTerminal(")");
        Block body = parseCompoundStatementNode();
        return new WhileNode(condition, body);
    }

    private ASTNode parseForNode() {
        parseTerminal("for");
        parseTerminal("(");
        ASTNode init = parseExpressionStatementNode();
        ASTNode condition = parseExpressionStatementNode();
        ASTNode modify;
        if (isTerminal(")")) {
            modify = new EmptyNode();
        } else {
            modify = parseExpressionNode();
        }
        parseTerminal(")");
        Block body = parseCompoundStatementNode();
        return new ForNode(init, condition, modify, body);
    }

    private ASTNode parseJumpStatementNode() {
        String jumpType = parseTerminal();
        parseTerminal(";");
        return new JumpStatementNode(jumpType);
    }

    private ASTNode parseIONode() {
        parseTerminal("println");
        ASTNode contentNode = parseExpressionNode();
        parseTerminal(";");
        return new IONode(contentNode);
    }

    private ASTNode parseExpressionStatementNode() {
        if (isTerminal(";"))
            return new EmptyNode();
        ASTNode expressionNode = parseExpressionNode();
        parseTerminal(";");
        return expressionNode;
    }

    private ASTNode parseExpressionNode() {
        if (lookAhead().equals("=")) {
            return parseAssignmentNode();
        }
        return parseOperationNode();
    }

    private ASTNode parseAssignmentNode() {
        String varible = parseTerminal(TokenType.IDENTIFIER);
        parseTerminal(TokenType.ASSIGN);
        ASTNode operationNode = parseOperationNode();
        return new AssignmentNode(varible, operationNode);
    }

    private ASTNode parseOperationNode() {
        ASTNode leftNode = parseTermNode();
        if (isTerminal(";")) {
            return new OperationNode(leftNode);
        }
        while (isTerminal("+") || isTerminal("-")) {
            String op = parseTerminal();
            ASTNode rightNode = parseTermNode();
            leftNode = new OperationNode(leftNode, rightNode, op);
        }
        return leftNode;
    }

    private ASTNode parseTermNode() {
        ASTNode leftNode = parseFactorNode();
        if (isTerminal(";")) {
            return new TermNode(leftNode);
        }
        while (isTerminal("*") || isTerminal("/") || isTerminal("%")) {
            String op = parseTerminal();
            ASTNode rightNode = parseFactorNode();
            leftNode = new TermNode(leftNode, rightNode, op);
        }
        return leftNode;
    }

    private ASTNode parseFactorNode() {
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
                Env.abort("You may miss )");
            }
        } else {
            Env.abort("FactorNode not match");
        }
        return new EmptyNode();
    }

    private String lookAhead() {
        return this.tokens.get(this.curTokenPos + 1).getValue();
    }

    private void eatToken() {
        this.curTokenPos++;
    }

    private String getToken() {
        return this.tokens.get(this.curTokenPos).getValue();
    }

    private String parseTerminal() {
        String value = this.tokens.get(this.curTokenPos).getValue();
        this.curTokenPos++;
        return value;
    }

    private String parseTerminal(TokenType tokenType) {
        if (this.curTokenPos >= this.tokens.size()) {
            Env.abort(String.format("Miss %s", tokenType));
        }
        if (this.tokens.get(this.curTokenPos).getType() != tokenType) {
            Env.abort("Terminal not match");
        }
        String value = this.tokens.get(this.curTokenPos).getValue();
        this.curTokenPos++;
        return value;
    }

    private void parseTerminal(String value) {
        if (this.curTokenPos >= this.tokens.size()) {
            Env.abort(String.format("Miss %s", value));
        }
        if (!this.tokens.get(this.curTokenPos).getValue().equals(value)) {
            Env.abort(String.format("Terminal not match, expect: %s, but: %s", value, this.tokens.get(this.curTokenPos).getValue()));
        }
        this.curTokenPos++;
    }

    private boolean isTerminal(TokenType tokenType) {
        if (this.curTokenPos >= this.tokens.size()) {
            return false;
        }
        return this.tokens.get(this.curTokenPos).getType() == tokenType;
    }

    private boolean isTerminal(String tokenValue) {
        if (this.curTokenPos >= this.tokens.size()) {
            return false;
        }
        return this.tokens.get(this.curTokenPos).getValue().equals(tokenValue);
    }

    private boolean isLeftBrace(String s) {
        return s.equals("{");
    }

    private boolean isSelection(String s) {
        return s.equals("if");
    }

    private boolean isLoop(String s) {
        return s.equals("while") || s.equals("for");
    }

    private boolean isJump(String s) {
        return s.equals("continue") || s.equals("break") || s.equals("return");
    }

    private boolean isIO(String s) {
        return s.equals("println");
    }

    private boolean isEOB(String token) {
        return token.equals("}");
    }
}
