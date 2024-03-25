package bbs.compilerproject.parser;

import java.io.PrintWriter;

abstract class Declaration {
    
    public abstract void print(PrintWriter pr, int indentation);
}
