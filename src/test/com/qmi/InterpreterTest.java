package test.com.qmi;

import com.qmi.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Interpreter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 13, 2015</pre>
 */
public class InterpreterTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: interpret(ExpressionListNode expressionListNode)
     */
    @Test
    public void testInterpret() throws Exception {
//TODO: Test goes here...
        String input =
//            "println 11 + 1111;" +
//                    "println 12 * 10;" +
//                    "k = 1210;" +
//                    "println a = k / 10;" +
//                    "a = 11;" +
//                    "b = a * 8;" +
//                    "b * b;" +
//                    "println a = 10 + (6 + (8 * 1 - 7));" +
//                      "i = 10;" +
//                      "while (i) {println i = i - 1;}" +
//                "for (i = 20; i; i = i - 1) { println i; continue; println 3;}" +
//                "for (i = 10; i; i = i - 1) { println i; break; println 3;}" +
//                "for (i = 5; i; i = i - 1) { println i; return; println 3;}" +
                "if (1) { println 1; } else { if (1) {} else if (0) {println 3;} else if (1) { println 11; } }" +
                        "if (0) { println 1; } else { if (1) {} else if (0) {println 3;} else if (1) { println 11; } }" +
                        "if (0) { println 1; } else { if (0) {} else if (1) {println 3;} else if (1) { println 11; } }" +
                        "if (0) { println 1; } else { if (0) {} else if (0) {println 3;} else if (1) { println 11; } }" +
                        "if (0) { println 1; } else { if (0) {} else if (0) {println 3;} else if (0) { println 11; } else { println 1111; } }";

        ArrayList<Token> tokens = Lexer.lex(input);
        Parser parser = new Parser(tokens);
        Block block = (Block) parser.parse();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(block);
    }

    /**
     * Method: visitNode(ExpressionListNode expressionListNode)
     */
    @Test
    public void testVisitNodeExpressionListNode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: visitNode(AssignmentNode assignmentNode)
     */
    @Test
    public void testVisitNodeAssignmentNode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: visitNode(OperationNode operationNode)
     */
    @Test
    public void testVisitNodeOperationNode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: visitNode(IdentifierNode identifierNode)
     */
    @Test
    public void testVisitNodeIdentifierNode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: visitNode(NumberNode numberNode)
     */
    @Test
    public void testVisitNodeNumberNode() throws Exception {
//TODO: Test goes here... 
    }


} 
