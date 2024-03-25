
package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.CMinusScanner;
import bbs.compilerproject.scanner.Token;
import static bbs.compilerproject.scanner.Token.*;
import static bbs.compilerproject.scanner.Token.TokenType.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
    
public class CMinusParser implements parser {
    
    private CMinusScanner scan;
    Token currToken = null;
    
    public CMinusParser(CMinusScanner s) {
        scan = s;
        currToken = scan.getNextToken();
    }
    
    public void advanceToken() {
        currToken = scan.getNextToken();
    }
    
    public Token viewNextToken() {
        return scan.viewNextToken();
    }
    
    private void matchToken(TokenType tokenType) throws Exception {
        if(tokenType != currToken.getTokenType()) {
            String str = String.format("matchToken expected %s, received %s", 
                    tokenType, currToken.getTokenType());
            throw new CMinusParserException(str);
        }
        advanceToken();
    }
    
    private IDExpression parseID() throws Exception {
        IDExpression id = new IDExpression(currToken);
        matchToken(IDENT_TOKEN);
        return id;
    }
    
    private NumExpression parseNum() throws Exception {
        Token t = currToken;
        matchToken(NUMBER_TOKEN);
        NumExpression e = new NumExpression(t);
        return e;
    }
    
    class CMinusParserException extends Exception {
        public CMinusParserException(String message) {
            super(message);
        }
    }
    
