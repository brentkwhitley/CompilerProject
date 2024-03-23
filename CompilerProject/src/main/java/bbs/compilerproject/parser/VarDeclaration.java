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
    
    private IDExpression id;
    private NumExpression arraySize;
    
    public VarDeclaration(IDExpression id){ 
        this.id = id;
        arraySize = null;
    }
    
    public VarDeclaration(IDExpression id, NumExpression arraySize){ 
        this.id = id;
        this.arraySize = arraySize;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation){
        String indent = "-".repeat(indentation);
        
        String str = String.format("%type:int", indent);
        pr.println(str);
        System.out.println(str);
        
        // TODO: print id
        id.print(pr, indentation);
        
//        str = String.format("%sname:%s", indent, name);
//        pr.println(str);
//        System.out.println(str);
    }
}
