/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;

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
}
