package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;

public class NumExpression extends Expression {
    
    private int value;
    
    public NumExpression(Token token){
        value = Integer.parseInt((String)token.getTokenData());
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%svalue:%s", indent, Integer.toString(value)); 
        pr.println(str);
        System.out.println(str);
    }
}

