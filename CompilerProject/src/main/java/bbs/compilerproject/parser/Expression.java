package bbs.compilerproject.parser;

import java.io.PrintWriter;

abstract class Expression {
    
    public abstract void print(PrintWriter printWriter, int indentation);
}
