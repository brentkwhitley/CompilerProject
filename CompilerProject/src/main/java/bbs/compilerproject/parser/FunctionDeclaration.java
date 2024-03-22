/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class FunctionDeclaration extends Declaration{

    private String type;
    private Expression id;
    private ArrayList<Param> params;
    private Statement compoundstmt;
    
    public FunctionDeclaration(String type, Expression k, ArrayList<Param> list, Statement compdstmt ){
        this.type = type;
        id = k;
        params = list;
        compoundstmt = compdstmt;
    }
    
    @Override
    public void print(PrintWriter pr){
        // print fun-decl with type and name
        String str = String.format("fun-decl\n-type:%s", type);
        pr.println(str);
        System.out.println(str);
            
        // print id expr name
        id.print(pr);
            
        // print params list
        str = String.format("-params");
        pr.println(str);
        System.out.println(str);
        // TODO: print params list if not void
    }
}