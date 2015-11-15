package test.com.qmi; 

import com.qmi.*;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;

/** 
* Interpreter Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 13, 2015</pre> 
* @version 1.0 
*/ 
public class InterpreterTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: interpret(ExpressionListNode expressionListNode) 
* 
*/ 
@Test
public void testInterpret() throws Exception { 
//TODO: Test goes here...
    String input =
            //"11 + 1111;" +
            //"12 * 10;" +
            "k = 1210;" +
            "a = k / 10;" +
            "a = 11;" +
            "b = a * 8;" +
            "b * b;"
            ;
    ArrayList<Token> tokens = Lexer.lex(input);
    Parser.setTokens(tokens);
    ExpressionListNode expressionListNode = (ExpressionListNode) Parser.parse();
    Interpreter interpreter = new Interpreter();
    ArrayList<Object> interpretResults = interpreter.interpret(expressionListNode);

    for (Object result : interpretResults) {
        System.out.println(result);
    }
} 

/** 
* 
* Method: visitNode(ExpressionListNode expressionListNode) 
* 
*/ 
@Test
public void testVisitNodeExpressionListNode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: visitNode(AssignmentNode assignmentNode) 
* 
*/ 
@Test
public void testVisitNodeAssignmentNode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: visitNode(OperationNode operationNode) 
* 
*/ 
@Test
public void testVisitNodeOperationNode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: visitNode(IdentifierNode identifierNode) 
* 
*/ 
@Test
public void testVisitNodeIdentifierNode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: visitNode(NumberNode numberNode) 
* 
*/ 
@Test
public void testVisitNodeNumberNode() throws Exception { 
//TODO: Test goes here... 
} 


} 
