/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

/**
 *
 * @author Ben
 */
public class VarDeclaration extends Declaration{
    
    //name of expression
    private Expression id;
    
    //whats in the brackets
    private Expression val;
   
    
    public VarDeclaration(Expression ex, Expression e){
        
        id = ex;
        val = e;
      
    }
    
    

    @Override
    public void print(){
        //print -> type id [ arraySize ] followingExpression
    }
}
