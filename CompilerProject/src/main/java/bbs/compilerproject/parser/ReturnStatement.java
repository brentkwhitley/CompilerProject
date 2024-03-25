package bbs.compilerproject.parser;

import java.io.PrintWriter;

public class ReturnStatement extends Statement {
    
    private Expression returnVal;
    
    public ReturnStatement(Expression ex) {
        returnVal = ex;
    }
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        if (returnVal != null) {
            String str = String.format("%sreturn", indent);
            pr.println(str);
            System.out.println(str);
            
            returnVal.print(pr, indentation);
        } else {
            String str = String.format("%sreturn", indent);
            pr.println(str);
            System.out.println(str);
        }
    }
}
