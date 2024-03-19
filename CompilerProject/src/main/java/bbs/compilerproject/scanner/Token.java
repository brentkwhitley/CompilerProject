
package bbs.compilerproject.scanner;

public class Token {
    
    public enum TokenType{
        EOF_TOKEN,
        ERROR_TOKEN,
        ELSE_TOKEN,
        IF_TOKEN,
        INT_TOKEN,
        RETURN_TOKEN,
        VOID_TOKEN,
        WHILE_TOKEN,
        PLUS_TOKEN,
        MINUS_TOKEN,
        MUL_TOKEN,
        SLASH_TOKEN,
        LT_TOKEN,
        LTEQ_TOKEN,
        GT_TOKEN,
        GTEQ_TOKEN,
        EQTO_TOKEN,
        NOTEQ_TOKEN,
        SEMICOLON_TOKEN,
        COMMA_TOKEN,
        LPAREN_TOKEN,
        RPAREN_TOKEN,
        LBRACE_TOKEN,
        RBRACE_TOKEN, 
        LBRACKET_TOKEN,
        RBRACKET_TOKEN,
        COMMENTBLOCK_TOKEN,
        NUMBER_TOKEN,
        IDENT_TOKEN,
        ASSIGN_TOKEN,
        DEFAULT_TOKEN
    }
   
    public Token(TokenType type){
        this(type, null);
    }
    
    public Token(TokenType type, Object data){
        tokenType = type;
        tokenData = data;
    }
    
    public TokenType getTokenType(){
        return this.tokenType;
    }
    
    public Object getTokenData(){
        return this.tokenData;
    }
    
    public String printToken(){
        if(this.tokenType == TokenType.IDENT_TOKEN 
                || this.tokenType == TokenType.NUMBER_TOKEN 
                || this.tokenType == TokenType.ERROR_TOKEN ){
            
            return (this.tokenType + ": " + this.tokenData);
        }
        else{
            return (this.tokenType + ""); 
        }
    }
 
    
    private TokenType tokenType;
    private Object tokenData;
}
