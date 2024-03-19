/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

/**
 *
 * @author Ben
 */
public class IDExpression extends Expression{
    
    private String name;
    private Expression arraySize;
    
 
    
    public IDExpression(String n, Expression e){
        name = n;
        arraySize = e;
   
    }
    
    
    public String getName(){
        return name;
    }
    
    @Override
    public void print(){
        
    }
}

