/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;


public class AssignExpression extends Expression{
    
    private IDExpression id;
    private Expression rhs;
    
    public AssignExpression(IDExpression id, Expression rhs){
        this.id = id;
        this.rhs = rhs; 
    }
    
    @Override
    public void print(PrintWriter pr, int indentation){
        
        // print L = R
    }
}
