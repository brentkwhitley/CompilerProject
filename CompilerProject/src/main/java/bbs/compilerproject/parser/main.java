/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;
import bbs.compilerproject.scanner.CMinusScanner;

/**
 *
 * @author Ben
 */
public class main {

    public static void main(String[] args) {
        
        String workingDir = System.getProperty("user.dir");
        String inFile;
        
        if (args.length != 1) {
            System.out.println("Error: expected 1 argument, received " + args.length);
            return;
        } else {
            inFile = args[0];
        }
        
        CMinusScanner scan = new CMinusScanner(workingDir + "/" + inFile);
        CMinusParser parse = new CMinusParser(scan); 
        Program program = parse.parse();
        
        String[] str = inFile.split("\\."); // use escape character because . represents any char in regex
        String inFileWithoutExt = str[0];
        program.print(inFileWithoutExt);
    }
}
