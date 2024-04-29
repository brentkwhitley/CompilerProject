package bbs.compilerproject.parser;

import java.io.PrintWriter;

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
        
        register = (int)f.getTable().get(id.getName());

        Operation assignOper = new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
        Operand dst = new Operand(Operand.OperandType.REGISTER, register);
        rhs.genLLCode(f);
        Operand src = new Operand(Operand.OperandType.REGISTER, rhs.register);

        assignOper.setSrcOperand(0, src);
        assignOper.setDestOperand(0, dst);

        f.getCurrBlock().appendOper(assignOper);
        
    }
}
