/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    public void print(PrintWriter pr){
        String str = String.format("-name:%s", name);
        pr.println(str);
        System.out.println(str);
    }
}

