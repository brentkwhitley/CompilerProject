package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.lowlevel.Function;
import bbs.compilerproject.lowlevel.Operand;
import bbs.compilerproject.lowlevel.Operation;
import bbs.compilerproject.lowlevel.Operand.OperandType;
import bbs.compilerproject.lowlevel.Operation.OperationType;

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

    public void genLLCode(Function f){

        if(returnVal != null){
            
            returnVal.genLLCode(f);

            Operand rhs = new Operand(OperandType.REGISTER, returnVal.register);
            Operand ret = new Operand(OperandType.MACRO, "RetReg");
            Operation result = new Operation(OperationType.ASSIGN, f.getCurrBlock());

            result.setSrcOperand(0, rhs);
            result.setDestOperand(0, ret);

            f.getCurrBlock().appendOper(result);
    
        }

         //jump to next block
         Operation jmp = new Operation(OperationType.JMP, f.getCurrBlock());
         Operand srcs = new Operand(OperandType.BLOCK, f.getReturnBlock().getBlockNum());
         jmp.setSrcOperand(0, srcs);
         
         f.getCurrBlock().appendOper(jmp);
    }
}
