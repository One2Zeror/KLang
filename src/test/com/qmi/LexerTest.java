package test.com.qmi; 

import com.qmi.Lexer;
import com.qmi.ProgramLoader;
import com.qmi.Token;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;

/** 
* Lexer Tester. 
* 
* @author <Authors name> 
* @since <pre>Ê®Ò»ÔÂ 12, 2015</pre> 
* @version 1.0 
*/ 
public class LexerTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: lex(String input) 
* 
*/ 
@Test
public void testLex() throws Exception { 
//TODO: Test goes here...
    String input = ProgramLoader.load("D:\\JavaProject\\KLang\\src\\test\\com\\qmi\\test.kl");

    ArrayList<Token> tokens = Lexer.lex(input);
    for (Token token : tokens)
        System.out.println(token);
}

} 
