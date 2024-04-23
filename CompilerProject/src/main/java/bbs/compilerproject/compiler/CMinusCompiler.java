package bbs.compilerproject.compiler;

import bbs.compilerproject.x64codegen.X64AssemblyGenerator;
import bbs.compilerproject.parser.*;
import bbs.compilerproject.scanner.CMinusScanner;
import bbs.compilerproject.lowlevel.*;
import java.util.*;
import java.io.*;
import bbs.compilerproject.optimizer.*;
import bbs.compilerproject.x64codegen.*;
import bbs.compilerproject.x86codegen.*;
import bbs.compilerproject.dataflow.*;

public class CMinusCompiler implements Compiler {

    public static HashMap globalHash = new HashMap();
    private static boolean genX64Code = false;

    public CMinusCompiler() {
    }

    public static void setGenX64Code(boolean useX64) {
        genX64Code = useX64;
    }
    public static boolean getGenX64Code() {
        return genX64Code;
    }

    public void compile(String filePath) {


        String filePrefix = "output";
        String fileName = filePrefix + ".c";
        
        try {

            CMinusScanner scan = new CMinusScanner(filePath);
            CMinusParser myParser = new CMinusParser(scan);

            Program parseTree = myParser.parse();
            parseTree.print("output.txtr");

            CodeItem lowLevelCode = parseTree.genLLCode();

            fileName = filePrefix + ".ll";
            PrintWriter outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            int optiLevel = 2;
            LowLevelCodeOptimizer lowLevelOpti =
                    new LowLevelCodeOptimizer(lowLevelCode, optiLevel);
            lowLevelOpti.optimize();

            fileName = filePrefix + ".opti";
            outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            if (genX64Code) {
                X64CodeGenerator x64gen = new X64CodeGenerator(lowLevelCode);
                x64gen.convertToX64();
            }
            else {
                X86CodeGenerator x86gen = new X86CodeGenerator(lowLevelCode);
                x86gen.convertToX86();
            }
            fileName = filePrefix + ".x86";
            outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

//    lowLevelCode.printLLCode(null);

            // simply walks functions and finds in and out edges for each BasicBlock
            ControlFlowAnalysis cf = new ControlFlowAnalysis(lowLevelCode);
            cf.performAnalysis();
//    cf.printAnalysis(null);

            // performs DU analysis, annotating the function with the live range of
            // the value defined by each oper (some merging of opers which define
            // same virtual register is done)
//    DefUseAnalysis du = new DefUseAnalysis(lowLevelCode);
//    du.performAnalysis();
//    du.printAnalysis();

            LivenessAnalysis liveness = new LivenessAnalysis(lowLevelCode);
            liveness.performAnalysis();
            liveness.printAnalysis();

            if (genX64Code) {
                int numRegs = 15;
                X64RegisterAllocator regAlloc = new X64RegisterAllocator(lowLevelCode,
                        numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                X64AssemblyGenerator assembler =
                        new X64AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateX64Assembly();
                outFile.close();
            }
            else {
                int numRegs = 7;
                X86RegisterAllocator regAlloc = new X86RegisterAllocator(lowLevelCode,
                        numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                X86AssemblyGenerator assembler =
                        new X86AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateAssembly();
                outFile.close();
            }

        } catch (IOException ioe) {
        }

    }

    public static void main(String[] args) {
        String filePrefix = "test5";
        CMinusCompiler myCompiler = new CMinusCompiler();
        myCompiler.setGenX64Code(true);
        myCompiler.compile(filePrefix);
    }
}
