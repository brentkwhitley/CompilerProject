/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;

/**
 *  int x;
 * int x[];
 * 
 * int x[] = x + 8;
 * @author Ben
 */
public class VarDeclaration extends Declaration{
    
    private String name;
    private Expression arraySize;
    
    public VarDeclaration(String name, Expression expr){ 
        this.name = name;
        arraySize = expr;
    }
    
    public VarDeclaration(String name){ 
        this.name = name;
        arraySize = null;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation){
        String indent = "-".repeat(indentation);
        
        String str = String.format("%type:int", indent);
        pr.println(str);
        System.out.println(str);
        
        str = String.format("%sname:%s", indent, name);
        pr.println(str);
        System.out.println(str);
    }
}
