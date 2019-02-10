package PsPlotterWrapper;

import PsMathCommon.PsMathArrays;
import PsPlotterGraphics.PsPlotterLayer;
import PsPlotterGraphics.PsPlotterStack;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PsPlotterWrapper {
    Stage stage;
    PsPlotterMenu menuBar;
    //VBox root = new VBox();
    BorderPane root = new BorderPane();
    PsPlotterStack stack = new PsPlotterStack(10,10,30,10);
    //BorderPane workArea = new BorderPane();
    public PsPlotterWrapper(Stage stage,int x,int y,int w,int h){
        this.stage=stage;
        this.stage.setTitle("Plotter");
        root.widthProperty().addListener(e -> stack.setSize(root.getWidth(),root.getHeight()-menuBar.h));
        root.heightProperty().addListener(e -> stack.setSize(root.getWidth(),root.getHeight()-menuBar.h));
        menuBar = new PsPlotterMenu(
                ()->{//actionRandom
                    stack.removeAll();
                    double[] r = PsMathArrays.genRandom(100,(double)10);
                    double[] s = PsMathArrays.genSin(100,(double)10,0,0.7);
                    double[] sr = PsMathArrays.mult(s,r);
                    double[] maxmin = PsMathArrays.maxmin(sr);
                    double[] straight = PsMathArrays.genStraight(100,(maxmin[1]-maxmin[0])/100,maxmin[0]);
                    r = PsMathArrays.sum(r,50);
                    stack.addGraph(new PsPlotterLayer(stack,s));
                    stack.addGraph(new PsPlotterLayer(stack,r));
                    stack.addGraph(new PsPlotterLayer(stack,sr));
                    stack.addGraph(new PsPlotterLayer(stack,straight));
                    stack.draw();
                },
                ()->{//actionZoomInX
                    stack.layers.zoomX(0.8);
                    stack.draw();
                },
                ()->{//actionZoomOutX
                    stack.layers.zoomX(1.2);
                    stack.draw();
                },
                () -> {//actionZoomFit
                    stack.layers.fitY(true);
                    stack.draw();
                },
                (boolean zoomInArea) -> {//actionZoomInArea
                    stack.toolZoomInArea = zoomInArea;
                    stack.draw();
                },
                () -> {//actionMoveLeft
                    stack.layers.move(-3);
                    stack.draw();
                },
                () -> {//actionMoveRight
                    stack.layers.move(3);
                    stack.draw();
                }
            );
        //workArea.setCenter(canvas);
        root.setTop(menuBar.root);
        //root.setCenter(canvas);
        root.setCenter(stack);
        setBounds(x,y,w,h);
        this.stage.setScene(new Scene(root,w,h));
        this.stage.show();
        menuBar.resize(30);
        stage.setWidth(stage.getWidth()+1);
    }
    public static PsPlotterWrapper invokeNew(Stage stage,int x,int y,int w,int h){
        PsPlotterWrapper plotterWrapper = new PsPlotterWrapper(stage,x,y,w,h);
        return plotterWrapper;
    }
    public void setBounds(int x,int y,int w,int h){
        stage.setX(x);
        stage.setY(y);
        stage.setWidth(w);
        stage.setHeight(h);
    }
}
