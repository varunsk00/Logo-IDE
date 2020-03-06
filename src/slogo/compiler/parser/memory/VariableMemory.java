package slogo.compiler.parser.memory;

import static slogo.compiler.parser.Compiler.MAX_RECURSION_DEPTH;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.StackOverflowException;
import slogo.compiler.exceptions.StackUnderflowException;
import slogo.compiler.exceptions.UnknownVariableException;

public class VariableMemory {

  private ArrayDeque<Map<String, Double>> variableStack = new ArrayDeque<>();
  private ArrayDeque<Map<String, Double>> historyStack = new ArrayDeque<>();
  private ResourceBundle errorMsgs;

  public VariableMemory() {
    variableStack.push(new HashMap<>());
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  public void save() {
    Map<String, Double> newMap = new HashMap<>(variableStack.getLast());
    historyStack.push(newMap);
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  public void undo() {
    variableStack.removeLast();
    variableStack.addLast(historyStack.pop());
  }

  public double getVariable(String name) {
    Double ret = variableStack.peek().getOrDefault(name, null);
    if (ret == null) {
      throw new UnknownVariableException(
          String.format(errorMsgs.getString("UnknownVariable"), name));
    }
    return ret;
  }

  public Map<String, Double> getVariableMapCopy() {
    return new HashMap<>(variableStack.peek());
  }

  public void setVariable(String name, double value) {
    variableStack.peek().put(name, value);
  }

  public void pushMemoryStack() {
    HashMap<String, Double> newLayer = new HashMap<>(variableStack.peek());
    variableStack.push(newLayer);
    if (variableStack.size() > MAX_RECURSION_DEPTH) {
      while (variableStack.size() > 1) {
        variableStack.pop();
      }
      throw new StackOverflowException(
          String.format(errorMsgs.getString("StackOverflow"), MAX_RECURSION_DEPTH));
    }
  }

  public void popMemoryStack() {
    variableStack.pop();
    if (variableStack.isEmpty()) {
      throw new StackUnderflowException(errorMsgs.getString("StackUnderflow"));
    }
  }

  public Collection<String> getAllVariableNames() {
    return variableStack.peek().keySet();
  }

}
