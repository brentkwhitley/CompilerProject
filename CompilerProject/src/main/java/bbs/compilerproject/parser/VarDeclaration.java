package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.compiler.CMinusCompiler;
import bbs.compilerproject.lowlevel.*;

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


    
    @SuppressWarnings("unchecked")
    public void genLLCode(Function f){

       if(f.getTable().containsKey(id.getName())){
            throw new CodeGenerationException("Local var already exists");
       }
       
        id.setRegister(f.getNewRegNum());

        f.getTable().put(id.getName(), id.getRegNum());
       
    
    }


    @SuppressWarnings("unchecked")
    public CodeItem genLLCode(){
        
        if(CMinusCompiler.globalHash.containsKey(id)){
            throw new CodeGenerationException("Global var already exists");
       }
        else{   
            
            Data data = new Data(1, id.getName());
            CMinusCompiler.globalHash.put(id.getName(), id.getName());
            return data;
        }
    }
}
