package slogo.compiler.control;

public class DoTimesCommand extends LoopCommand {

  public DoTimesCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    double val = args.get(0).getArgs().get(1).execute(); //FIXME magic val
    String varName = args.get(0).getArgs().get(0).getName();
    return executeLoop(varName, 1, val, 1);
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 2 &&
        args.get(0).typeEquals("liststart") &&
        args.get(1).typeEquals("liststart") &&
        args.get(0).getArgs().size() == 3 &&
        args.get(0).getArgs().get(0)
            .typeEquals("variabletype"); //list has two args, 1st is variable
  }
}
