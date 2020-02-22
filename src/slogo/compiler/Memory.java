package slogo.compiler;

import slogo.compiler.exceptions.UnknownVariableException;
import java.util.HashMap;
import java.util.Map;

public class Memory {

  private static Map<String, Double> variableMap = new HashMap<>();

  public static double getVariable(String name) {
    Double ret = variableMap.getOrDefault(name, null);
    if (ret == null) {
      throw new UnknownVariableException("The variable " + name + " has not yet been defined.");
    }
    return ret;
  }

  public static void setVariable(String name, double value) {
    variableMap.put(name, value);
  }

}
