package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;

public class IDExpression extends Expression {
    
    private String name;
    
    public IDExpression(Token token){
        name = (String)token.getTokenData();
    }

    public String getName(){
        return name;
    }

    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        String str = String.format("%sname:%s", indent, name);
        pr.println(str);
        System.out.println(str);
    }

    public void genLLCode(){
//is this in local or in gloable

//if in local  it is in a register
        
    }
}
