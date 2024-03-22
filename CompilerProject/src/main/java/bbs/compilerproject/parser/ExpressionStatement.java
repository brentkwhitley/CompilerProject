
package bbs.compilerproject.parser;


public class ExpressionStatement extends Statement{
    
    Expression expr;
    
    public ExpressionStatement(Expression expr){
        this.expr = expr;
    }
    
    @Override
    public void print(){
        //print the semicolon
    }
    
}
