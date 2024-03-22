/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Ben
 */
public class VarDeclaration extends Declaration{
    
    private String type;
    private String name;
    private Expression val;
    private Expression rhs;
    
    public VarDeclaration(String ex, Expression e, Expression r){
        this.type = type;
        name = ex;
        val = e;
        rhs = r;
    }
    
    public String getName(){
        return name;
    }
    
    public void setRHS(Expression e){
        rhs = e;
        
    }

    @Override
    public void print(PrintWriter pr){
//        try {
//            File file = new File("output.ast");
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//            
//            FileWriter writer = new FileWriter("output.ast");
//            
//            if (rhs == null) {
//                writer.write(name.toString());
//                System.out.println(name.toString());
//            }
//            
//            writer.close();
//            
//            
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//        
        //print-> name[val] = rhs    *if rhs != null, else just print left side* 
    }
}
