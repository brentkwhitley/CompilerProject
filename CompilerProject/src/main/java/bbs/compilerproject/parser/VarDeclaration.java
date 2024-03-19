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
    private String name;
    
    //whats in the brackets
    private Expression val;
   
    private Expression rhs;
    
    public VarDeclaration(String ex, Expression e, Expression r){
        
        name = ex;
        val = e;
        rhs = r;
      
    }
    
    public String getName(){
        return name;
    }
    
    public void setRHS(Expression e){
        rhs = e;
        
    }

    @Override
    public void print(){
        //print-> name[val] = rhs    *if rhs != null, else just print left side* 
    }
}
