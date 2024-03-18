/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;


public class ExpressionStatement extends Statement{
    
    Expression e;
    
    public ExpressionStatement(Expression ex){
        
        e = ex;
    }
    
    @Override
    public void print(){
        //print the semicolon
    }
    
}
