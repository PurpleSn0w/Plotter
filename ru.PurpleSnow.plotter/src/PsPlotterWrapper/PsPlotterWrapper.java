package PsPlotterWrapper;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PsPlotterWrapper {
    Stage stage;
    PsPlotterMenu menuBar;
    //VBox root = new VBox();
    BorderPane root = new BorderPane();
    PsPlotterCanvas canvas = new PsPlotterCanvas();
    //BorderPane workArea = new BorderPane();
    public PsPlotterWrapper(Stage stage,int x,int y,int w,int h){
        this.stage=stage;
        this.stage.setTitle("Plotter");
        menuBar = new PsPlotterMenu(
                ()->{
                    canvas.testRandom(500);
                },
                ()->{
                    canvas.zoomX(0.8);
                    canvas.drawAll();
                },
                ()->{
                    canvas.zoomX(1.2);
                    canvas.drawAll();
                },
                () -> {
                    canvas.fitFull();
                    canvas.drawAll();
                },
                (boolean zoomInArea) -> {
                    canvas.toolZoomInArea = zoomInArea;
                }
            );
        //workArea.setCenter(canvas);
        root.setTop(menuBar.root);
        root.setCenter(canvas);
        root.widthProperty().addListener(e -> canvas.setSize(root.getWidth(),root.getHeight()-menuBar.h));
        root.heightProperty().addListener(e -> canvas.setSize(root.getWidth(),root.getHeight()-menuBar.h));
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
