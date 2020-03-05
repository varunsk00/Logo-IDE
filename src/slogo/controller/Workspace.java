package slogo.controller;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import slogo.compiler.parser.Compiler;
import slogo.terminal.TerminalController;
import slogo.terminal.TerminalView;
import slogo.turtle.TurtleHabitat;

public class Workspace extends BorderPane {
    private TurtleHabitat myHabitat;
    private TerminalView myTerminalView;
    private TerminalController myTerminalController;
    private int status;
    private static final double HEADER_HEIGHT = 80;
    private static final double VARIABLE_EXPLORER_HEIGHT = 150;
    private static final double TAB_HEIGHT = 30;
    private double TABPANE_WIDTH;
    private double TERMINAL_WIDTH;
    private double TERMINAL_HEIGHT;
    private double HABITAT_WIDTH;
    private double HABITAT_HEIGHT;

    private Compiler comp;

    public Workspace(double width, double height){
         setSizes(width, height);
         startCompiler();
         setPrefWidth(width);
         setPrefHeight(height);
         setTurtleHabitat();
         setTerminalView();
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int stat){
        this.status = stat;
    }

    public Compiler getCompiler(){
        return comp;
    }

    public TerminalController getTerminalController(){
        return myTerminalController;
    }

    public TurtleHabitat getHabitat(){
        return myHabitat;
    }

    public TerminalView getTerminal(){
        return myTerminalView;
    }

    private void startCompiler(){
        comp = new Compiler();
    }

    private void setSizes(double scene_width, double scene_height){
        TABPANE_WIDTH = scene_width;
        HABITAT_WIDTH = scene_width/2;
        HABITAT_HEIGHT = scene_height - HEADER_HEIGHT - VARIABLE_EXPLORER_HEIGHT;
        TERMINAL_WIDTH = scene_width/2;
        TERMINAL_HEIGHT = scene_height - HEADER_HEIGHT - VARIABLE_EXPLORER_HEIGHT - TAB_HEIGHT;
    }
    private void setTerminalView() {
        myTerminalView = new TerminalView( (int) TERMINAL_WIDTH, (int) TERMINAL_HEIGHT);
        System.out.println(TERMINAL_HEIGHT);
        myTerminalController = new TerminalController(myTerminalView);
        myTerminalController.setExternals(comp);
        status = -1;
        setLeft(myTerminalView);
    }

    private void setTurtleHabitat() {
        myHabitat = new TurtleHabitat(HABITAT_WIDTH, HABITAT_HEIGHT);
        setRight(myHabitat);
    }
}
