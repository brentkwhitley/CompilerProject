package bbs.compilerproject.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import bbs.compilerproject.lowlevel.*;

public class Program {
    
    ArrayList<Declaration> program = new ArrayList<>();
    
    public Program(){
        program = null;
    }

    public void print(String inFileName) {
        String outFile = inFileName + ".ast";
        try {
            File file = new File(outFile);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName() + "\n");
            }
            PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(outFile, false)));
            for (int i = 0; i < program.size(); i++) {
                program.get(i).print(pr, 0);
            }
            pr.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public CodeItem genLLCode(){

        
        CodeItem head = null;
        CodeItem tail = null;


        for (int i = 0; i < program.size(); i++) {
            
            CodeItem item = program.get(i).genLLCode();

            if(head == null){

                head = tail = item;
            }
            else{

                tail.setNextItem(item);
                tail = tail.getNextItem();


            }
        }


        return head;

    }
}

