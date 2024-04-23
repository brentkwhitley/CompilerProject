package bbs.compilerproject.parser;

import java.io.PrintWriter;

abstract class Statement {
    
    public abstract void print(PrintWriter pr, int indentation);

    public abstract void genLLCode();
}
