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
public class IDExpression extends Expression{
    
//    private VarDeclaration id;
//      private Expression rhs;
    
    public IDExpression(VarDeclaration id, Expression rhs){
        this.id = id;
        this.rhs = rhs;
    }
    public VarDeclaration getName(){
       return id;
    }
    
//    public void setRHS(Expression e){
//        val = e;
//    }

    @Override
    public void print(PrintWriter pr, int indentation){
        //print id = rhs;
    }
}
