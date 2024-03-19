
package bbs.compilerproject.scanner;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class main {
    
    public static void main(String[] args) {
        // Specify the file path for output
        String outputPath = "/Users/bennyballin/NetBeansProjects/CompilerProject/CompilerProject/src/main/java/bbs/compilerproject/scanner/output.txt";
        String inputPath = "/Users/bennyballin/NetBeansProjects/CompilerProject/CompilerProject/src/main/java/bbs/compilerproject/scanner/testInput.txt";
        
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            flexScanner scan = new flexScanner(new FileReader(inputPath));
            Token output = scan.getNextToken();
            
            writer.write(output.printToken()); 
            writer.newLine(); 
            
            // Write each token to the output file
            System.out.println(output.printToken()); 
            while (output.getTokenType() != Token.TokenType.EOF_TOKEN) {
                output = scan.getNextToken();
                writer.write(output.printToken()); 
                writer.newLine(); 
                System.out.println(output.printToken());
            }
            
            // Close the BufferedWriter
            writer.close();
            
            System.out.println("Output written to file: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
