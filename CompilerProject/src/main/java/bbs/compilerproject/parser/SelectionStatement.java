/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;

/**
 *
 * @author Ben
 */
public class SelectionStatement extends Statement {
    
    private Expression condition;
    private Statement thenStmt;
    private Statement elseStmt;
    
    public SelectionStatement(Expression expr, Statement thenStmt, Statement elseStmt) {
        condition = expr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%sif", indent);       
        pr.println(str);
        System.out.println(str);
        
        indentation += 1;
        indent = "|".repeat(indentation);
        
        str = String.format("%scondition", indent);       
        pr.println(str);
        System.out.println(str);
        condition.print(pr , indentation);
        
        str = String.format("%sthen", indent);   
        pr.println(str);
        System.out.println(str);
        thenStmt.print(pr , indentation);
        
        if (elseStmt != null) {
            str = String.format("%selse", indent);       
            pr.println(str);
            System.out.println(str);
            elseStmt.print(pr, indentation - 1);
        }
    }
}
