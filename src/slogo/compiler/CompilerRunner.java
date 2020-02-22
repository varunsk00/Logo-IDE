package slogo.compiler;

import java.util.Scanner;

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
