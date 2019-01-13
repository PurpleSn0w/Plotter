package PsPlotterGraphics;

import PsPainterGraph.PsPainterGraphCore;
import PsPainterGraph.PsPainterGraphModel;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PsPlotterStack extends StackPane{
    public PsPainterGraphModel<PsPlotterLayer> layers = new PsPainterGraphModel(10);
    public Insets insets;
    Canvas ctrlFilm = new Canvas(100,100);
    GraphicsContext gcCtrlFilm = ctrlFilm.getGraphicsContext2D();
    Color colorCtrlFilmSelect0 = Color.rgb(254,98,98,0.30);

    public boolean toolZoomInArea = false;
    public double xpressed,ypressed,xdragged,ydragged,xdelta,ydelta;

    public PsPlotterStack(int areaX,int areaY,int areaW,int areaH){
        insets = new Insets(areaX,areaY,areaX,areaY);
        setZoomInHandlers();
    }
    public void removeAll(){
        layers.removeAll();
        getChildren().removeAll(getChildren());
    }
    public void addGraph(PsPlotterLayer graph){
        getChildren().remove(ctrlFilm);
        layers.add(graph);
        PsPlotterLayer l = layers.get(layers.getCount()-1);
        l.gc.setStroke(l.colorSpare);
        l.gc.setLineWidth(l.lineWidth);
        l.canvas.setWidth(insets.getLeft()+layers.areaW+insets.getRight());
        l.canvas.setHeight(insets.getTop()+layers.areaH+insets.getBottom());
        getChildren().add(getChildren().size(),l.canvas);
        getChildren().add(ctrlFilm);
    }
    public PsPlotterLayer getGraph(int index){
        return layers.get(index);
    }
    public void draw(int index){
        layers.get(index).gc.setFill(Color.rgb(154,23,23));
        //layers.get(0).gc.fillRect(10,10,40,30);
        PsPlotterLayer l = layers.get(index);
        layers.get(index).draw();

    }
    public void drawAll(){
        for(int i=0;i<layers.cores.size();i++){
            draw(i);
        }
    }
    private void drawDragging(){
        gcCtrlFilm.clearRect(0,0,ctrlFilm.getWidth(),ctrlFilm.getHeight());
        if(toolZoomInArea){
            gcCtrlFilm.setFill(colorCtrlFilmSelect0);
            double x = Math.min(xpressed,xdragged);
            double y = Math.min(ypressed,ydragged);
            double dx = Math.abs(xdelta);
            double dy = Math.abs(ydelta);
            gcCtrlFilm.fillRect(x,y,dx,dy);
        }
    }
    private void drawReleasing(){
        gcCtrlFilm.clearRect(0,0,ctrlFilm.getWidth(),ctrlFilm.getHeight());
    }
    public void draw(){
        drawAll();
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
        ctrlFilm.setWidth(w);
        ctrlFilm.setHeight(h);
    }

    //initials
    private void setZoomInHandlers(){
        setOnMousePressed(e -> {
            xpressed = e.getX();
            ypressed = e.getY();
        });
        setOnMouseDragged(e -> {
            xdragged = e.getX();
            ydragged = e.getY();
            xdelta = xdragged - xpressed;
            ydelta = ydragged - ypressed;
            drawDragging();
            System.err.println(
                    "new begin = "+PsPainterGraphCore.getBeginX(Math.min(xpressed,xdragged),layers.areaX,layers.xstep,layers.begin)+
                    "    new end = "+PsPainterGraphCore.getEndX(Math.max(xpressed,xdragged),layers.cores.get(0).y.length,
                            layers.areaX,layers.xstep,layers.begin));
        });
        setOnMouseReleased(e -> {
            drawReleasing();
            if(toolZoomInArea && layers!=null && layers.cores!=null && layers.cores.size()>0){
                int newBegin = PsPainterGraphCore.getBeginX(Math.min(xpressed,xdragged),layers.areaX,layers.xstep,layers.begin);
                int newEnd = PsPainterGraphCore.getEndX(Math.max(xpressed,xdragged),layers.cores.get(0).y.length,
                        layers.areaX,layers.xstep,layers.begin);
                layers.begin = newBegin;
                layers.end = newEnd;
                layers.calcParams();
                System.err.println(layers.begin+"   "+layers.end);
                draw();
            }
        });
    }
}
