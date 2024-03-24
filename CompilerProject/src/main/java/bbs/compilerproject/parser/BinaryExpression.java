/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;
import java.io.PrintWriter;

/**
 *
 * @author Ben
 */
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
            
    public BinaryExpression(Token.TokenType op, Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        
        if (op != null) switch (op) {
            case PLUS_TOKEN:
                operator = opType.PLUS;
                break;
            case MINUS_TOKEN:
                operator = opType.MINUS;
                break;
            case MUL_TOKEN:
                operator = opType.MUL;
                break;
            case SLASH_TOKEN:
                operator = opType.SLASH;
                break;
            case LT_TOKEN:
                operator = opType.LT;
                break;
            case LTEQ_TOKEN:
                operator = opType.LTEQ;
                break;
            case GT_TOKEN:
                operator = opType.GT;
                break;
            case GTEQ_TOKEN:
                operator = opType.GTEQ;
                break;
            case EQTO_TOKEN:
                operator = opType.EQTO;
                break;
            case NOTEQ_TOKEN:
                operator = opType.NOTEQ;
                break;
            case ASSIGN_TOKEN:
                operator = opType.ASSIGN;
                break;
            default:
                break;
        }
    } 
    
    public opType getOperator( ) {
        return this.operator;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        
    }
}

