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
        
    }
    
}
