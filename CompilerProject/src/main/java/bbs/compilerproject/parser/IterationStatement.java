/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

public class IterationStatement extends Statement{
     
    private Expression e;
    private Statement s;

    public IterationStatement(Expression ex, Statement state){
        e = ex;
        s = state;
    }
    
    @Override
    public void print(){
        
    }
    
}