    @Override
    public Program parse() {

        Program p = null;
        try {
            p = parseProgram();
        } catch (Exception ex) {
            Logger.getLogger(CMinusParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    private Program parseProgram() throws Exception {
        Program p = new Program();
        p.program = parseDecl();
        return p;
    }
    
    private ArrayList<Declaration> parseDecl() throws Exception {
        
        ArrayList<Declaration> list = new ArrayList<>();
        while (currToken.getTokenType() != EOF_TOKEN){
            switch (this.currToken.getTokenType()) {
                case VOID_TOKEN:
                    matchToken(VOID_TOKEN);
                    Declaration funDecl = parseFunDecl("void", parseID());
                    list.add(funDecl);
                    break;
                case INT_TOKEN:
                    matchToken(INT_TOKEN);
                    Declaration decl = parseDeclPrime(parseID());
                    list.add(decl);
                    break;
                case SEMICOLON_TOKEN:
                    matchToken(SEMICOLON_TOKEN);
                    break;
                default:
                    throw new CMinusParserException("error in parseDecl");
            }
        }
     return list; 
    }
    
    private Declaration parseDeclPrime(IDExpression id) throws Exception {
        
        Declaration ret = null;
        
        if(currToken.getTokenType() != null) switch (currToken.getTokenType()) {
            case SEMICOLON_TOKEN:
                matchToken(SEMICOLON_TOKEN);
                ret = new VarDeclaration(id);
                break;
            case LBRACKET_TOKEN:
                matchToken(LBRACKET_TOKEN);
                NumExpression size = parseNum();
                ret = new VarDeclaration(id, size);
                matchToken(RBRACKET_TOKEN);
                matchToken(SEMICOLON_TOKEN);
                break;
            case LPAREN_TOKEN:
                ret = parseFunDecl("int", id);
                break;
            default:
                throw new CMinusParserException("error in parseDeclPrime");
        }
        return ret;
    }
    
    private Declaration parseFunDecl(String type, IDExpression id) throws Exception {
        
        matchToken(LPAREN_TOKEN);
        ArrayList<Param> params = null;
        
        if (currToken.getTokenType() != RPAREN_TOKEN) {
            params = parseParams();
        }
        
        matchToken(RPAREN_TOKEN);
        Statement statement = parseCompoundStatement();
        Declaration d = new FunctionDeclaration(type, id, params, statement);
        return d;
    }
    
    private ArrayList<Param> parseParams() throws Exception {
        
        ArrayList<Param> params = new ArrayList();
        Param p = parseParam();
        params.add(p);
        
        while (currToken.getTokenType() == COMMA_TOKEN) {
            matchToken(COMMA_TOKEN);
            p = parseParam();
            params.add(p);
        }
        return params;
    }
    
    private Param parseParam() throws Exception {
        
        matchToken(INT_TOKEN);
        Param p = parseParamPrime(parseID());
        return p;
    }
    
    private Param parseParamPrime(IDExpression id) throws Exception {
        Param p = null;
        switch (currToken.getTokenType()) {
            case LBRACKET_TOKEN:
                matchToken(LBRACKET_TOKEN);
                matchToken(RBRACKET_TOKEN);
                p = new Param(id, true);
                break;
            default:
                p = new Param(id, false);
        }
        return p;
    }
        
    private Statement parseStatement() throws Exception {
        
        Statement stmt = null;
        
        if (currToken.getTokenType() == SEMICOLON_TOKEN ||
            currToken.getTokenType() == IDENT_TOKEN     ||
            currToken.getTokenType() == NUMBER_TOKEN    ||
            currToken.getTokenType() == LPAREN_TOKEN    || 
            currToken.getTokenType() == IF_TOKEN        || 
            currToken.getTokenType() == WHILE_TOKEN     ||
            currToken.getTokenType() == RETURN_TOKEN) {
            
            switch (currToken.getTokenType()) {
                case LBRACE_TOKEN:
                    stmt = parseCompoundStatement();
                    break;
                case IF_TOKEN:
                    stmt = parseSelectionStatement();
                    break;
                case WHILE_TOKEN:
                    stmt = parseIterationStatement();
                    break;
                case RETURN_TOKEN:
                    stmt = parseReturnStatement();
                    break;
                default:
                    stmt = parseExpressionStatement();
            }
        }
        return stmt;
    }
    
    private Statement parseExpressionStatement() throws Exception {
        
        Expression e = null;
                
        if(currToken.getTokenType() == IDENT_TOKEN ||
           currToken.getTokenType() == NUMBER_TOKEN||
           currToken.getTokenType() == LPAREN_TOKEN) {
             e = parseExpression();
        }
        matchToken(SEMICOLON_TOKEN);
        ExpressionStatement stmt = new ExpressionStatement(e);
        return stmt;
    }
    
    private Statement parseCompoundStatement() throws Exception {
        
        ArrayList<Declaration> declList = new ArrayList();
        ArrayList<Statement> stmtList = new ArrayList();
        
        matchToken(LBRACE_TOKEN);
        while (currToken.getTokenType() == INT_TOKEN){
            
            matchToken(INT_TOKEN);
            IDExpression id = parseID();
            VarDeclaration varDecl;
            
            switch (currToken.getTokenType()) {
                case SEMICOLON_TOKEN:
                    matchToken(SEMICOLON_TOKEN);
                    varDecl = new VarDeclaration(id);
                    declList.add(varDecl);
                    break;
                case LBRACKET_TOKEN:
                    matchToken(LBRACKET_TOKEN);
                    NumExpression size = parseNum();
                    varDecl = new VarDeclaration(id, size);
                    declList.add(varDecl);
                    matchToken(RBRACKET_TOKEN);
                    matchToken(SEMICOLON_TOKEN);
                    break;
                default:
                    throw new CMinusParserException("error in parse compoundStatement");
            }
        }
        
        while (currToken.getTokenType() == SEMICOLON_TOKEN ||
               currToken.getTokenType() == IDENT_TOKEN     ||
               currToken.getTokenType() == NUMBER_TOKEN    ||
               currToken.getTokenType() == LPAREN_TOKEN    || 
               currToken.getTokenType() == LBRACE_TOKEN    ||
               currToken.getTokenType() == IF_TOKEN        ||
               currToken.getTokenType() == WHILE_TOKEN     ||
               currToken.getTokenType() == RETURN_TOKEN) {
            
            Statement stmt = parseStatement();
            stmtList.add(stmt);
        }
        matchToken(RBRACE_TOKEN);
        Statement compountStmt = new CompoundStatement(declList, stmtList);        
        return compountStmt;
    }
    
    private Statement parseSelectionStatement() throws Exception {
        
        matchToken(IF_TOKEN);
        matchToken(LPAREN_TOKEN);
        Expression e = parseExpression();
        matchToken(RPAREN_TOKEN);
        Statement thenStmt = parseStatement();
        Statement elseStmt = null;

        if(currToken.getTokenType() == ELSE_TOKEN){
            matchToken(ELSE_TOKEN);
            elseStmt = parseStatement();
        }
        Statement returnStmt = new SelectionStatement(e, thenStmt, elseStmt);
        return returnStmt;
    }
    
    private Statement parseIterationStatement() throws Exception {
        
        matchToken(WHILE_TOKEN);
        matchToken(LPAREN_TOKEN);
        Expression e = parseExpression();
        matchToken(RPAREN_TOKEN);
        Statement stmt = parseStatement();
        Statement returnStmt = new IterationStatement(e, stmt);
        return returnStmt;
    }
    
    private Statement parseReturnStatement() throws Exception {
        
        matchToken(RETURN_TOKEN);
        Expression e = null;
        
        if(currToken.getTokenType() == IDENT_TOKEN  ||
           currToken.getTokenType() == NUMBER_TOKEN ||
           currToken.getTokenType() == LPAREN_TOKEN){
             e = parseExpression();
        }
        matchToken(SEMICOLON_TOKEN);
        Statement returnStmt = new ReturnStatement(e);
        return returnStmt;
    }
    
    private Expression parseExpression() throws Exception {
        
        Expression ret = null;
        
        if(currToken.getTokenType() != null) switch (currToken.getTokenType()) {
            case IDENT_TOKEN:
                IDExpression id = parseID();
                if (currToken.getTokenType() == SEMICOLON_TOKEN) {
                    ret = id;
                    break;
                }
                ret = parseExpressionPrime(id);
                break;
            case NUMBER_TOKEN:
                NumExpression num = parseNum();
                if (currToken.getTokenType() == SEMICOLON_TOKEN) {
                    ret = num;
                    break;
                }
                ret = parseSimpleExpression(num);
                break;
            case LPAREN_TOKEN:
                matchToken(LPAREN_TOKEN);
                Expression e = parseExpression();
                matchToken(RPAREN_TOKEN);
                ret = parseSimpleExpression(e);
            default:
               throw new CMinusParserException("error in parseExpression");
       }
       return ret;
    }
    
    private Expression parseExpressionPrime(IDExpression id) throws Exception {
        
        Expression ret = null;
        
        if (currToken.getTokenType() == null || currToken.getTokenType() == SEMICOLON_TOKEN) {
            return ret;
        }
        
        switch (currToken.getTokenType()) {
            case ASSIGN_TOKEN:
                matchToken(ASSIGN_TOKEN);
                Expression e = parseExpression();
                ret = new AssignExpression(id, e);
                break;
            case LBRACKET_TOKEN:
                matchToken(LBRACKET_TOKEN);
                Expression arraySize = parseExpression();
                matchToken(RBRACKET_TOKEN);
                ret = parseExpressionDoublePrime(id);
                break;
            case LPAREN_TOKEN:
                matchToken(LPAREN_TOKEN);
                ArrayList<Expression> args = parseArgs();
                matchToken(RPAREN_TOKEN);
                Expression simpEx = parseSimpleExpression(id);
                ret = new CallExpression(id, args);
                break;
            case SEMICOLON_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case COMMA_TOKEN:
            case IDENT_TOKEN:
            case NUMBER_TOKEN:
            case MUL_TOKEN:
            case SLASH_TOKEN:
            case PLUS_TOKEN:
            case MINUS_TOKEN:
            case EQTO_TOKEN:
            case NOTEQ_TOKEN:
            case LT_TOKEN:
            case LTEQ_TOKEN:
            case GT_TOKEN:
            case GTEQ_TOKEN:
                ret = parseSimpleExpression(id);
                break;
            default:
                throw new CMinusParserException("error in parseExpressionPrime");
        }
        return ret;
    }
    
    private Expression parseExpressionDoublePrime(IDExpression id) throws Exception {
        
        if (currToken.getTokenType() == ASSIGN_TOKEN) {
            matchToken(ASSIGN_TOKEN);
            Expression e = parseExpression();
            return new AssignExpression(id, e);
        } else {
            return parseSimpleExpression(id);
        }
    }
    
    private Expression parseSimpleExpression(Expression id) throws Exception {
        
        Expression lhs = parseAdditiveExpressionPrime(id);
        
        if (isRelop(currToken.getTokenType())) {
            Token opToken = currToken;
            advanceToken();
            Expression rhs = parseAdditiveExpression();
            lhs = new BinaryExpression(opToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private boolean isRelop(TokenType p){
        return p == LTEQ_TOKEN ||
               p == LT_TOKEN   ||
               p == GT_TOKEN   ||
               p == GTEQ_TOKEN ||
               p == EQTO_TOKEN ||
               p == NOTEQ_TOKEN;
    }
    
    private Expression parseAdditiveExpression() throws Exception {
        
        Expression lhs = parseTerm();
        
        while(isAddop(currToken.getTokenType())) {
            Token opToken = currToken;
            advanceToken();
            Expression rhs = parseTerm();
            lhs = new BinaryExpression(opToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private Expression parseAdditiveExpressionPrime(Expression id) throws Exception {
        
        Expression lhs = parseTermPrime(id);
        
        while(isAddop(currToken.getTokenType())) {
            Token opToken = currToken;
            advanceToken();
            Expression rhs = parseTerm();
            lhs = new BinaryExpression(opToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private boolean isAddop(TokenType tokenType) {
        return tokenType == PLUS_TOKEN || tokenType == MINUS_TOKEN;
    }
    
    private Expression parseTerm() throws Exception {
        
        Expression lhs = parseFactor();
        
        while (isMulop(currToken.getTokenType())) {
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseFactor();
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private Expression parseTermPrime(Expression id) throws Exception {
        
        Expression lhs = id;
        
        while (isMulop(currToken.getTokenType())) {
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseFactor();
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private boolean isMulop(TokenType p) {
        return p == MUL_TOKEN || p == SLASH_TOKEN;
    }
        
    private Expression parseFactor() throws Exception {
        
        Expression e = null;
        if(currToken.getTokenType() != null) switch (currToken.getTokenType()) {
            case LPAREN_TOKEN:
                matchToken(LPAREN_TOKEN);
                e = parseExpression();
                matchToken(RPAREN_TOKEN);   
                break;
            case NUMBER_TOKEN:
                e = parseNum();
                break;
            case IDENT_TOKEN:
                IDExpression id = parseID();
                if (currToken.getTokenType() == LBRACKET_TOKEN || 
                    currToken.getTokenType() == LPAREN_TOKEN) {
                    e = parseVarcall(id);
                } else {
                    e = id;
                }
                break;
            default:
                throw new CMinusParserException("error in parseFactor");
        }
        return e;
    }
    
    private Expression parseVarcall(IDExpression id) throws Exception {
        
        Expression e = null;
        
        if (null != currToken.getTokenType()) switch (currToken.getTokenType()) {
            case LBRACKET_TOKEN:
                matchToken(LBRACKET_TOKEN);
                e = parseExpression();
                matchToken(RBRACKET_TOKEN);
                break;
            case LPAREN_TOKEN:
                matchToken(LPAREN_TOKEN);
                ArrayList<Expression> args = parseArgs();
                matchToken(RPAREN_TOKEN);
                e = new CallExpression(id, args);
                break;
            default:
                throw new CMinusParserException("error in parseVarcall");
            }
        return e;
    }
    
    private ArrayList<Expression> parseArgs() throws Exception {
       
       ArrayList<Expression> args = new ArrayList<>();
        
        if (null == currToken.getTokenType()) {
           throw new CMinusParserException("error in parseArgs");
        }
        else switch (currToken.getTokenType()) {
            case IDENT_TOKEN:
            case NUMBER_TOKEN:
            case LPAREN_TOKEN:
                args = parseArgsList();
                break;
            case RPAREN_TOKEN:
                break;
            default:
                throw new CMinusParserException("error in parseArgs");
         }
       return args;
    }
    
    private ArrayList<Expression> parseArgsList() throws Exception {
        
        ArrayList<Expression> lhs = new ArrayList<>();
        lhs.add(parseExpression());
        
        while (currToken.getTokenType() == COMMA_TOKEN) {
            matchToken(COMMA_TOKEN);   
            lhs.add(parseExpression());
        }
        return lhs;
    }
}

// TODO: WHAT IS THIS:
//            if(currToken.getTokenType() == LBRACKET_TOKEN){
//                matchToken(LBRACKET_TOKEN);
//                NumExpression n = parseNumExpression();
//                matchToken(RBRACKET_TOKEN);
//                Declaration d = new VarDeclaration(((VarDeclaration)e).getName(), n, null);
//                    if(currToken.getTokenType() == ASSIGN_TOKEN){
//                        
//                        matchToken(ASSIGN_TOKEN);
//                        Expression rhs = parseExpression();
//
//                        ((VarDeclaration)d).setRHS(rhs);
//
//                        declList.add(d);
//                    }
//            }
//            else if(currToken.getTokenType() == ASSIGN_TOKEN){
//                matchToken(ASSIGN_TOKEN);
//                Expression rhs = parseExpression();
//                
//                Declaration k = new VarDeclaration(((VarDeclaration)e).getName(), null, rhs);
//                
//                declList.add(k);
//                
//            }

