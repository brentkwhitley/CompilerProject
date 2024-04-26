package bbs.compilerproject.parser;

import bbs.compilerproject.compiler.CMinusCompiler;


public class main {

    public static void main(String[] args) {
        
        
        String inFile = "/Users/bennyballin/CompilerProject/CompilerProject/src/main/java/bbs/compilerproject/parser/input.txt";
    
    
        CMinusCompiler compile = new CMinusCompiler();

        compile.compile(inFile);

       
    }
}
