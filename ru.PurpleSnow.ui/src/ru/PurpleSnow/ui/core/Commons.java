package ru.PurpleSnow.ui.core;

import java.awt.*;

public class Commons {
    public static int defineResizeType(double x,double y,double width,double height,int indent){
        int resizeType=0;
        if(x<indent)resizeType=Const.WEST;
        else if(x>width-indent)resizeType=Const.EAST;
        if(y<indent)resizeType|=Const.NORTH;
        else if(y>height-indent)resizeType|=Const.SOUTH;
        return resizeType;
    }
    public static int defineResizeType(double x, double y, Dimension size, int indent){
        return defineResizeType(x,y,size.getWidth(),size.getHeight(),indent);
    }
}
