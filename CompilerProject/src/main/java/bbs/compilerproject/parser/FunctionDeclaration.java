package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import bbs.compilerproject.lowlevel.*;



public class FunctionDeclaration extends Declaration {

    private String type;
    private String id;
    private ArrayList<Param> params;
    private Statement compoundStmt;
    

    
    public FunctionDeclaration(String type, String id, 
             ArrayList<Param> paramsList, Statement compoundStmt){
        this.type = type;
        this.id = id;
        params = paramsList;
        this.compoundStmt = compoundStmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        
        
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
    
    @SuppressWarnings("unchecked")
    public CodeItem genLLCode(){

        Function func;

        //get return type
        if(id == "void"){
            func = new Function(0, id);
        }
        else{
            func = new Function(1, id);
        }

        FuncParam head = null;
        FuncParam tail = null;

        if(params != null){
            for(Param p : params){

                //put in local sym table
                if(func.getTable().containsKey(p.getName())){
                        throw new CodeGenerationException("Local var already exists");
                }
                else{
                        func.getTable().put(p.getName(), func.getNewRegNum());
                }
                //makes func param out of it
                FuncParam k = new FuncParam(1, p.getName());

                //append func param to linked list of func params
                if(head == null){

                    head = tail = k;
                }
                else{

                    tail.setNextParam(k);
                    tail = tail.getNextParam();

                }
                
            }
        }
        
        //put list in first param
        func.setFirstParam(head);

        func.createBlock0();

        BasicBlock b = new BasicBlock(func);

        func.appendBlock(b);

        func.setCurrBlock(b);
        
        compoundStmt.genLLCode(func);

        func.appendBlock(func.getReturnBlock());

        if(func.getFirstUnconnectedBlock() != null){

            func.appendBlock(func.getFirstUnconnectedBlock());
        }

        return func;
    }
}