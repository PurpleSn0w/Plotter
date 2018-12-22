package PsPlotterGraphics;

import PsPainterGraph.PsPainterGraphCores;
import PsPainterGraph.PsPlotterGraphModel;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PsPlotterStack extends StackPane {
    //PsPainterGraphCores<PsPlotterLayer> layers=new PsPainterGraphCores();
    PsPlotterGraphModel<PsPlotterLayer>layers = new PsPlotterGraphModel();
    public Insets insets;
    public PsPlotterStack(int areaX,int areaY,int areaW,int areaH){
        insets = new Insets(areaX,areaY,areaX,areaY);
        addGraph(new PsPlotterLayer(this));
    }
    public void addGraph(PsPlotterLayer graph){
        layers.add(graph);
        PsPlotterLayer l = layers.get(layers.getCount()-1);
        l.gc.setStroke(l.colorSpare);
        l.gc.setLineWidth(l.lineWidth);
        l.refreshArea(  (int)insets.getLeft(),(int)insets.getTop(),
                        (int)(getWidth()-insets.getLeft()-insets.getRight()),
                        (int)(getHeight()-insets.getTop()-insets.getBottom())   );
        getChildren().add(l.canvas);
    }
    public PsPlotterLayer getGraph(int index){
        return layers.get(index);
    }
    public void draw(int index){
        layers.get(0).gc.setFill(Color.rgb(154,23,23));
        layers.get(0).gc.fillRect(10,10,40,30);
        layers.get(index).draw();

    }
    public void refreshArea(int areaX,int areaY,int areaW,int areaH){
        layers.setAreaX(layers.get(0).areaX,layers.get(0).areaW);
        layers.setAreaY(layers.get(0).areaY,layers.get(0).areaH);
    }
    public void resize(double w,double h){
        for(PsPlotterLayer l:layers.cores){
            l.resize(w,h);
        }
    }
}
