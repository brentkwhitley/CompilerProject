package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.lowlevel.Function;

abstract class Statement {
    
    public abstract void print(PrintWriter pr, int indentation);

    public abstract void genLLCode(Function f);
}
