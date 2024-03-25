package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;

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
        String str = String.format("fun-decl\n|type:%s", type);
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
}