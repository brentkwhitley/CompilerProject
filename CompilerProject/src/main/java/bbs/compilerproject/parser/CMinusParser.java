
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
    
    class CMinusParserException extends Exception {
        public CMinusParserException(String message) {
        super(message);
    }
}
    
    private void matchToken(TokenType t) throws Exception{
        if(t != currToken.getTokenType()){
            throw new CMinusParserException("Error: token does not match, expected " + currToken.getTokenType());
        }
        advanceToken();
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
                    advanceToken();
                    Expression id = parseIDEx();
                    Declaration d = parseFunDecl("void", id);
                    list.add(d);
                    break;
                case INT_TOKEN:
                    advanceToken();
                    Expression e = parseIDEx();
                    Declaration decl = parseDeclPrime("int", e);
                    list.add(decl);
                    break;
                case SEMICOLON_TOKEN:
                    matchToken(SEMICOLON_TOKEN);
                    break;
                default:
                    throw new CMinusParserException("Error parsing parseDecl");
            }
        }
     return list; 
    }
    
    private Declaration parseDeclPrime(String type, Expression e) throws Exception {
        
        Declaration ret = null;
        
        if(null != currToken.getTokenType())switch (currToken.getTokenType()) {
            case SEMICOLON_TOKEN:
            {
                matchToken(SEMICOLON_TOKEN);
                
//                TODO: add type
                ret = new VarDeclaration(((IDExpression)e).getName(), null, null);
                break;
            }   
            case LBRACKET_TOKEN:
            {
                matchToken(LBRACKET_TOKEN);
                Expression n = parseNumExpression();
                
//                TODO: add type
                ret = new VarDeclaration(((IDExpression)e).getName(), n, null);
                matchToken(RBRACKET_TOKEN);
                break;
            }
            case LPAREN_TOKEN:
            {
              
                ret = parseFunDecl(type, e);
                break;
            }
            default:
                throw new CMinusParserException("Error parsing DeclPrime");
                
        }
        return ret;
        
    }
    
    private Expression parseNumExpression(){
        
        NumExpression e = new NumExpression(Integer.parseInt((String)this.currToken.getTokenData()));
        advanceToken();
        return e;
    }
    
    private Declaration parseIDDecl(){
        
        Declaration e = new VarDeclaration((String)(this.currToken.getTokenData()), null, null);
        advanceToken();
        return e;
    }
    
    private Expression parseIDEx(){
        
        Expression e = new IDExpression((String)(this.currToken.getTokenData()), null);
        advanceToken();
        return e;
    }
    
    private Declaration parseFunDecl(String type, Expression name) throws Exception {
        
        matchToken(LPAREN_TOKEN);
        
//        TODO: check if there are params (see if there is a right param as next token)
        ArrayList<Param> p = parseParams();
        matchToken(RPAREN_TOKEN);
        Statement statement = parseCompoundStatement();
        
        Declaration d = new FunctionDeclaration(type, name, p, statement);
        
        return d;
        
    }
    
    private ArrayList<Param> parseParams() throws Exception{
        
        ArrayList<Param> p = null;
        
        if(currToken.getTokenType() == INT_TOKEN){
            p = parseParamList(); 
        }
        
//        TODO: there might not be a void token, might just be empty
        
        else if(currToken.getTokenType() == VOID_TOKEN){
            advanceToken();
        }
        else{
            throw new CMinusParserException("Error parsing parseParams");
        }
  
        return p;
    }
    
    private ArrayList<Param> parseParamList() throws Exception{
        
        ArrayList<Param> k = new ArrayList();
        
        Param p = parseParam();
        
        k.add(p);
        
        while(currToken.getTokenType() == COMMA_TOKEN){
            matchToken(COMMA_TOKEN);
            Param t = parseParam();
            k.add(t);
        }
        
        return k;
    }
    
    private Param parseParam() throws Exception{
        advanceToken(); // we know and INT will be here, so skip it
        Declaration id = parseIDDecl();
        Param p = parseParamPrime(id);
        return p;
        
    }
    
    private Param parseParamPrime(Declaration id) throws Exception{
        Param p = null;
        
        if(null == currToken.getTokenType()){
            throw new CMinusParserException("Error parsing parseParamPrime");
        }
        else switch (currToken.getTokenType()) {
            case LBRACKET_TOKEN:
                p = new Param("INT,", id, true);
                matchToken(LBRACKET_TOKEN);
                matchToken(RBRACKET_TOKEN);
                break;
            case RPAREN_TOKEN:
            case COMMA_TOKEN:
                p = new Param("INT,", id, false);
                break;
            default:
                throw new CMinusParserException("Error parsing parseParamPrime");
        }
       
        return p;
    }
        
    private Statement parseStatement() throws Exception{
        Statement s = null;
        if(null!= currToken.getTokenType())
            switch (currToken.getTokenType()) {
                case SEMICOLON_TOKEN:
                case IDENT_TOKEN:
                case NUMBER_TOKEN:
                case LPAREN_TOKEN:
                    s = parseExpressionStatement();
                    break;
                case LBRACE_TOKEN:
                    s = parseCompoundStatement();
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
                    throw new CMinusParserException("Error parsing parseStatment");
                    
        }
        return s;
    }
    
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
        
        ArrayList<Statement> statementList = new ArrayList();
        ArrayList<Declaration> declList = new ArrayList();
        
        
        matchToken(LBRACE_TOKEN);
        
        while(currToken.getTokenType() == INT_TOKEN){
            
            advanceToken(); //advances past INT
            Declaration e = parseIDDecl();
            
            
            if(currToken.getTokenType() == LBRACKET_TOKEN){
                matchToken(LBRACKET_TOKEN);
                Expression n = parseNumExpression();
                matchToken(RBRACKET_TOKEN);
                Declaration d = new VarDeclaration(((VarDeclaration)e).getName(), n, null);
                    if(currToken.getTokenType() == ASSIGN_TOKEN){
                        
                        matchToken(ASSIGN_TOKEN);
                        Expression rhs = parseExpression();

                        ((VarDeclaration)d).setRHS(rhs);

                        declList.add(d);

                    }
                
            }
            else if(currToken.getTokenType() == ASSIGN_TOKEN){
                matchToken(ASSIGN_TOKEN);
                Expression rhs = parseExpression();
                
                Declaration k = new VarDeclaration(((VarDeclaration)e).getName(), null, rhs);
                
                declList.add(k);
                
            }
            
            if(currToken.getTokenType() == IDENT_TOKEN || 
                currToken.getTokenType() == NUMBER_TOKEN ||
                currToken.getTokenType() == LPAREN_TOKEN ||
                currToken.getTokenType() == IF_TOKEN ||
                currToken.getTokenType() == WHILE_TOKEN ||
                currToken.getTokenType() == RETURN_TOKEN){

                Statement s = parseStatement();
                statementList.add(s);

            }
            else if (currToken.getTokenType() == SEMICOLON_TOKEN){
                matchToken(SEMICOLON_TOKEN);
                Declaration s = new VarDeclaration(((VarDeclaration)e).getName(), null, null);
                declList.add(s);
            }
            
        }
             
        while (currToken.getTokenType() == SEMICOLON_TOKEN || //IF STMT ONLY
                currToken.getTokenType() == IDENT_TOKEN || 
                currToken.getTokenType() == NUMBER_TOKEN ||
                currToken.getTokenType() == LPAREN_TOKEN ||
                currToken.getTokenType() == IF_TOKEN ||
                currToken.getTokenType() == WHILE_TOKEN ||
                currToken.getTokenType() == RETURN_TOKEN){

                Statement s = parseStatement();
                statementList.add(s);

            }
        
  
        
        matchToken(RBRACE_TOKEN);
        
        Statement CS = new CompoundStatement(declList, statementList);
                
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
        
        if(currToken.getTokenType() == IDENT_TOKEN ||
               currToken.getTokenType() == NUMBER_TOKEN||
               currToken.getTokenType() == LPAREN_TOKEN){
             e = parseExpression();
        }
        matchToken(SEMICOLON_TOKEN);
        Statement r = new ReturnStatement(e);
        return r;
    }
    
    private Expression parseExpression() throws Exception{
    
        Expression ret = null;
        
       if(currToken.getTokenType() != null){
           
            switch (currToken.getTokenType()) {
                case IDENT_TOKEN:
                    Expression id = parseIDEx();
                    ret = parseExpressionPrime(id);
                    
                    break;
                case NUMBER_TOKEN:
                    Expression num = parseNumExpression();
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
                Expression ID = new IDExpression(((IDExpression)id).getName(), arraySize);
                ret = parseExpressionDoublePrime(ID);    
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

                    e = parseNumExpression();
                    
                    break;
                case IDENT_TOKEN:
                    
                    Expression ID = parseIDEx();
                    
                    e = parseVarcall(ID);
                    
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
                    e = new IDExpression(((IDExpression)id).getName(), null);
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

