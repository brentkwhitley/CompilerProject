package bbs.compilerproject.parser;

import bbs.compilerproject.lowlevel.*;
import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;


public class NumExpression extends Expression {
    
    private int value;
    
    public NumExpression(Token token){
        value = Integer.parseInt((String)token.getTokenData());
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%svalue:%s", indent, Integer.toString(value)); 
        pr.println(str);
        System.out.println(str);
    }

    public void genLLCode(bbs.compilerproject.lowlevel.Function f ){
        register = f.getNewRegNum();
        Operand dest = new Operand(Operand.OperandType.REGISTER, register);
        Operand val = new Operand(Operand.OperandType.INTEGER, value);
        Operation assignOperand = new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());

        assignOperand.setDestOperand(0, dest);
        assignOperand.setSrcOperand(0, val);

        f.getCurrBlock().appendOper(assignOperand);
    }
}

