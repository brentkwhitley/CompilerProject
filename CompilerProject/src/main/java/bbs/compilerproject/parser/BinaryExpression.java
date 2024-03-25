package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;

public class BinaryExpression extends Expression {
    
    private Expression lhs; 
    private Expression rhs;
    private opType operator;
    
    public enum opType{
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
        
        if (operator != null) switch (operator) {
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
    
    public opType getOperator( ) {
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
}

