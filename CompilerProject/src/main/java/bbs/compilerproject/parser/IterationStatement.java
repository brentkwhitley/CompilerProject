package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.lowlevel.Function;

public class IterationStatement extends Statement {
     
    private Expression expr;
    private Statement stmt;

    public IterationStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%swhile", indent);
        pr.println(str);
        System.out.println(str);
        
        indentation += 1;
        indent = "|".repeat(indentation);
        
        str = String.format("%scondition", indent);
        pr.println(str);
        System.out.println(str);
        expr.print(pr, indentation);
        
        str = String.format("%sthen", indent);
        pr.println(str);
        System.out.println(str);
        stmt.print(pr, indentation);
    }

    public void genLLCode(Function f){

        

        
    }
}
