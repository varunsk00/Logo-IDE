package slogo.controller;

import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ColorSelect extends Stage {
    private static final int WINDOW_HEIGHT = 200;
    private static final int WINDOW_WIDTH = 200;
    private static ResourceBundle myResources;
    private TilePane tp;
    private ColorPicker cp;

    public ColorSelect(String lang, Color color){
        setResizable(false);
        myResources = ResourceBundle.getBundle(lang);
        setTitle(myResources.getString("ColorWindow"));
        tp = new TilePane();
        cp = new ColorPicker();
        cp.setValue(color);
        tp.getChildren().add(cp);
        Scene sc = new Scene(tp, WINDOW_WIDTH, WINDOW_HEIGHT);
        setScene(sc);
    }

    public ColorPicker getColorPicker(){
        return cp;
    }

    public Color getColor(){
        return cp.getValue();
    }
}
