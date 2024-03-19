
package bbs.compilerproject.parser;


public class ExpressionStatement extends Statement{
    
    Expression e;
    
    public ExpressionStatement(Expression ex){
        
        e = ex;
    }
    
    @Override
    public void print(){
        //print the semicolon
    }
    
}
