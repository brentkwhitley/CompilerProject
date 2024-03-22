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
    
     
    public void print() {
        try {
            File file = new File("output.ast");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName() + "\n");
            }
            
            FileWriter fw = new FileWriter("output.ast", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pr = new PrintWriter(bw);
            
            for (int i = 0; i < program.size(); i++) {
                program.get(i).print(pr);
            }
            pr.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

