/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;

public class ReturnStatement extends Statement {
    
    private Expression returnVal;
    
    public ReturnStatement(Expression ex) {
        returnVal = ex;
    }
    @Override
    public void print(PrintWriter pr, int indentation) {
        String indent = "-".repeat(indentation);
        
        if (returnVal == null) {
            String str = String.format("%sreturn", indent);
            pr.println(str);
            System.out.println(str);
        }   
    }
}
