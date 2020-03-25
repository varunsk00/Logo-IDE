package slogo.compiler.control;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for a repeat loop
 */
public class RepeatCommand extends LoopCommand {

  public RepeatCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    double val = args.get(0).execute();
    return executeLoop(":repcount", 1, val, 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(1).typeEquals("liststart");
  }
}
