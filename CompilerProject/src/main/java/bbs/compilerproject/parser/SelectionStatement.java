package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.lang.management.OperatingSystemMXBean;

import bbs.compilerproject.compiler.CMinusCompiler;
import bbs.compilerproject.lowlevel.BasicBlock;
import bbs.compilerproject.lowlevel.Function;
import bbs.compilerproject.lowlevel.Operand;
import bbs.compilerproject.lowlevel.Operation;
import bbs.compilerproject.lowlevel.Operand.OperandType;
import bbs.compilerproject.lowlevel.Operation.OperationType;

public class SelectionStatement extends Statement {
    
    private Expression condition;
    private Statement thenStmt;
    private Statement elseStmt;
    
    public SelectionStatement(Expression expr, Statement thenStmt, Statement elseStmt) {
        condition = expr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%sif", indent);       
        pr.println(str);
        System.out.println(str);
        
        indentation += 1;
        indent = "|".repeat(indentation);
        
        str = String.format("%scondition", indent);       
        pr.println(str);
        System.out.println(str);
        condition.print(pr , indentation);
        
        str = String.format("%sthen", indent);   
        pr.println(str);
        System.out.println(str);
        thenStmt.print(pr , indentation);
        
        if (elseStmt != null) {
            str = String.format("%selse", indent);       
            pr.println(str);
            System.out.println(str);
            elseStmt.print(pr, indentation - 1);
        }
    }

    public void genLLCode(Function f){

        if(elseStmt == null){
            //make 2 blocks
            BasicBlock then = new BasicBlock(f);
            BasicBlock p = new BasicBlock(f);
            
            //gencode expression
            condition.genLLCode(f);

            Operand stmt = new Operand(OperandType.REGISTER, this.condition.register);
            Operand zero = new Operand(OperandType.REGISTER, 0);
            Operand post = new Operand(OperandType.BLOCK, p.getBlockNum());
            
            Operation branch = new Operation(OperationType.BEQ, f.getCurrBlock());

            branch.setSrcOperand(0, stmt);
            branch.setSrcOperand(1, zero);
            branch.setSrcOperand(2, post);

            f.getCurrBlock().appendOper(branch);

            f.appendBlock(then);

            f.setCurrBlock(then);

            thenStmt.genLLCode(f);

            f.appendBlock(p);

            f.setCurrBlock(p);

        }
        else{
            //make 3 blocks
            BasicBlock thenBlock = new BasicBlock(f);
            BasicBlock elseBlock = new BasicBlock(f);
            BasicBlock postBlock = new BasicBlock(f);

            condition.genLLCode(f);

            Operand stmt = new Operand(OperandType.REGISTER, this.condition.register);
            Operand zero = new Operand(OperandType.REGISTER, 0);
            Operand post = new Operand(OperandType.BLOCK, elseBlock.getBlockNum());
            
            Operation branch = new Operation(OperationType.BEQ, f.getCurrBlock());

            branch.setSrcOperand(0, stmt);
            branch.setSrcOperand(1, zero);
            branch.setSrcOperand(2, post);

            f.getCurrBlock().appendOper(branch);

            f.appendBlock(thenBlock);

            f.setCurrBlock(thenBlock);

            thenStmt.genLLCode(f);

            f.appendBlock(postBlock);

            f.setCurrBlock(elseBlock);

            elseStmt.genLLCode(f);

            Operation jmp = new Operation(OperationType.JMP, f.getCurrBlock());

            Operand jmpPost = new Operand(OperandType.BLOCK, postBlock.getBlockNum());

            jmp.setSrcOperand(0, jmpPost);

            f.getCurrBlock().appendOper(jmp);

            f.appendUnconnectedBlock(elseBlock);

            f.setCurrBlock(postBlock);

        }

        
    }
}
