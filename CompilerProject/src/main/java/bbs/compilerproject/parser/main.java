package bbs.compilerproject.parser;

import bbs.compilerproject.compiler.CMinusCompiler;
import bbs.compilerproject.scanner.CMinusScanner;

public class main {

    public static void main(String[] args) {
        
        
        String inFile = "/Users/bennyballin/CompilerProject/CompilerProject/src/main/java/bbs/compilerproject/parser/input.txt";
    
        
        CMinusCompiler compile = new CMinusCompiler();

        compile.compile(inFile);

       /* String[] str = inFile.split("\\."); // use escape character because . represents any char in regex
        String inFileWithoutExt = str[0];
        program.print(inFileWithoutExt);*/
    }
}
