package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.compiler.CMinusCompiler;
import bbs.compilerproject.lowlevel.Function;
import bbs.compilerproject.lowlevel.Operand;
import bbs.compilerproject.lowlevel.Operation;

public class AssignExpression extends Expression {
    
    private IDExpression id;
    private Expression rhs;

    
    public AssignExpression(IDExpression id, Expression rhs) {
        this.id = id;
        this.rhs = rhs;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%sassign", indent); 
        pr.println(str);
        System.out.println(str);
        
        id.print(pr, indentation);
        rhs.print(pr, indentation);
    }

    public void genLLCode(Function f){
        

        //id.genLLCode(f);

        rhs.genLLCode(f);
        String str = id.getName();

        if(f.getTable().containsKey(str)){

            Operation assignOper = new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
            int destReg = (int)f.getTable().get(str);
            Operand dst = new Operand(Operand.OperandType.REGISTER, destReg);
            register = destReg;
        
            Operand src = new Operand(Operand.OperandType.REGISTER, rhs.register);

            assignOper.setSrcOperand(0, src);
            assignOper.setDestOperand(0, dst);

            f.getCurrBlock().appendOper(assignOper);
        }
        else if(CMinusCompiler.globalHash.containsKey(str)){
            Operation storeOper = new Operation(Operation.OperationType.STORE_I, f.getCurrBlock());
            Operand src = new Operand(Operand.OperandType.REGISTER, rhs.register);
            Operand src2 = new Operand(Operand.OperandType.STRING, str);

            register = rhs.register;
            
            storeOper.setSrcOperand(0, src);
            storeOper.setSrcOperand(1, src2);

            f.getCurrBlock().appendOper(storeOper);

        }
        else{
            throw new CodeGenerationException("Var not found");

        }
        
    }
}
