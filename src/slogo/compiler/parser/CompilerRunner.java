package slogo.compiler.parser;

import java.util.Scanner;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: Used to test commands pre-GUI. Takes in a line of SLogo code, compiles it, and executes
 * it.
 * <p>
 * Dependencies: Compiler
 */
public class CompilerRunner {

  public static void main(String[] args) {
    Compiler comp = new Compiler();
    Scanner kb = new Scanner(System.in);

    while (true) {
      System.out.print(">>> ");
      String command = kb.nextLine();
      if (command.equals("exit")) {
        break;
      }
      System.out.println(comp.execute(command));
    }
  }

}
