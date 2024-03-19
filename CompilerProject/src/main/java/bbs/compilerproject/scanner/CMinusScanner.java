
package bbs.compilerproject.scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static bbs.compilerproject.scanner.CMinusScanner.StateType.*;
import bbs.compilerproject.scanner.Token.TokenType;
import static bbs.compilerproject.scanner.Token.TokenType.*;



public class CMinusScanner implements Scanner {
    
    
    private BufferedReader inFile;
    private Token nextToken;
    private char[] charBuffer;
    private int bufferIndex;
    
    public enum StateType {
       START,
       INASSIGN,
       INCOMMENT,
       CHECKFORCOMMENTSTART,
       CHECKFORCOMMENTEND,
       CHECKLTEQ,
       CHECKGTEQ,
       CHECKNOTEQ,
       INNUM,
       INID,
       DONE,
       EQTO
    }
    
    public CMinusScanner(String filename){
      
        try {
            inFile = new BufferedReader(new FileReader(filename));
            charBuffer = new char[40];
            bufferIndex = 0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        nextToken = scanToken();
    }
    
    @Override
    public Token getNextToken(){
        Token returnToken = nextToken;
        if(nextToken.getTokenType() != Token.TokenType.EOF_TOKEN){
            nextToken = scanToken();
        }
        return returnToken;
    }
    
    public char getNextChar(){
        int charCode = 0;
        char character;
        
        if(bufferIndex > 0){
            bufferIndex--;
            character = charBuffer[bufferIndex];
        }
        else{

            try {
                charCode = inFile.read();
            } catch (IOException ex) {
                Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
               
            if(charCode == -1){
                character = '|'; //eof
            }
            else{
                character = (char) charCode;
            }
        }
        return character;
    }
    
    public void ungetNextChar(char c){
        if(bufferIndex < charBuffer.length){
            charBuffer[bufferIndex++] = c;
        }
        
    }
    @Override
    public Token viewNextToken(){
        return nextToken;
    }
    
    public TokenType checkKeywords(String s){
        String[] keywords = {"if", "else", "int", "return", "void", "while"};
        
        for(String check : keywords){
            if (s.equals(check)){
                switch (check) {
                    case "if":
                        return IF_TOKEN;
                    case "else":
                        return ELSE_TOKEN;
                    case "int":
                        return INT_TOKEN;
                    case "return":
                        return RETURN_TOKEN;
                    case "void":
                        return VOID_TOKEN;
                    case "while":
                        return WHILE_TOKEN;
                }
            }
        }
        return IDENT_TOKEN;
    }
    
    public final Token scanToken(){
        
        TokenType currentToken = DEFAULT_TOKEN;
        StateType state = START;
        boolean save;
        String tokenString = "";
        
        while(state != DONE){
            char c = getNextChar();
            save = true;
            switch(state){
                case START:
                    if(Character.isDigit(c)){
                        state = INNUM;
                    }
                    else if(Character.isLetter(c)){
                        state = INID;
                    }
                    else if(c == '='){
                        state = INASSIGN;
                    }
                    else if((c == ' ') || (c == '\t') || (c == '\n')){
                        save = false;
                    }
                    else{
                        switch(c){
                            case '|'://checks for the replacement character ie EOF
                                save = false;
                                state = DONE;
                                currentToken = EOF_TOKEN;
                                break;
                                
                            case '=':
                                state = INASSIGN;
                                break;
                                
                            case '<':
                                currentToken = LT_TOKEN;
                                state = CHECKLTEQ;
                                break;
                                
                            case '>':
                                currentToken = GT_TOKEN;
                                state = CHECKGTEQ;
                                break;
                                
                            case '+':
                                currentToken = PLUS_TOKEN;
                                state = DONE;
                                break;
                                
                            case '-':
                                currentToken = MINUS_TOKEN;
                                state = DONE;
                                break;
                                
                            case '*':
                                currentToken = MUL_TOKEN;
                                state = DONE;
                                break;
                                
                            case '/':
                                currentToken = SLASH_TOKEN;
                                state = CHECKFORCOMMENTSTART;
                                break;
                                
                            case '(':
                                currentToken = LPAREN_TOKEN;
                                state = DONE;
                                break;
                                
                            case ')':
                                currentToken = RPAREN_TOKEN;
                                state = DONE;
                                break;
                                
                             case '[':
                                currentToken = LBRACKET_TOKEN;
                                state = DONE;
                                break;
                                
                            case ']':
                                currentToken = RBRACKET_TOKEN;
                                state = DONE;
                                break;
                                
                             case '{':
                                currentToken = LBRACE_TOKEN;
                                state = DONE;
                                break;
                                
                            case '}':
                                currentToken = RBRACE_TOKEN;
                                state = DONE;
                                break;    
                               
                            case ';':
                                currentToken = SEMICOLON_TOKEN;
                                state = DONE;
                                break;
                              
                            case ',':
                                currentToken = COMMA_TOKEN;
                                state = DONE;
                                break;
                                
                            case '!':
                                currentToken = ERROR_TOKEN;
                                state = CHECKNOTEQ;
                                break;
                                
                            default:
                                currentToken = ERROR_TOKEN;
                                state = DONE;
                                break;
                        }
                    }
                    break;
                    
                case CHECKFORCOMMENTSTART:
                    if(c == '*'){
                        state = INCOMMENT;
                    }
                    else{
                        ungetNextChar(c);
                        save = false;
                        state = DONE;
                    }
                    break;
                case CHECKFORCOMMENTEND:
                    if(c == '/'){
                        currentToken = COMMENTBLOCK_TOKEN;
                        state = DONE;
                    }
                    else{
                        state = INCOMMENT;
                    }
                case INCOMMENT:
                    if(c == '*'){
                        state = CHECKFORCOMMENTEND; 
                    }
                    if(c == '|'){ //EOF symbol
                        save = false;
                        ungetNextChar(c);
                        state = DONE;
                        currentToken = ERROR_TOKEN;
                    }
                
                    break;
                case CHECKLTEQ:
                    if(c == '='){
                        currentToken = LTEQ_TOKEN;   
                    }
                    else{
                        ungetNextChar(c);
                        save = false;
                    }    
                        state = DONE;
                    break;
                    
                case CHECKGTEQ:
                     if(c == '='){
                        currentToken = GTEQ_TOKEN;   
                    }
                    else{
                        ungetNextChar(c);
                        save = false;
                    }    
                        state = DONE;
                    break;
                    
                case CHECKNOTEQ:
                     if(c == '='){
                        currentToken = NOTEQ_TOKEN;   
                    }
                    else{
                        ungetNextChar(c);
                        save = false;
                    }    
                        state = DONE;
                    break;
                    
                case INASSIGN:
                    state = DONE;
                switch (c) {
                    case '=':
                        currentToken = EQTO_TOKEN;
                        break;
                    default:
                        ungetNextChar(c);
                        currentToken = ASSIGN_TOKEN;
                        break;
                }
                    break;

                case INNUM:
                    if(Character.isLetter(c)){
                        state = DONE;               //number cant have letter
                        currentToken = ERROR_TOKEN;
                    }
                    else if(!Character.isDigit(c)){
                        ungetNextChar(c);
                        save = false;
                        state = DONE;
                        currentToken = NUMBER_TOKEN;
                    }
                    break;
                case INID:
                    if(Character.isDigit(c)){
                        state = DONE;               //identifier cant have number 
                        currentToken = ERROR_TOKEN;
                    }
                    else if(!Character.isLetter(c)){
                        ungetNextChar(c);
                        save = false;
                        state = DONE;
                        currentToken = IDENT_TOKEN;
                    }
                    break;
                case DONE:
                default:
                    state = DONE;
                    currentToken = ERROR_TOKEN;
                    break;
            }
            if(save){
                tokenString += c;
            }
            if(state == DONE){
                if(currentToken == IDENT_TOKEN){
                    currentToken = checkKeywords(tokenString);
                }
            }
            
        }
        
        Token finalToken = new Token(currentToken, tokenString);
       
        return finalToken;
    }
}
