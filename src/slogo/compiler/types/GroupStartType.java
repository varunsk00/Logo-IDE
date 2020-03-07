package slogo.compiler.types;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import slogo.compiler.exceptions.GroupingException;
import slogo.compiler.parser.Command;

public class GroupStartType extends TypeCommand {

  private Map<Integer, Callable<Double>> methodMap = new HashMap<>();
  private int memberDesiredArgs;

  public GroupStartType(String declaration) {
    super(declaration);
    groupingType = Command.GROUPING_INVALID;
    methodMap.put(CommandType.GROUPING_INVALID, this::executeInvalid);
    methodMap.put(CommandType.GROUPING_ITERATIVE, this::executeIterative);
    methodMap.put(CommandType.GROUPING_COMPARISON, this::executeComparison);
    methodMap.put(CommandType.GROUPING_RECURSIVE, this::executeRecursive);

  }

  @Override
  public double executeCommand() {
    if (args.size()-2 < memberDesiredArgs) {
      throw new GroupingException("Bad number of args for grouping"); //fixme error msg
    }
    try {
      return methodMap.getOrDefault(args.get(0).getGroupingType(), this::executeInvalid).call();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e);
      throw new GroupingException("Invalid grouping");
    }
  }

  public double executeInvalid() {
    return 0; //FIXME should I throw error?
  }

  private double executeIterative() {
    if ((args.size() - 2) % memberDesiredArgs != 0) {
      throw new GroupingException("Bad number of args for grouping"); //fixme error msg
    }
    double ret = 0;
    Command exe = args.get(0);
    for (int i = 1; i < args.size() - 1; i += memberDesiredArgs) {
      for (int j = 0; j < memberDesiredArgs; j++) {
        exe.setArg(args.get(i+j), j);
      }
      ret = exe.execute();
    }
    return ret;
  }

  private double executeComparison() {
    double ret = 1;
    Command exe = args.get(0);
    exe.setArg(args.get(1), 0);
    for (int i = 1; i < args.size()-2; i += memberDesiredArgs - 1) { //fixme odd logic here
      for (int j = 1; j < memberDesiredArgs; j++) {
        exe.setArg(args.get(i+j), j);
      }
      ret *= exe.execute();
    }
    return ret;
  }

  private double executeRecursive() {
    double ret = 0;
    Command exe = args.get(0);
    exe.setArg(args.get(1), 0);

    for (int i = 1; i < args.size()-2; i += memberDesiredArgs - 1) {
      for (int j = 1; j < memberDesiredArgs; j++) {
        exe.setArg(args.get(i+j), j);
      }
      ret = exe.executeCommand();
      System.out.println(ret);
      exe.setArg(new ConstantType(""+ret), 0);
    }
    return ret;
  }


  @Override
  public boolean isCompleteSub() { //fixme
    if (!args.isEmpty()) {
      memberDesiredArgs = args.get(0).getDesiredArgs();
      if (memberDesiredArgs == 0) {
        throw new GroupingException(String
            .format("Attempted to group type (%s) which takes no arguments.",
                args.get(0).getType()));
      }
    }
    return !args.isEmpty() && args.get(args.size() - 1).typeEquals("groupend");
  }
}
