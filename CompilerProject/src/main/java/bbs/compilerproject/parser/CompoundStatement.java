


package bbs.compilerproject.parser;

import java.util.ArrayList;


public class CompoundStatement extends Statement{
    
    private ArrayList<Declaration> decls;
    private ArrayList<Statement> statements;
    
    public CompoundStatement(ArrayList<Declaration> declList, ArrayList<Statement> stmtList){
        decls = declList;
        statements = stmtList;
    }    
    
    @Override
    public void print(){
        //print start and end { } no matter what
    }
    
}
