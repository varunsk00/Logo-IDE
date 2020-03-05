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
    private double TERMINAL_WIDTH;
    private double TERMINAL_HEIGHT;
    private double HABITAT_WIDTH;
    private double HABITAT_HEIGHT;
    private double HEADER_HEIGHT = 80;
    private double TABPANE_HEIGHT = 150;
    private double TABPANE_WIDTH;

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
        HABITAT_HEIGHT = scene_height - HEADER_HEIGHT - TABPANE_HEIGHT;
        TERMINAL_WIDTH = scene_width/2;
        TERMINAL_HEIGHT = scene_height - HEADER_HEIGHT - TABPANE_HEIGHT;
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
