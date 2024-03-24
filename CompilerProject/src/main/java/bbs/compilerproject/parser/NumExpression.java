/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;

/**
 *
 * @author Ben
 */
public class NumExpression extends Expression {
    
    private int val;
    
    public NumExpression(Token token){
        val = Integer.parseInt((String)token.getTokenData());
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        
    }
}

