package compiler;

public class ConstantCommand extends Command
{
  private double value;
  public ConstantCommand(double val) {
    super();
    value = val;
  }
  @Override
  double execute() {
    return value;
  }

  @Override
  boolean isComplete() {
    return true;
  }
}
