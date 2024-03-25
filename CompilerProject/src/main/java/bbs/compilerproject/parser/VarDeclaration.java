package bbs.compilerproject.parser;

import java.io.PrintWriter;

public class VarDeclaration extends Declaration {
    
    private IDExpression id;
    private NumExpression arraySize;
    
    public VarDeclaration(IDExpression id) { 
        this.id = id;
        arraySize = null;
    }
    
    public VarDeclaration(IDExpression id, NumExpression arraySize) { 
        this.id = id;
        this.arraySize = arraySize;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        if (arraySize == null) {
            String str = String.format("%stype:int", indent);
            pr.println(str);
            System.out.println(str);
        } else {
            String str = String.format("%stype:int-array", indent);
            pr.println(str);
            System.out.println(str);
        }
        
        id.print(pr, indentation);
    }
}
