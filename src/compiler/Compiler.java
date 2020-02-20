package compiler;

import java.util.ArrayDeque;

public class Compiler {

  public Command parse(String input) {
    ArrayDeque<Command> stack = new ArrayDeque<>();
    for (String word: input.split(" ")) {
      Command comm = getCommandFromString(word);
      stack.push(comm);
      while(stack.peek().isComplete()) {
        Command arg = stack.pop();
        stack.peek().addArg(arg);
      }
    }
    return stack.getLast();
  }

  private Command getCommandFromString(String str) {
    /*
    check type (variable, constant, command, list open/close)
    if not command, create appropriate class and return
    else, lookup command and create corresponding empty class

    setup type map
    setup command map

    iterate over type map
    if matches, create class

    else, iterate over command map
    if matches, create command class

    else, throw invalidsyntaxex
     */
    return null; //FIXME obviously WIP
  }

}
