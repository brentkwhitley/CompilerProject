


package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;


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
        indentation += 1;
        String indent = "-".repeat(indentation);
        
        if (declList != null) {
            for (int i = 0; i < declList.size(); i++) {
                String str = String.format("%svar-decl", indent);
                pr.println(str);
                System.out.println(str);
                declList.get(i).print(pr, indentation);
            }
        }
        
        // repeat similar code for statement list
        if (stmtList != null) {
            for (int i = 0; i < stmtList.size(); i++) {
                stmtList.get(i).print(pr, indentation);
            }
        }
    }
}
