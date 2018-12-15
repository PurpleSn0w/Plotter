package PsPainterGraph;

import javafx.scene.paint.Color;

public class PsPainterGraphCoreFx extends PsPainterGraphCore{
    Color colorMain = Color.rgb(198,198,198);
    Color colorSpare = Color.rgb(198,61,61);
    double lineWidth = 1;
    public PsPainterGraphCoreFx(double []y,int areaX,int areaY,int areaW,int areaH,DrawLine drawLine){
        super(y,areaX,areaY,areaW,areaH,drawLine);
    }
}
