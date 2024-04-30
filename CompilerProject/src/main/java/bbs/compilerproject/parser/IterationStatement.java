package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.lowlevel.BasicBlock;
import bbs.compilerproject.lowlevel.Function;
import bbs.compilerproject.lowlevel.Operand;
import bbs.compilerproject.lowlevel.Operand.OperandType;
import bbs.compilerproject.lowlevel.Operation;
import bbs.compilerproject.lowlevel.Operation.OperationType;

public class IterationStatement extends Statement {
     
    private Expression expr;
    private Statement stmt;

    public IterationStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%swhile", indent);
        pr.println(str);
        System.out.println(str);
        
        indentation += 1;
        indent = "|".repeat(indentation);
        
        str = String.format("%scondition", indent);
        pr.println(str);
        System.out.println(str);
        expr.print(pr, indentation);
        
        str = String.format("%sthen", indent);
        pr.println(str);
        System.out.println(str);
        stmt.print(pr, indentation);
    }

    public void genLLCode(Function f){

            BasicBlock then = new BasicBlock(f);
            BasicBlock p = new BasicBlock(f);
            
            
            expr.genLLCode(f);

            Operand stmt = new Operand(OperandType.REGISTER, this.expr.register);
            Operand zero = new Operand(OperandType.INTEGER, 0);
            Operand post = new Operand(OperandType.BLOCK, p.getBlockNum());
           
            Operation branch = new Operation(OperationType.BEQ, f.getCurrBlock());
        
            branch.setSrcOperand(0, stmt);
            branch.setSrcOperand(1, zero);
            branch.setSrcOperand(2, post);

            f.getCurrBlock().appendOper(branch);
            f.appendToCurrentBlock(then);
            f.setCurrBlock(then);

            this.stmt.genLLCode(f);

            //loop
            expr.genLLCode(f);

            Operand val = new Operand(OperandType.REGISTER, this.expr.register);
            Operand jumpBlock = new Operand(OperandType.BLOCK, then.getBlockNum());

            Operation jmp = new Operation(OperationType.BNE, f.getCurrBlock());

            jmp.setSrcOperand(0, val);
            jmp.setSrcOperand(1, zero);
            jmp.setSrcOperand(2, jumpBlock);


            f.getCurrBlock().appendOper(jmp);
            f.appendToCurrentBlock(p);
            f.setCurrBlock(p);

    }
}
