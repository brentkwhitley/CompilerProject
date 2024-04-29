package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import bbs.compilerproject.lowlevel.*;;

public class CompoundStatement extends Statement {
    
    private ArrayList<Declaration> declList;
    private ArrayList<Statement> stmtList;
    
    public CompoundStatement() {
        declList = null;
        stmtList = null;
    }
    
    public CompoundStatement(ArrayList<Declaration> declList, ArrayList<Statement> stmtList) {
        this.declList = declList;
        this.stmtList = stmtList;
    }    
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        String indent = "|".repeat(indentation);
        
        if (declList != null) {
            for (int i = 0; i < declList.size(); i++) {
                declList.get(i).print(pr, indentation + 1);
            }
        }
        
        if (stmtList != null) {
            for (int i = 0; i < stmtList.size(); i++) {
                stmtList.get(i).print(pr, indentation);
            }
        }
    }

    public void genLLCode(Function f){
        for (int i = 0; i < declList.size(); i++) {

            declList.get(i).genLLCode(f);
            
        }

        for (int i = 0; i < stmtList.size(); i++) {
        
            stmtList.get(i).genLLCode(f);
            
        }
        
    }
}
