package bbs.compilerproject.parser;

import java.io.PrintWriter;

import bbs.compilerproject.lowlevel.Function;

abstract class Expression {
    public int register;
    public abstract void print(PrintWriter printWriter, int indentation);
    public abstract void genLLCode(Function f);

}
