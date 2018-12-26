package PsPlotterGraphics;

import PsPainterGraph.PsPainterGraphModel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PsPlotterStack extends StackPane{
    PsPainterGraphModel<PsPlotterLayer> layers = new PsPainterGraphModel(10);
    public Insets insets;
    public PsPlotterStack(int areaX,int areaY,int areaW,int areaH){
        insets = new Insets(areaX,areaY,areaX,areaY);
    }
    public void removeAll(){
        layers.removeAll();
        getChildren().removeAll(getChildren());
    }
    public void addGraph(PsPlotterLayer graph){
        layers.add(graph);
        PsPlotterLayer l = layers.get(layers.getCount()-1);
        l.gc.setStroke(l.colorSpare);
        l.gc.setLineWidth(l.lineWidth);
        l.canvas.setWidth(insets.getLeft()+layers.areaW+insets.getRight());
        l.canvas.setHeight(insets.getTop()+layers.areaH+insets.getBottom());
        getChildren().add(l.canvas);
    }
    public PsPlotterLayer getGraph(int index){
        return layers.get(index);
    }
    public void draw(int index){
        PsPlotterLayer l = layers.get(index);
        if(layers.length>0 && l!=null) {
            l.gc.setFill(Color.rgb(154, 23, 23));
            //layers.get(0).gc.fillRect(10,10,40,30);
            l.draw();
        }
    }
    public void setSize(double w,double h){
        setWidth(w);
        setHeight(h);
        layers.setAreaX((int)insets.getLeft(),(int)(w-insets.getLeft()-insets.getRight()));
        layers.setAreaY((int)insets.getTop(),(int)(h-insets.getTop()-insets.getBottom()));
        for(PsPlotterLayer l:layers.cores){
            l.canvas.setWidth(w);
            l.canvas.setHeight(h);
        }
        draw(0);
    }
}
