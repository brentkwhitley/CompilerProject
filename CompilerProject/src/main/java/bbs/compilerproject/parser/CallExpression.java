/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;

public class CallExpression extends Expression{
    
    private IDExpression id;
    private ArrayList<Expression> args;
    
    public CallExpression(IDExpression id, ArrayList<Expression> args){
        this.id = id;
        this.args = args;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation){
        
    }
}

