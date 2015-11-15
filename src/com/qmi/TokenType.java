package com.qmi;

/**
 * Created by Administrator on 2015/11/12.
 */

/*
print(name); // name is undefined, exception is thrown

x = 5;
X = 10; // X is a different variable than x.

x += 2;

fun isEven(n) = n % 2 == 0;

if (isEven(3))
    print('Even!');
else
    print('Odd!');

fun fib(n) {
    if (n <= 1) return 1;

    fib(n - 2) + fib(n - 1);
}

// n is not accessible from here

fun sayHello(name) {
    message = 'Hello, ' + name + '!';
    print(message);
}

myName = 'Marco';

sayHello(myName); // Prints 'Hello, Marco!'
 */

public enum TokenType {
    // Token types cannot have underscores
    NUMBER("-?[0-9]+"), OPERATOR("[*/%+-]"),
    IDENTIFIER("[_a-zA-Z][_a-zA-Z0-9]*"),
    ConditionalOperator("==|!=|<=|>=|<|>"),
    ASSIGN("="),
    BRACKET("[(){}]"),
    NEWLINE("\n"),
    WHITESPACE("[ \t\f\r]+"),
    TERMINATOR(";"),
    ERROR(".");

    public final String pattern;

    private TokenType(String pattern) {
        this.pattern = pattern;
    }
}
