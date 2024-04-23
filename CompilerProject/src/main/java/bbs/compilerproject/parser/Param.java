package bbs.compilerproject.parser;

import java.io.PrintWriter;

public class Param {
    
    private IDExpression id;
    private boolean isArray;
    private int indentation = 2;
    
    public Param(IDExpression id, boolean isArray) {
        this.id = id;
        this.isArray = isArray;
    }
    
    public void print(PrintWriter pr) {
        String indent = "|".repeat(indentation + 1);
        
        // param type can only be int
        if (isArray == false) {
            String str = String.format("||param\n%stype:int", indent);
            pr.println(str);
            System.out.println(str);
        } else {
            String str = String.format("||param\n%stype:int-array", indent);
            pr.println(str);
            System.out.println(str);
        }
        
        id.print(pr, indentation);
    }

    public void genLLCode(){

        
    }
}
