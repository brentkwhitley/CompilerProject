package bbs.compilerproject.parser;

import bbs.compilerproject.lowlevel.CodeItem;
import java.io.PrintWriter;

abstract class Declaration {
    
    public abstract void print(PrintWriter pr, int indentation);
    
    public abstract CodeItem genLLCode();
    
}
