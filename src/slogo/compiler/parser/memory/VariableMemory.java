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

/**
 * @author Maverick Chung mc608
 *
 * Purpose: Stores all defined variables and their values.
 *
 * Assumptions: All input are valid and verified elsewhere, as with Memory
 *
 * Dependencies: CompilerException, Memory, Comoiler
 */
public class VariableMemory {

  private ArrayDeque<Map<String, Double>> variableStack = new ArrayDeque<>();
  private ArrayDeque<Map<String, Double>> historyStack = new ArrayDeque<>();
  private ResourceBundle errorMsgs;

  /**
   * Creates new VariableMemory
   */
  public VariableMemory() {
    variableStack.push(new HashMap<>());
  }

  /**
   * Sets the error message bundle for this memory
   * @param msgs the resource bundle to be used
   */
  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  /**
   * Saves the state of memory such that it can be reverted to
   */
  public void save() {
    Map<String, Double> newMap = new HashMap<>(variableStack.getLast());
    historyStack.push(newMap);
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  /**
   * Reverts to most recent saved state, throwing away current state
   */
  public void undo() {
    if (!historyStack.isEmpty()) {
      variableStack.removeLast();
      variableStack.addLast(historyStack.pop());
    }
  }

  /**
   * Gets the double value of a variable
   * @param name name of the variable
   * @return the double value of the variable
   */
  public double getVariable(String name) {
    Double ret = variableStack.peek().getOrDefault(name, null);
    if (ret == null) {
      throw new UnknownVariableException(
          String.format(errorMsgs.getString("UnknownVariable"), name));
    }
    return ret;
  }

  /**
   * Sets the value of a variable
   * @param name the name of the variable to be set
   * @param value the value of the variable
   */
  public void setVariable(String name, double value) {
    variableStack.peek().put(name, value);
  }

  /**
   * Pushes the memory stack, creating a new scope
   */
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

  /**
   * Pops the memory stack, deleting the old scope
   */
  public void popMemoryStack() {
    variableStack.pop();
    if (variableStack.isEmpty()) {
      throw new StackUnderflowException(errorMsgs.getString("StackUnderflow"));
    }
  }

  /**
   * Returns all extant variable names
   * @return all extant variable names
   */
  public Collection<String> getAllVariableNames() {
    return variableStack.peek().keySet();
  }

}
