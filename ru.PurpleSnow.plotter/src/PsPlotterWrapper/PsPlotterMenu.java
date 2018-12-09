package PsPlotterWrapper;

import PsInterfaces.Action;
import PsInterfaces.ActionB;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class PsPlotterMenu {
    Action actionRandom,actionZoomInX,actionZoomOutX, actionZoomFit;
    ActionB actionZoomInArea;
    public int h=20;
    public HBox root = new HBox();
    Node []nodes;
    public PsPlotterMenu(Action actionRandom, Action actionZoomInX, Action actionZoomOutX,
                         Action actionZoomFit, ActionB actionZoomInArea){
        fillContent();
        setFunctional(actionRandom,actionZoomInX,actionZoomOutX,actionZoomFit,actionZoomInArea);
        initFunctional();
        root.setFillHeight(true);
        root.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        resize(h);
        root.getChildren().addAll(nodes);
    }
    private void fillContent(){
        nodes = new Node[5];

        nodes[0] = new Button("R");
        nodes[1] = new Button("+");
        nodes[2] = new Button("-");
        nodes[3] = new Button("[ ]");
        nodes[4] = new ToggleButton("[+]");

    }
    public void setFunctional(Action actionRandom,
                              Action actionZoomInX,Action actionZoomOutX,
                              Action actionZoomFit,ActionB actionZoomInArea
    ){
        this.actionRandom=actionRandom;
        this.actionZoomInX=actionZoomInX;
        this.actionZoomOutX=actionZoomOutX;
        this.actionZoomFit=actionZoomFit;
        this.actionZoomInArea=actionZoomInArea;
    }
    private void initFunctional(){
        ((Button)nodes[0]).setOnAction(e -> actionRandom.act());
        ((Button)nodes[1]).setOnAction(e -> actionZoomInX.act());
        ((Button)nodes[2]).setOnAction(e -> actionZoomOutX.act());
        ((Button)nodes[3]).setOnAction(e -> actionZoomFit.act());
        ((ToggleButton)nodes[4]).setOnAction(e -> actionZoomInArea.act(((ToggleButton)nodes[4]).isSelected()));
    }
    public void resize(int h){
        this.h=h;
        root.setMinHeight(h);
        root.setPrefHeight(h);
        for(Node n:nodes){
            ((Region)n).setPrefHeight(h);
            ((Region)n).setMinHeight(h);
        }
    }

}
