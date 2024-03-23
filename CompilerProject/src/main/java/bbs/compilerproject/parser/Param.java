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
public class Param {
    
    // only possible param type is int
    private IDExpression id;
    private boolean brackets;
    private int indentation = 2;
    
    public Param(IDExpression id, boolean brackets){
        this.id = id;
        this.brackets = brackets;
    }
    
//    public String getName() {
//        return id;
//    }
    
    public void print(PrintWriter pr, int indentation){
        
        id.print(pr, indentation);
    }
}
