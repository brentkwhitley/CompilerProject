package bbs.compilerproject.parser;

import bbs.compilerproject.lowlevel.Function;
import bbs.compilerproject.lowlevel.Operand;
import bbs.compilerproject.lowlevel.Operation;
import bbs.compilerproject.lowlevel.Operand.OperandType;
import bbs.compilerproject.lowlevel.Operation.OperationType;
import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;

public class BinaryExpression extends Expression {

    private Expression lhs;
    private Expression rhs;
    private opType operator;

    public enum opType {
        PLUS,
        MINUS,
        MUL,
        SLASH,
        LT,
        LTEQ,
        GT,
        GTEQ,
        EQTO,
        NOTEQ,
        ASSIGN
    }

    public BinaryExpression(Token.TokenType operator, Expression lhs, Expression rhs) {

        this.lhs = lhs;
        this.rhs = rhs;

        if (operator != null)
            switch (operator) {
                case PLUS_TOKEN:
                    this.operator = opType.PLUS;
                    break;
                case MINUS_TOKEN:
                    this.operator = opType.MINUS;
                    break;
                case MUL_TOKEN:
                    this.operator = opType.MUL;
                    break;
                case SLASH_TOKEN:
                    this.operator = opType.SLASH;
                    break;
                case LT_TOKEN:
                    this.operator = opType.LT;
                    break;
                case LTEQ_TOKEN:
                    this.operator = opType.LTEQ;
                    break;
                case GT_TOKEN:
                    this.operator = opType.GT;
                    break;
                case GTEQ_TOKEN:
                    this.operator = opType.GTEQ;
                    break;
                case EQTO_TOKEN:
                    this.operator = opType.EQTO;
                    break;
                case NOTEQ_TOKEN:
                    this.operator = opType.NOTEQ;
                    break;
                case ASSIGN_TOKEN:
                    this.operator = opType.ASSIGN;
                    break;
                default:
                    break;
            }
    }

    public opType getOperator() {
        return this.operator;
    }

    private String getConditionType() {
        switch (operator) {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case MUL:
                return "*";
            case SLASH:
                return "/";
            case LT:
                return "<";
            case LTEQ:
                return "<=";
            case GT:
                return ">";
            case GTEQ:
                return ">=";
            case EQTO:
                return "==";
            case NOTEQ:
                return "!=";
            default:
                return "=";
        }
    }

    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);

        String str = String.format("%soperation:%s", indent, getConditionType());
        pr.println(str);
        System.out.println(str);

        lhs.print(pr, indentation);
        rhs.print(pr, indentation);
    }



    public void genLLCode(Function f) {

        lhs.genLLCode(f);
        rhs.genLLCode(f);

        Operation binary = null;
        
        switch (this.operator) {
            case PLUS:
                binary = new Operation(Operation.OperationType.ADD_I, f.getCurrBlock());
                break;
            case MINUS:
                binary = new Operation(Operation.OperationType.SUB_I, f.getCurrBlock());
                break;
            case MUL:
                binary = new Operation(Operation.OperationType.MUL_I, f.getCurrBlock());
                break;
            case SLASH:
                binary = new Operation(Operation.OperationType.DIV_I, f.getCurrBlock());
                break;
            case LT:
                binary = new Operation(Operation.OperationType.LT, f.getCurrBlock());
                break;
            case LTEQ:
                binary = new Operation(Operation.OperationType.LTE, f.getCurrBlock());
                break;
            case GT:
                binary = new Operation(Operation.OperationType.GT, f.getCurrBlock());
                break;
            case GTEQ:
                binary = new Operation(Operation.OperationType.GTE, f.getCurrBlock());
                break;
            case EQTO:
                binary = new Operation(Operation.OperationType.EQUAL, f.getCurrBlock());
                break;
            case NOTEQ:
                binary = new Operation(Operation.OperationType.NOT_EQUAL, f.getCurrBlock());
                break;
            default:
                break;
        }

        Operand left = new Operand(Operand.OperandType.REGISTER, lhs.register);
        Operand right = new Operand(Operand.OperandType.REGISTER, rhs.register);
        register = f.getNewRegNum();
        Operand dest = new Operand(OperandType.REGISTER, register);
        binary.setDestOperand(0, dest);
        binary.setSrcOperand(0, left);
        binary.setSrcOperand(1, right);

        f.getCurrBlock().appendOper(binary);

    }
}
