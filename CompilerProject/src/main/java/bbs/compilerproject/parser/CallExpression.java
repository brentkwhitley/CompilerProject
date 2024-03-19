/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.util.ArrayList;

public class CallExpression extends Expression{
    private Expression id;
    private ArrayList<Expression> args;
    private Expression simpEx;
    
    
    public CallExpression(Expression ident, ArrayList<Expression> a, Expression ex){
        id = ident;
        args = a;
        simpEx = ex;
    }
    
    @Override
    public void print(){
        
    }
}

