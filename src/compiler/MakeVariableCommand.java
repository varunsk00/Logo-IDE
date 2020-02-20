package compiler;

public class MakeVariableCommand extends Command{

  @Override
  double execute() {
    double value = args.get(1).execute();
    Memory.setVariable(((VariableCommand)args.get(0)).getName(), value); //FIXME refactor args to remove instanceof?
    return value;
  }

  @Override
  boolean isComplete() {
    return args.get(0) instanceof VariableCommand && args.size()==2;
  }
}
