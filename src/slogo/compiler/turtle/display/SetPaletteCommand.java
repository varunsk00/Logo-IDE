package slogo.compiler.turtle.display;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for adding a palette color
 */
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
