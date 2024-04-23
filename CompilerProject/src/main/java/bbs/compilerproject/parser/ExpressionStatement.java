package bbs.compilerproject.parser;

import java.io.PrintWriter;

public class ExpressionStatement extends Statement {
    
    Expression expr;
    
    public ExpressionStatement(Expression expr) {
        this.expr = expr;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation){
        expr.print(pr, indentation);
    }

    public void genLLCode(){

        
    }
}
