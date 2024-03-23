/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

public class IterationStatement extends Statement{
     
    private Expression expr;
    private Statement stmt;

    public IterationStatement(Expression expr, Statement stmt){
        this.expr = expr;
        this.stmt = stmt;
    }
    
    @Override
    public void print(){
        
    }
    
}
