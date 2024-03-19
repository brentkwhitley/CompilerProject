/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;

/**
 *
 * @author Ben
 */
public class BinaryExpression extends Expression{
    
    Expression lhs; 
    Expression rhs;
    Token.TokenType operator;
    
    public BinaryExpression(Token.TokenType op, Expression l, Expression r){
        lhs = l;
        rhs = r;
        operator = op;
    } 
    
    public Token.TokenType getOperator(){
        return this.operator;
    }
    
    @Override
    public void print(){
        
    }
}

