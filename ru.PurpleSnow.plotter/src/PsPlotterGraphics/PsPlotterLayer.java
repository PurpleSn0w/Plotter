package PsPlotterGraphics;

import PsPainterGraph.PsPainterGraphCore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PsPlotterLayer extends PsPainterGraphCore {
    Canvas canvas=new Canvas(10,10);
    public GraphicsContext gc = canvas.getGraphicsContext2D();
    public Color colorMain = Color.rgb(198,198,198);
    public Color colorSpare = Color.rgb(198,61,61);
    double lineWidth = 1;
    public PsPlotterLayer(StackPane parent){
        super();
        parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resize(parent.getWidth(),parent.getHeight());
            }
        });
        parent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resize(parent.getWidth(),parent.getHeight());
            }
        });
        setDrawLine((double x1,double y1,double x2,double y2) -> gc.strokeRect(x1,y1,x2,y2));
    }
    public PsPlotterLayer(double []y, int areaX, int areaY, int areaW, int areaH){
        super(y,areaX,areaY,areaW,areaH,null);
        setDrawLine((double x1,double y1,double x2,double y2) -> gc.strokeRect(x1,y1,x2,y2));
    }
    public void resize(double w,double h){
        canvas.setWidth(w);
        canvas.setHeight(h);
    }
}
