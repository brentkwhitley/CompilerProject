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
        String indent = "|".repeat(indentation);
        String str = String.format("%svar", indent);
        pr.println(str);
        System.out.println(str);
        
        indent = "|".repeat(indentation + 1);
        
        if (arraySize == null) {
            str = String.format("%stype:int", indent);
            pr.println(str);
            System.out.println(str);
        } else {
            str = String.format("%stype:int-array\n%ssize", indent, indent);
            pr.println(str);
            System.out.println(str);
            arraySize.print(pr, indentation + 1);
        }
        id.print(pr, indentation);
    }
}
