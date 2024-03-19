
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
    
    public TokenType peekToken(){
        
        return scan.viewNextToken().getTokenType();
    }
    
    public void advanceToken(){
        currToken = scan.getNextToken();
    }
    
    private void matchToken(TokenType t) throws Exception{
        if(t != currToken.getTokenType()){
            throw new Exception("Token does not match, expected: " + currToken.getTokenType());
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
        
        switch (this.currToken.getTokenType()) {
            case VOID_TOKEN:
                advanceToken();
                Expression id = parseIDEx();
                Declaration d = parseFunDecl(id);
                list.add(d);
                break;
            case INT_TOKEN:
                advanceToken();
                Expression e = parseIDEx();
                Declaration decl = parseDeclPrime(e);
                list.add(decl);
                break;
            case EOF_TOKEN:
              break; 

            default:
                throw new Exception("Error parsing Decl");
        }
     return list; 
    }
    
    private Declaration parseDeclPrime(Expression e) throws Exception {
        
        Declaration ret = null;
        
        if(null != currToken.getTokenType())switch (currToken.getTokenType()) {
            case SEMICOLON_TOKEN:
            {
                matchToken(SEMICOLON_TOKEN);
                ret = new VarDeclaration(((IDExpression)e).getName(), null, null);
                break;
            }   
            case LBRACKET_TOKEN:
            {
                matchToken(LBRACKET_TOKEN);
                Expression n = parseNumExpression();
                ret = new VarDeclaration(((IDExpression)e).getName(), n, null);
                matchToken(RBRACKET_TOKEN);
                break;
            }
            case LPAREN_TOKEN:
            {
              
                ret = parseFunDecl(e);
                break;
            }
            default:
                break;
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
    
    private Declaration parseFunDecl(Expression name) throws Exception {
        
        matchToken(LPAREN_TOKEN);
        ArrayList<Param> p = parseParams();
        matchToken(RPAREN_TOKEN);
        Statement statement = parseCompoundStatement();
        
        Declaration d = new FunctionDeclaration(name, p, statement);
        
        return d;
        
    }
    
    private ArrayList<Param> parseParams() throws Exception{
        
        ArrayList<Param> p = null;
        
        if(currToken.getTokenType() == INT_TOKEN){
            p = parseParamList(); 
        }
        else if(currToken.getTokenType() == VOID_TOKEN){
            advanceToken();
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
        
        if(currToken.getTokenType() == LBRACKET_TOKEN){
            p = new Param("INT,", id, true);
            matchToken(LBRACKET_TOKEN);
            matchToken(RBRACKET_TOKEN);
        }
        else{
            p = new Param("INT,", id, false);
        }
        
        return p;
    }
        
    private Statement parseStatement() throws Exception{
        Statement s = null;
        if(null!=
                currToken.getTokenType())switch (currToken.getTokenType()) {
        //EXPR-STMT
            case SEMICOLON_TOKEN:
            case IDENT_TOKEN:
            case NUMBER_TOKEN:
            case LPAREN_TOKEN:
                
                s = parseExpressionStatement();
                break;
            case LBRACE_TOKEN:
                //Compound Stmt
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
                break;
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
                    //todo: this could be incorrect

                    break;
                case LPAREN_TOKEN:
                    matchToken(LPAREN_TOKEN);
                    Expression e = parseExpression();
                    matchToken(RPAREN_TOKEN);
                    ret = parseSimpleExpression(e);
                    
                     
                default:
                    break;
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
            default:
                ret = parseSimpleExpression(id);
                break;
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
                    break;
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
                default:
                    e = new IDExpression(((IDExpression)id).getName(), null);
                    break;
        }
        
        return e;
        
    }
    
    private ArrayList<Expression> parseArgs() throws Exception{
       ArrayList<Expression> args = new ArrayList<>();
       if (currToken.getTokenType() == IDENT_TOKEN ||
               currToken.getTokenType() == NUMBER_TOKEN ||
               currToken.getTokenType() == LPAREN_TOKEN) {
           args = parseArgsList();
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

