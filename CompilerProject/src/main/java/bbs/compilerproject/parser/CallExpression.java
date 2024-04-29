package bbs.compilerproject.parser;

import java.io.ObjectStreamException;
import java.io.PrintWriter;
import java.util.ArrayList;

import bbs.compilerproject.lowlevel.Attribute;
import bbs.compilerproject.lowlevel.Function;
import bbs.compilerproject.lowlevel.Operand;
import bbs.compilerproject.lowlevel.Operation;
import bbs.compilerproject.lowlevel.Operand.OperandType;
import bbs.compilerproject.lowlevel.Operation.OperationType;

public class CallExpression extends Expression {
    
    private IDExpression id;
    private ArrayList<Expression> args;
    
    public CallExpression(IDExpression id, ArrayList<Expression> args) {
        this.id = id;
        this.args = args;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%sfunc", indent);
        pr.println(str);
        System.out.println(str);
        
        id.print(pr, indentation);
        
        if (args != null) {
            indentation += 1;
            indent = "|".repeat(indentation);
            for (int i = 0; i < args.size(); i++) {
                str = String.format("%sarg", indent);
                pr.println(str);
                System.out.println(str);
                args.get(i).print(pr, indentation);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void genLLCode(Function f){

        

        for(int i = 0; i < args.size(); i++){
            args.get(i).genLLCode(f);

            //assumed that a param has to be an IDExpression
            Operand param = new Operand(OperandType.REGISTER, args.get(i).register);
            Operation pass = new Operation(OperationType.PASS, f.getCurrBlock());
            Attribute a = new Attribute("PARAM_NUM", Integer.toString(i));

            pass.setSrcOperand(0, param);
            pass.addAttribute(a);
            f.getCurrBlock().appendOper(pass);
            
        }

        Operation call = new Operation(OperationType.CALL, f.getCurrBlock());
        Operand funcCallName = new Operand(OperandType.STRING, id.getName());

        call.setSrcOperand(0, funcCallName);

    
        Attribute a = new Attribute("numParams", Integer.toString(args.size()));

        call.addAttribute(a);

        f.getCurrBlock().appendOper(call);
        
        register = f.getNewRegNum();

        Operation returnR = new Operation(OperationType.ASSIGN, f.getCurrBlock());
        Operand dest = new Operand(OperandType.REGISTER, register);

        returnR.setDestOperand(0, dest);
        Operand retReg = new Operand(OperandType.MACRO, "RetReg");
        returnR.setSrcOperand(0, retReg);

        f.getCurrBlock().appendOper(returnR);


        

        
    }
}

