/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import bbs.compilerproject.scanner.Token;

import java.io.BufferedWriter;
import bbs.compilerproject.scanner.CMinusScanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class main {

    public static void main(String[] args) {
        String outputPath = "/Users/bennyballin/NetBeansProjects/CompilerProject/CompilerProject/src/main/java/bbs/compilerproject/scanner/output.txt";
        String inputPath = "/Users/bennyballin/NetBeansProjects/CompilerProject/CompilerProject/src/main/java/bbs/compilerproject/scanner/testInput.txt";
        
        FileWriter p = null;
        
        try {
            p = new FileWriter(outputPath);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        

        BufferedWriter writer = new BufferedWriter(p);
        CMinusScanner scan = new CMinusScanner(inputPath);
        
        
        CMinusParser parse = new CMinusParser(scan);
        
        Program program = parse.parse();
        
        //program.print();
        
    }
}
