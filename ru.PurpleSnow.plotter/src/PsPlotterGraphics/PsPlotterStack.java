package PsPlotterGraphics;

import PsPainterGraph.PsPainterGraphCore;
import PsPainterGraph.PsPainterGraphModel;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PsPlotterStack extends StackPane{
    public PsPainterGraphModel<PsPlotterLayer> layers = new PsPainterGraphModel(10);
    public Insets insets;
    Canvas ctrlFilm = new Canvas(100,100);
    GraphicsContext gcCtrlFilm = ctrlFilm.getGraphicsContext2D();
    Color colorCtrlFilmSelect0 = Color.rgb(254,98,98,0.30);

    boolean dragged=false;

    private static double scrollWidth;
    Color colorHscrollLine = Color.rgb(127,127,127,0.30);
    Color colorHscrollSlider = Color.rgb(127,127,127);
    double hscrollPos = 0,hscrollLen=scrollWidth;
    boolean isHorScrolling=false;
    double hscrollPressedDelta;

    boolean pressedCtrl;

    public boolean toolZoomInArea = false;

    public double xpressed,ypressed,xdragged,ydragged,xdelta,ydelta,xactual,yactual;

    public PsPlotterStack(int top,int right,int bottom,int left){
        insets = new Insets(top,right,bottom,left);
        setCommonHandlers();
        setZoomInHandlers();
        setHorScrollHandlers();
        setKeyHandlers();

        Text t = new Text("123Aa");
        scrollWidth = t.getFont().getSize();
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
        layers.get(index).draw();
    }
    public void drawAll(){
        for(int i=0;i<layers.cores.size();i++){
            draw(i);
        }
    }
    private void drawDragging(){
        if(toolZoomInArea){
            gcCtrlFilm.setFill(colorCtrlFilmSelect0);
            double x = Math.min(xpressed,xdragged);
            double y = Math.min(ypressed,ydragged);
            double dx = Math.abs(xdelta);
            double dy = Math.abs(ydelta);
            gcCtrlFilm.fillRect(x,y,dx,dy);
        }
    }
    private void drawHorScroll(){
        gcCtrlFilm.setFill(colorHscrollLine);
        gcCtrlFilm.fillRect(0,getHeight()-scrollWidth,getWidth(),scrollWidth);
        calcHscrollPos();
        gcCtrlFilm.setFill(colorHscrollSlider);
        gcCtrlFilm.fillRect(hscrollPos,getHeight()-scrollWidth,hscrollLen,scrollWidth);
    }
    private void drawCtrlFilm(){
        gcCtrlFilm.clearRect(0,0,ctrlFilm.getWidth(),ctrlFilm.getHeight());
        if(dragged){
            dragged = false;
            drawDragging();
        }
        drawHorScroll();
    }
    public void draw(){
        drawAll();
        drawCtrlFilm();
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
        draw();
    }

    private void calcHscrollPos(){
        if(layers.getEnd()-layers.getBegin()==layers.getLength()-1)hscrollLen=getWidth();
        else hscrollLen = Math.max(scrollWidth,getWidth()*(layers.getEnd()-layers.getBegin())/layers.getLength());
        int pos = layers.getBegin()+(layers.getEnd()-layers.getBegin())/2;
        hscrollPos = (getWidth()-scrollWidth)*pos/layers.getLength()-hscrollLen/2;
        if(hscrollPos<0)hscrollPos=0;
        double d = hscrollPos + hscrollLen - getWidth();
        if(d>0)hscrollPos-=d;
    }
    private int calcHscrollArrayPoint(double xPixel,boolean relative){
        if(relative==false)return (int)(xPixel*(layers.getLength())/getWidth());
        else {
            int b = layers.getBegin();
            int e = layers.getEnd();
            return b+(int)(xPixel*(e-b)/getWidth());
        }
    }

    //initials
    private void setCommonHandlers(){
        addEventHandler(MouseEvent.MOUSE_MOVED,e -> {
            xactual = e.getX();
            yactual = e.getY();
            requestFocus();
        });
        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            xpressed = e.getX();
            ypressed = e.getY();
            xdelta = 0;
        });
        addEventHandler(MouseEvent.MOUSE_RELEASED,e -> {
            isHorScrolling = false;
        });
        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            dragged = true;
            xdragged = e.getX();
            ydragged = e.getY();
            xdelta = xdragged - xpressed;
            ydelta = ydragged - ypressed;
            drawCtrlFilm();
        });
    }
    private void setZoomInHandlers(){
        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            drawCtrlFilm();
            if(toolZoomInArea && layers!=null && layers.cores!=null && layers.cores.size()>0 && Math.abs(xdelta)>0){
                int newBegin = PsPainterGraphCore.getBeginX(Math.min(xpressed,xdragged),layers.areaX,layers.xstep,layers.begin);
                int newEnd = PsPainterGraphCore.getEndX(Math.max(xpressed,xdragged),layers.cores.get(0).y.length,
                        layers.areaX,layers.xstep,layers.begin);
                if(newEnd-newBegin>2) {
                    layers.begin = newBegin;
                    layers.end = newEnd;
                    layers.calcParams();
                }
                draw();
                layers.get(0).info();
                layers.info();
            }
        });
    }
    private void setHorScrollHandlers(){
        addEventHandler(MouseEvent.MOUSE_PRESSED,e -> {
            xactual = e.getX();
            yactual = e.getY();
            if(yactual>getHeight()-scrollWidth){
                isHorScrolling=true;
                hscrollPressedDelta = xactual-(hscrollPos+hscrollLen/2);
                if(xactual<=hscrollPos || xactual>=hscrollPos+hscrollLen){
                    layers.moveTo(calcHscrollArrayPoint(xactual,false));
                    draw();
                }
            }
        });
        addEventHandler(MouseEvent.MOUSE_DRAGGED,e -> {
            if(isHorScrolling){
                layers.moveTo(calcHscrollArrayPoint(e.getX()-hscrollPressedDelta,false));
                draw();
            }
        });
        addEventHandler(ScrollEvent.ANY, e -> {
            if(pressedCtrl){
                int zoomPoint = calcHscrollArrayPoint(e.getX(),true);
                if(e.getDeltaY()>0)layers.zoomX(0.8,zoomPoint);
                else if(e.getDeltaY()<0)layers.zoomX(1.2,zoomPoint);
                draw();
            }
            else{
                int step = layers.getEnd()-layers.getBegin();
                step = Math.max(1,step/25);
                if(e.getDeltaX()+e.getDeltaY()>0)layers.move(-step);
                else if(e.getDeltaX()+e.getDeltaY()<0)layers.move(step);
                draw();
            }
        });
    }
    private void setKeyHandlers(){
        addEventHandler(KeyEvent.KEY_PRESSED,e -> {
            if(e.getCode() == KeyCode.CONTROL)pressedCtrl=true;
        });
        addEventHandler(KeyEvent.KEY_RELEASED,e -> {
            if(e.getCode() == KeyCode.CONTROL)pressedCtrl=false;
        });
    }
}
