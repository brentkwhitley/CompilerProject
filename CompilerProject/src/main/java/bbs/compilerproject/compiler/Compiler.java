package bbs.compilerproject.compiler;

import java.io.*;

public interface Compiler {
  public void compile(String filePrefix) throws IOException;
}
