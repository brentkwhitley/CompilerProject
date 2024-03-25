package bbs.compilerproject.parser;

import java.io.PrintWriter;
import java.util.ArrayList;

public class CallExpression extends Expression {
    
    private IDExpression id;
    private ArrayList<Expression> args;
    
    public CallExpression(IDExpression id, ArrayList<Expression> args) {
        this.id = id;
        this.args = args;
    }
    
    @Override
    public void print(PrintWriter pr, int indentation) {
        indentation += 1;
        String indent = "|".repeat(indentation);
        
        String str = String.format("%sfunc", indent);
        pr.println(str);
        System.out.println(str);
        
        id.print(pr, indentation);
        
        if (args != null) {
            indentation += 1;
            indent = "|".repeat(indentation);
            for (int i = 0; i < args.size(); i++) {
                str = String.format("%sarg", indent);
                pr.println(str);
                System.out.println(str);
                args.get(i).print(pr, indentation);
            }
        }
    }
}

