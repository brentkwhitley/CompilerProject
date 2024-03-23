
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
    
    
    public CMinusParser(CMinusScanner s){
        scan = s;
        currToken = scan.getNextToken();
    }
    
    public void advanceToken(){
        currToken = scan.getNextToken();
    }
    
    public Token viewNextToken(){
        return scan.viewNextToken();
    }
    
    private void matchToken(TokenType t) throws Exception{
        if(t != currToken.getTokenType()){
            throw new CMinusParserException("Error: token does not match, expected " + currToken.getTokenType());
        }
        advanceToken();
    }
    
    private IDExpression parseID() throws Exception{
        IDExpression id = new IDExpression(currToken);
        matchToken(IDENT_TOKEN);
        return id;
    }
    
    private NumExpression parseNum() throws Exception{
        Token t = currToken;
        matchToken(NUMBER_TOKEN);
        NumExpression e = new NumExpression(t);
        return e;
    }
    
    class CMinusParserException extends Exception{
        public CMinusParserException(String message) {
            super(message);
        }
    }
    
    @Override
    public Program parse(){

        Program p = null;
        try {
            p = parseProgram();
        } catch (Exception ex) {
            Logger.getLogger(CMinusParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    private Program parseProgram() throws Exception{
        Program p = new Program();
        p.program = parseDecl();
        return p;
    }
    
    private ArrayList<Declaration> parseDecl() throws Exception{
        
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
                    throw new CMinusParserException("Error parsing declaration");
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
                throw new CMinusParserException("Error parsing declaration");
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
    
    private ArrayList<Param> parseParams() throws Exception{
        
        ArrayList<Param> params = new ArrayList();
        Param p = parseParam();
        params.add(p);
        
        while (currToken.getTokenType() == COMMA_TOKEN){
            matchToken(COMMA_TOKEN);
            p = parseParam();
            params.add(p);
        }
        return params;
    }
    
    private Param parseParam() throws Exception{
        matchToken(INT_TOKEN); // we know and INT will be here, so skip it
        Param p = parseParamPrime(parseID());
        return p;
    }
    
    private Param parseParamPrime(IDExpression id) throws Exception{
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
        
    private Statement parseStatement() throws Exception{
        Statement s = null;
        TokenType tt = currToken.getTokenType();
        while (tt == SEMICOLON_TOKEN ||
               tt == IDENT_TOKEN     ||
               tt == NUMBER_TOKEN    ||
               tt == LPAREN_TOKEN    || 
               tt == IF_TOKEN        || 
               tt == WHILE_TOKEN     ||
               tt == RETURN_TOKEN) {
            
            switch (tt) {
                case SEMICOLON_TOKEN:

                    break;
                case IDENT_TOKEN:

                    break;
                case NUMBER_TOKEN:
                    
                    break;
                case LPAREN_TOKEN:
                    
                    break;
                case LBRACE_TOKEN: 
                    
                    break;
                case IF_TOKEN:
                    s = parseSelectionStatement();
                    break;
                case WHILE_TOKEN:
                    s = parseIterationStatement();
                    break;
                case RETURN_TOKEN:
                    s = parseReturnStatement();
                    break;
                default:
                    throw new CMinusParserException("Error parsing compound statement");
            }
        }
        return s;
    }
        
//        if(null!= currToken.getTokenType())
//            switch (currToken.getTokenType()) {
//                case SEMICOLON_TOKEN:
//                case IDENT_TOKEN:
//                case NUMBER_TOKEN:
//                case LPAREN_TOKEN:
//                    s = parseExpressionStatement();
//                    break;
//                case LBRACE_TOKEN:
//                    s = parseCompoundStatement();
//                    break;
//                case IF_TOKEN:
//                    s = parseSelectionStatement();
//                    break;
//                case WHILE_TOKEN:
//                    s = parseIterationStatement();
//                    break;
//                case RETURN_TOKEN:
//                    s = parseReturnStatement();
//                    break;
//                default:
//                    throw new CMinusParserException("Error parsing statement");
    
    private Statement parseExpressionStatement() throws Exception{
        
        Expression e = null;
        
        if(currToken.getTokenType() == IDENT_TOKEN ||
               currToken.getTokenType() == NUMBER_TOKEN||
               currToken.getTokenType() == LPAREN_TOKEN){
             e = parseExpression();
        }
        matchToken(SEMICOLON_TOKEN);
        
        ExpressionStatement p = new ExpressionStatement(e);
        
        return p;
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
                    throw new CMinusParserException("Error parsing compound statement");
            }
        }
        
        TokenType tt = currToken.getTokenType();
        while (tt == SEMICOLON_TOKEN ||
               tt == IDENT_TOKEN     ||
               tt == NUMBER_TOKEN    ||
               tt == LPAREN_TOKEN    || 
               tt == LBRACE_TOKEN    ||
               tt == IF_TOKEN        || 
               tt == WHILE_TOKEN     ||
               tt == RETURN_TOKEN) {
            
            Statement stmt = parseStatement();
            stmtList.add(stmt);
        }
            
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
        matchToken(RBRACE_TOKEN);
        Statement CS = new CompoundStatement(declList, stmtList);        
        return CS;
    }
    
    private Statement parseSelectionStatement() throws Exception{            
            
        matchToken(IF_TOKEN);
        matchToken(LPAREN_TOKEN);
        Expression ifExpr = parseExpression();
        matchToken(RPAREN_TOKEN);
        Statement thenStmt = parseStatement();
        Statement elseStmt = null;

        if(currToken.getTokenType() == ELSE_TOKEN){
            advanceToken();
            elseStmt = parseStatement();
        }

        Statement returnStmt = new SelectionStatement(ifExpr, thenStmt, elseStmt);
        return returnStmt;
     
    }
    
    private Statement parseIterationStatement() throws Exception{
        
        matchToken(WHILE_TOKEN);
        matchToken(LPAREN_TOKEN);
        Expression e = parseExpression();
        matchToken(RPAREN_TOKEN);
        Statement s = parseStatement();
        
        Statement state = new IterationStatement(e, s);
        
        return state;
    }
    
    private Statement parseReturnStatement() throws Exception{
        Expression e = null;
        matchToken(RETURN_TOKEN);
        
        if(currToken.getTokenType() == IDENT_TOKEN      ||
               currToken.getTokenType() == NUMBER_TOKEN ||
               currToken.getTokenType() == LPAREN_TOKEN){
             e = parseExpression();
        }
        matchToken(SEMICOLON_TOKEN);
        Statement r = new ReturnStatement(e);
        return r;
    }
    
    private Expression parseExpression() throws Exception{
    
        Expression ret = null;
        
        if(currToken.getTokenType() != null) switch (currToken.getTokenType()) {
            case IDENT_TOKEN:
                Expression id = parseID();
                ret = parseExpressionPrime(id);
                break;
            case NUMBER_TOKEN:
                Expression num = parseNum();
                ret = parseSimpleExpression(num);
                break;
            case LPAREN_TOKEN:
                matchToken(LPAREN_TOKEN);
                Expression e = parseExpression();
                matchToken(RPAREN_TOKEN);
                ret = parseSimpleExpression(e);
            default:
               throw new CMinusParserException("Error parsing parseExpression");
       }
       
       return ret;
    }
    
    private Expression parseExpressionPrime(Expression id) throws Exception{
        
        Expression ret = null;
                
        if(null != currToken.getTokenType())
            switch (currToken.getTokenType()) {
            case ASSIGN_TOKEN:
                matchToken(ASSIGN_TOKEN);
                Expression e = parseExpression();
                ret = new AssignExpression((IDExpression)id, e);
                break;
            case LBRACKET_TOKEN:{
                matchToken(LBRACKET_TOKEN);
                Expression arraySize = parseExpression();
                matchToken(RBRACKET_TOKEN);
//                Expression ID = new IDExpression(((IDExpression)id).getName(), arraySize);
//                ret = parseExpressionDoublePrime(ID);    
                break;
                }
            case LPAREN_TOKEN:{
                matchToken(LPAREN_TOKEN);
                ArrayList<Expression> k = parseArgs();
                matchToken(RPAREN_TOKEN);
                Expression simpEx = parseSimpleExpression(id);
                
                ret = new CallExpression(id, k, simpEx);
                break;
                }
            case MUL_TOKEN:
            case SLASH_TOKEN:
            case PLUS_TOKEN:
            case MINUS_TOKEN:
            case SEMICOLON_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case COMMA_TOKEN:
            case IDENT_TOKEN:
            case NUMBER_TOKEN:
            case EQTO_TOKEN:
            case LTEQ_TOKEN:
            case LT_TOKEN:
            case GT_TOKEN:
            case GTEQ_TOKEN:
            case NOTEQ_TOKEN:
                ret = parseSimpleExpression(id);
                break;
            default:
                throw new CMinusParserException("Error parsing parseExpressionPrime");
        }
        
        return ret;
    }
    
    private Expression parseExpressionDoublePrime(Expression id) throws Exception{
        
        if(currToken.getTokenType()== ASSIGN_TOKEN){
                matchToken(ASSIGN_TOKEN);
                Expression e = parseExpression();
                return new AssignExpression((IDExpression)id, e);
                
        }
        else{
            return parseSimpleExpression(id);
        }
        
    }
   
    private boolean isRelop(TokenType p){
        return p == LTEQ_TOKEN ||
                p == LT_TOKEN||
                p == GT_TOKEN ||
                p == GTEQ_TOKEN ||
                p == EQTO_TOKEN ||
                p == NOTEQ_TOKEN;
    }
    
    private boolean isAddop(TokenType p){
        
        return p == PLUS_TOKEN || p == MINUS_TOKEN;
    }
    
    private boolean isMulop(TokenType p){
        
        return p == MUL_TOKEN || p == SLASH_TOKEN;
    }
    
    private Expression parseSimpleExpression(Expression id) throws Exception{
        
        Expression lhs = parseAdditiveExpressionPrime(id);
        
        if(isRelop(currToken.getTokenType())){
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseAdditiveExpression();
            
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private Expression parseAdditiveExpression() throws Exception{
        
        Expression lhs = parseTerm();
        
        while(isAddop(currToken.getTokenType())){
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseTerm();
            
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private Expression parseAdditiveExpressionPrime(Expression id) throws Exception{
        
        Expression lhs = parseTermPrime(id);
        
        while(isAddop(currToken.getTokenType())){
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseTerm();
            
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private Expression parseTerm() throws Exception{
        
        Expression lhs = parseFactor();
        
        while(isMulop(currToken.getTokenType())){
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseFactor();
            
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    private Expression parseTermPrime(Expression id) throws Exception{
        
        Expression lhs = id;
        
        while(isMulop(currToken.getTokenType())){
            Token oldToken = currToken;
            advanceToken();
            Expression rhs = parseFactor();
            
            lhs = new BinaryExpression(oldToken.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
        
    private Expression parseFactor() throws Exception{
        Expression e = null;
        
        if(null != currToken.getTokenType())
            switch (currToken.getTokenType()) {
                case LPAREN_TOKEN:
                    matchToken(LPAREN_TOKEN);
                    e = parseExpression();
                    matchToken(RPAREN_TOKEN);
                   
                    break;
                case NUMBER_TOKEN:

                    e = parseNum();
                    
                    break;
                case IDENT_TOKEN:
                    
//                    Expression ID = parseIDEx();
//                    e = parseVarcall(ID);
                    
                    break;
                default:
                    throw new CMinusParserException("Error parsing parseExpressionPrime");
        }
        
        return e;
    }
    
    private Expression parseVarcall(Expression id) throws Exception{
        
        Expression e = null;
        
        if(null != currToken.getTokenType())
            switch (currToken.getTokenType()) {
                case LBRACE_TOKEN:
                    matchToken(LBRACE_TOKEN);
                    e = parseExpression();
                    matchToken(RBRACE_TOKEN);

                    break;
                case LPAREN_TOKEN:
                    matchToken(LPAREN_TOKEN);
                    ArrayList<Expression> args = parseArgs();
                    matchToken(RPAREN_TOKEN);
                    e = new CallExpression(id, args, null);

                    break;
                case PLUS_TOKEN:
                case MINUS_TOKEN:
                case MUL_TOKEN:
                case SLASH_TOKEN:
                case LT_TOKEN:
                case LTEQ_TOKEN:
                case GT_TOKEN:
                case GTEQ_TOKEN:
                case EQTO_TOKEN:
                case NOTEQ_TOKEN:
                case SEMICOLON_TOKEN:
                case COMMA_TOKEN:    
                case RPAREN_TOKEN:
                case RBRACE_TOKEN: 
                case NUMBER_TOKEN:
                case IDENT_TOKEN:
                case ASSIGN_TOKEN:
//                    e = new IDExpression(((IDExpression)id).getName(), null);
                    break;
                default:
                    throw new CMinusParserException("Error in parseVarCall");

            }
        
        return e;
        
    }
    
    private ArrayList<Expression> parseArgs() throws Exception{
       ArrayList<Expression> args = new ArrayList<>();
       if (null == currToken.getTokenType()) {
           throw new CMinusParserException("Error in parseArgs");
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
                throw new CMinusParserException("Error in parseArgs");
        }
       
       return args;
    }
    
    private ArrayList<Expression> parseArgsList() throws Exception{
        
        ArrayList<Expression> lhs = new ArrayList<>();
        lhs.add(parseExpression());
        
        while(currToken.getTokenType() == COMMA_TOKEN){
            matchToken(COMMA_TOKEN);
            
            lhs.add(parseExpression());
        }
        
        return lhs;
    }
}

