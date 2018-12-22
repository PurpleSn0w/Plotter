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
    private void ini(StackPane parent){
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
    }
    public PsPlotterLayer(StackPane parent){
        super();
        ini(parent);
    }
    public PsPlotterLayer(StackPane parent,double []y, int areaX, int areaY, int areaW, int areaH){
        super(y,areaX,areaY,areaW,areaH,null);
        ini(parent);
    }
    public void resize(double w,double h){
        canvas.setWidth(w);
        canvas.setHeight(h);
    }
}
