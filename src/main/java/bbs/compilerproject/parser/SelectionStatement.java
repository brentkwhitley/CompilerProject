/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

/**
 *
 * @author Ben
 */
public class SelectionStatement extends Statement{
    
    private Expression ifExpr;
    private Statement thenStmt;
    private Statement elseStmt;
    
    public SelectionStatement(Expression exp, Statement st, Statement elseSt){
        
        ifExpr = exp;
        thenStmt = st;
        elseStmt = elseSt;
        
    }
    
    @Override
    public void print(){
        
    }
    
}
