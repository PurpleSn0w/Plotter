package PsPlotterGraphics;

import PsPainterGraph.PsPainterGraphCore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PsPlotterLayer extends PsPainterGraphCore {
    public Canvas canvas=new Canvas(10,10);
    public GraphicsContext gc = canvas.getGraphicsContext2D();
    public Color colorMain = Color.rgb(198,198,198);
    public Color colorSpare = Color.rgb(198,61,61);
    public boolean independentY = false;
    double lineWidth = 1;

    private void ini(StackPane parent){
        parent.widthProperty().addListener(e -> resize(parent.getWidth(),parent.getHeight()));
        parent.heightProperty().addListener(e -> resize(parent.getWidth(),parent.getHeight()));
        setDrawLine((double x1,double y1,double x2,double y2) -> {
            //System.err.println(x1+" "+y1+"   "+x2+" "+y2);
            gc.setFill(Color.RED);
            gc.setStroke(Color.RED);
            gc.strokeLine(x1,y1,x2,y2);
        });
    }
    public PsPlotterLayer(StackPane parent){
        super();
        ini(parent);
    }
    public PsPlotterLayer(StackPane parent,double[] y){
        super(y);
        ini(parent);
    }
    public PsPlotterLayer(StackPane parent,double []y, int areaX, int areaY, int areaW, int areaH){
        super(y,areaX,areaY,areaW,areaH,null);
        ini(parent);
    }
    public void resize(double w,double h){
        canvas.setWidth(w);
        canvas.setHeight(h);
        draw();
    }
    public void draw(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        super.draw();
    }
}
