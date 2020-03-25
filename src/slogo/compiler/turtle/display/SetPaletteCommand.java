package slogo.compiler.turtle.display;

import slogo.compiler.parser.Command;

public class SetPaletteCommand extends Command {

  public SetPaletteCommand(String declaration) {
    super(declaration);
    desiredArgs = 4;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    int ret = (int) args.get(0).execute();
    memory.addColor(ret, (int) args.get(1).execute(), (int) args.get(2).execute(),
        (int) args.get(3).execute());
    return ret;
  }
}
