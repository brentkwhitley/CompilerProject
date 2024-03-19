/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

public class ReturnStatement extends Statement{
    private Expression returnVal;
    
    public ReturnStatement(Expression ex){
        returnVal = ex;
    }
    @Override
    public void print(){
        //print semicolon and return
    }
    
}
