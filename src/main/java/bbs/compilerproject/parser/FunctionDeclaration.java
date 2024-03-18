/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class FunctionDeclaration extends Declaration{

    private Expression id;
    private ArrayList<Param> params;
    private Statement compoundstmt;
    
    public FunctionDeclaration(Expression k, ArrayList<Param> list, Statement compdstmt ){
        id = k;
        params = list;
        compoundstmt = compdstmt;
    }
    
    @Override
    public void print(){
        
    }
}