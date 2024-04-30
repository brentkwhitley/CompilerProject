package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;
import bbs.compilerproject.compiler.CMinusCompiler;
import bbs.compilerproject.lowlevel.*;
import java.io.PrintWriter;

public class IDExpression extends Expression {
    
    private String name;
    
    public IDExpression(Token token){
        name = (String)token.getTokenData();
    }

    public String getName(){
        return name;
    }
    public void setRegister(int a){
        register = a;
    }

    public int getRegNum(){
        return register;
    }

    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        String str = String.format("%sname:%s", indent, name);
        pr.println(str);
        System.out.println(str);
    }

    
    public void genLLCode(Function f){


        if(f.getTable().containsKey(name)){

            register = (int)f.getTable().get(name);
        }
        else if(CMinusCompiler.globalHash.containsKey(name)){

            register = f.getNewRegNum();

            Operand ident = new Operand(Operand.OperandType.STRING, name);
            Operand src = new Operand(Operand.OperandType.REGISTER, register);
            Operation load = new Operation(Operation.OperationType.LOAD_I, f.getCurrBlock());

            load.setDestOperand(0, src);
            load.setSrcOperand(0, ident);

            f.getCurrBlock().appendOper(load);
            
        }
        else{

            throw new CodeGenerationException("IDExpression is not defined");
        }

    }
}
