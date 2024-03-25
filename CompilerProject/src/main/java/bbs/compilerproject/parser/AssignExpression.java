package bbs.compilerproject.parser;

import java.io.PrintWriter;

public class AssignExpression extends Expression {
    
    private IDExpression id;
    private Expression rhs;
    
    public AssignExpression(IDExpression id, Expression rhs) {
        this.id = id;
        this.rhs = rhs;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%soperator:=", indent); 
        pr.println(str);
        System.out.println(str);
        
        id.print(pr, indentation);
        rhs.print(pr, indentation);
    }
}
