/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Program {
    
    ArrayList<Declaration> program = new ArrayList<>();
    
    public Program(){
        program = null;
    }
     
    public void print(String inFileName){
        String outFile = inFileName + ".ast";
        
        try {
            File file = new File(outFile);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName() + "\n");
            }
            PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(outFile, false)));
            for (int i = 0; i < program.size(); i++) {
                program.get(i).print(pr, 1);
            }
            pr.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

