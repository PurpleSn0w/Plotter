package PsPlotterGraphics;

import PsMathCommon.PsMathArrays;
import PsPainterGraph.PsPainterGraphModel;
import javafx.geometry.Insets;

public class PsPlotterLayers extends PsPainterGraphModel<PsPlotterLayer> {
    public PsPlotterLayers(int length){
        super(length);
    }
    public void resize(int w, int h, Insets insets){
        setAreaX((int)insets.getLeft(),(int)(w-insets.getLeft()-insets.getRight()));
        setAreaY((int)insets.getTop(),(int)(h-insets.getTop()-insets.getBottom()));
        for(PsPlotterLayer l:cores){
            l.canvas.setWidth(w);
            l.canvas.setHeight(h);
        }
    }
}
