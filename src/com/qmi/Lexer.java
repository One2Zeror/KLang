package com.qmi;

import com.sun.corba.se.impl.oa.toa.TOA;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/12.
 */


//        KLang = (Instruction)+
//        Instruction    = (Expression Terminator) | Statement

//        ExpressionList      = (Expression Terminator)+
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


public class Lexer {
    public static ArrayList<Token> lex(String input) {
        ArrayList<Token> tokens = new ArrayList<>();

        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // matching tokens
        Matcher matcher = tokenPatterns.matcher(input);

        matchIteration:
        while (matcher.find()) {
            if (matcher.group(TokenType.NEWLINE.name()) != null) {
                //tokens.add(new Token(TokenType.NEWLINE, matcher.group(TokenType.NEWLINE.name())));
                Token.increaseErrorLine();
                Token.setErrorColumn(1);
                continue ;
            }
            if (matcher.group(TokenType.WHITESPACE.name()) != null) {
                Token.increaseErrorColumn(matcher.group(TokenType.WHITESPACE.name()).length());
                //tokens.add(new Token(TokenType.WHITESPACE, matcher.group(TokenType.WHITESPACE.name())));
                continue ;
            }
            if (matcher.group(TokenType.ERROR.name()) != null) {
                throw new Error(String.format("Error: line %d, column %d, Not match: %s", Token.getErrorLine(),
                        Token.getErrorColumn(), matcher.group(TokenType.ERROR.name())));
            }
            for (TokenType tk : TokenType.values()) {
                if (matcher.group(tk.name()) != null) {
                    Token.increaseErrorColumn(matcher.group(tk.name()).length());
                    tokens.add(new Token(tk, matcher.group(tk.name())));
                    continue matchIteration;
                }
            }
        }

        return tokens;
    }
}
