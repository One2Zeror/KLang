package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */
public class Token {
    private TokenType type;
    private String value;

    private static int errorLine = 1;
    private static int errorColumn = 1;

    public static int getErrorLine() {
        return errorLine;
    }

    public static void setErrorLine(int errorLine) {
        Token.errorLine = errorLine;
    }

    public static void setErrorColumn(int errorColumn) {
        Token.errorColumn = errorColumn;
    }

    public static void increaseErrorLine() {
        Token.errorLine++;

    }

    public static int getErrorColumn() {
        return errorColumn;
    }

    public static void increaseErrorColumn(int length) {
        Token.errorColumn += length;
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), value);
    }
}