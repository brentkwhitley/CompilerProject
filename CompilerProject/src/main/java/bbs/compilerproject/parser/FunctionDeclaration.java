/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class FunctionDeclaration extends Declaration{

    private String type;
    private String name;
    private ArrayList<Param> params;
    private Statement compoundStmt;
    
    private int indentation;
    
    public FunctionDeclaration(String type, String name, ArrayList<Param> paramsList, Statement compoundStmt){
        this.type = type;
        this.name = name;
        params = paramsList;
        this.compoundStmt = compoundStmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation){
        // print fun-decl
        String str = String.format("fun-decl\n-type:%s\n-name:%s", type, name);
        pr.println(str);
        System.out.println(str);
        
        // TODO: print params list if not void
        if (params != null) {
            
            // print params
            str = String.format("-params");
            pr.println(str);
            System.out.println(str);
            
            for (int i = 0; i < params.size(); i++) {
                str = String.format("--param");
                pr.println(str);
                System.out.println(str);

                // print param type and name
                str = String.format("---type:int\n---name:%s", params.get(i));
                pr.println(str);
                System.out.println(str);
            }
        }
    }
}