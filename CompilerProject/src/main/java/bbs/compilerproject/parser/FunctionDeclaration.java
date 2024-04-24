package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import bbs.compilerproject.lowlevel.*;



public class FunctionDeclaration extends Declaration {

    private String type;
    private IDExpression id;
    private ArrayList<Param> params;
    private Statement compoundStmt;
    
    private int indentation;
    
    public FunctionDeclaration(String type, IDExpression id, 
            ArrayList<Param> paramsList, Statement compoundStmt){
        this.type = type;
        this.id = id;
        params = paramsList;
        this.compoundStmt = compoundStmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        String indent = "-".repeat(indentation);
        
        // print fun-decl
        String str = String.format("fun\n|type:%s", type);
        pr.println(str);
        System.out.println(str);
        
        // print function name
        id.print(pr, 0); // indentation should be 0 here
        
        if (params != null) {
            str = String.format("|params");
            pr.println(str);
            System.out.println(str);
            
            // print each paramter type and name
            for (int i = 0; i < params.size(); i++) {
                params.get(i).print(pr);
            }
        }
        
        // print compoundStmt
        if (compoundStmt != null) {
            str = String.format("|body");
            pr.println(str);
            System.out.println(str);
            compoundStmt.print(pr, indentation + 1);
        }
    }

    public CodeItem genLLCode(){
        Function func = null;

        if(params == null){
            if(type == "void"){
                func = new Function(0, type);
            }
            else{
                func = new Function(1, type);
            }
        }
        else{
            // TODO: create function with params, params are linked list, update local table
        }

        func.createBlock0();

        BasicBlock b = new BasicBlock(func);

        func.setCurrBlock(b);
        
        compoundStmt.genLLCode();

        func.appendBlock(func.getReturnBlock());

        if(func.getFirstUnconnectedBlock() != null){

            func.appendBlock(func.getFirstUnconnectedBlock());
        }

        return func;
    }
}