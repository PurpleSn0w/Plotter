package PsPlotterWrapper;

import PsMathCommon.PsMathArrays;
import PsMathDSP.PsMathDSP;
import PsPainterGraph.PsPainterGraphCore;
import PsUiPanes.PsUiDoubleCanvas;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PsPlotterCanvas extends PsUiDoubleCanvas {
    static final Text baseText = new Text("123");
    static final int baseHeight = (int)baseText.getFont().getSize()*2;

    double xPressed,yPressed;
    double xReleased,yReleased;
    double xDelta,yDelta;
    double xActual,yActual;

    public boolean toolZoomInArea = false;
    boolean redrawToolZoomArea = false;
    double left,top,right,bottom;

    boolean redrawAfterResize = true;
    public Color colorBckGnd = Color.rgb(34,34,34);
    public Color colorWork = Color.rgb(154,154,255);
    public Color colorTool = Color.rgb(255,255,154);
    public ArrayList<PsPainterGraphCore> graphs = new ArrayList();
    public Insets insets = new Insets(baseHeight,baseHeight,baseHeight,baseHeight);
    public void refreshArea(){
        if(redrawAfterResize)drawBckGnd();
        for(PsPainterGraphCore g:graphs){
            g.refreshArea((int)insets.getLeft(),(int)insets.getTop(),
                    (int)(getWidth()-insets.getLeft()-insets.getRight()),
                    (int)(getHeight()-insets.getLeft()-insets.getRight())  );
            if(redrawAfterResize && g.ready)drawGraph(g);
        }
    }
    public PsPlotterCanvas(){
        super(false);
        setBackground(new Background(new BackgroundFill(colorBckGnd, CornerRadii.EMPTY,Insets.EMPTY)));
        widthProperty().addListener(e -> refreshArea());
        heightProperty().addListener(e -> refreshArea());

        canvasUpper.addEventHandler(MouseEvent.ANY,mouseEventHandler);
        //graphs.add(new PsPainterGraphCore(new double[3],0,0,1,1));
    }

    @Override
    public void draw() {
        super.draw();
        drawBckGnd();
        drawUpper();
    }
    public void drawBckGnd(){
        gcUnder.setFill(colorBckGnd);
        gcUnder.fillRect(0,0,canvasUnder.getWidth(),canvasUnder.getHeight());
    }
    public void drawGraph(PsPainterGraphCore g){
        if(g!=null && g.ready) {
            gcUnder.setStroke(colorWork);
            g.draw();
        }
    }
    public void drawGraphs(){
        for(PsPainterGraphCore g:graphs){
            if(g!=null && g.ready) {
                drawBckGnd();
                g.draw();
            }
        }
    }
    public void drawAll(){
        drawBckGnd();
        drawGraphs();
    }
    public void addGraph(PsPainterGraphCore graph){
        graphs.add(graph);
    }
    public void clearGraphs(){
        graphs.clear();
    }
    public void removeGraph(int i){
        graphs.remove(i);
    }
    public void removeGraph(PsPainterGraphCore i){
        graphs.remove(i);
    }
    public void zoomX(double rate){
        for(PsPainterGraphCore g:graphs){
            g.zoomX(rate);
        }
    }
    public void fitFull(){
        for(PsPainterGraphCore g:graphs){
            g.fitFull();
        }
    }

    public void drawUpper(){
        gcUpper.clearRect(0,0,canvasUpper.getWidth(),canvasUpper.getHeight());
        if(toolZoomInArea & redrawToolZoomArea){
            gcUpper.setStroke(colorTool);
            gcUpper.setLineWidth(1);
            gcUpper.strokeRect(xPressed,yPressed,xDelta,yDelta);
            gcUpper.strokeRect(Math.min(xPressed,xActual),Math.min(yPressed,yActual),
                    Math.abs(xDelta),Math.abs(yDelta));
        }
    }

    //==================================================================================================================
    private void refreshLTRB(){
        left = Math.min(xPressed,xActual);
        right = Math.max(xPressed,xActual);
        top = Math.min(yPressed,yActual);
        bottom = Math.max(yPressed,yActual);
    }
    private void calcXstep(int newBegin,int newEnd){
        for(PsPainterGraphCore g:graphs){
            g.calcXstep(newBegin,newEnd);
        }
    }
    private final EventHandler<MouseEvent> mouseEventHandler = e -> {
        if(e.getEventType() == MouseEvent.MOUSE_PRESSED){
            xPressed = e.getX();
            yPressed = e.getY();
            xDelta=0;
            yDelta=0;
            if(toolZoomInArea)redrawToolZoomArea = true;
        }
        else if(e.getEventType() == MouseEvent.MOUSE_RELEASED){
            xReleased = e.getX();
            yReleased = e.getY();
            xDelta = xReleased - xPressed;
            yDelta = yReleased - yPressed;
            refreshLTRB();
            redrawToolZoomArea = false;
            if(toolZoomInArea){
                if(right-left > graphs.get(0).xstep*2) {
                    int newBegin = graphs.get(0).getBeginX(left);
                    int newEnd = graphs.get(0).getEndX(right);
                    if(newEnd-newBegin > 1)calcXstep(newBegin,newEnd);
                }
                drawAll();
            }
        }
        else if(e.getEventType() == MouseEvent.MOUSE_DRAGGED){
            xDelta = e.getX() - xPressed;
            yDelta = e.getY() - yPressed;
            xActual = e.getX();
            yActual = e.getY();
            refreshLTRB();
        }
        else if(e.getEventType() == MouseEvent.MOUSE_MOVED){
            xActual = e.getX();
            yActual = e.getY();
            refreshLTRB();
        }
        drawUpper();
    };

    public void testRandom(int size){
        double[] a=PsMathArrays.genSin(size,(double)100,0,0.1);
        double[] b=PsMathArrays.genSin(size,(double)100,0,0.2);
        double[] x=PsMathArrays.sum(a,b);
        double[] h = {1,-1};
        //double[] h = {-0.5,0,0,0,0,0};
        double[] c = PsMathDSP.convolution(x,h);
        c = PsMathArrays.exclude(c,x.length,10000);

        clearGraphs();
        addGraph(new PsPainterGraphCore(
                //PsMathArrays.genRandom(size,(double)100),
                //PsMathArrays.sum(a,b),
                x,
                baseHeight,baseHeight,(int)(getWidth()-2*baseHeight),(int)(getHeight()-2*baseHeight),
                (double x1,double y1,double x2,double y2) -> {
                    gcUnder.strokeLine(x1,y1,x2,y2);
                })
        );
        addGraph(new PsPainterGraphCore(
                //PsMathArrays.genRandom(size,(double)100),
                //PsMathArrays.sum(a,b),
                c,
                baseHeight,baseHeight,(int)(getWidth()-2*baseHeight),(int)(getHeight()-2*baseHeight),
                (double x1,double y1,double x2,double y2) -> {
                    gcUnder.strokeLine(x1,y1,x2,y2);
                }));
        drawAll();
    }
}
