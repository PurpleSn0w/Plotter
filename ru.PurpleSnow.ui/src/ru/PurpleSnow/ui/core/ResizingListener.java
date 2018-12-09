package ru.PurpleSnow.ui.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResizingListener extends MouseAdapter {
    JFrame frame;
    Component component;
    int baseSize=3;
    int resizingType=0;
    int w,h;
    int newW,newH;
    double newX,newY;
    int minW=50;
    int minH=50;
    public ResizingListener(JFrame frame,Component component,int baseSize){
        this.frame=frame;
        this.baseSize=baseSize;
        this.component=component;
    }
    double xPressed=0,yPressed=0;
    double xDragged=0,yDragged=0;
    int prevW,prevH;
    double xPrevLocation=0,yPrevLocation=0;
    public void mousePressed(MouseEvent e) {
        xPressed=e.getXOnScreen();
        yPressed=e.getYOnScreen();
        xPrevLocation=frame.getLocation().getX();
        yPrevLocation=frame.getLocation().getY();
        prevW=frame.getWidth();
        prevH=frame.getHeight();
        resizingType=Commons.defineResizeType(e.getX(),e.getY(),frame.getSize(),Const.BASE_SIZE);
        if((resizingType & (Const.WEST|Const.EAST|Const.NORTH|Const.SOUTH))!=0){
            w=frame.getWidth();
            h=frame.getHeight();
        }
        newW=w;
        newH=h;
    }
    public void mouseMoved(MouseEvent e){
        resizingType=Commons.defineResizeType(e.getX(),e.getY(),frame.getSize(),Const.BASE_SIZE);
        component.setCursor(new Cursor(
                resizingType==0?                       Cursor.DEFAULT_CURSOR:
                resizingType==Const.WEST?              Cursor.W_RESIZE_CURSOR:
                resizingType==(Const.NORTH|Const.WEST)?Cursor.NW_RESIZE_CURSOR:
                resizingType==Const.NORTH?             Cursor.N_RESIZE_CURSOR:
                resizingType==(Const.NORTH|Const.EAST)?Cursor.NE_RESIZE_CURSOR:
                resizingType==Const.EAST?              Cursor.E_RESIZE_CURSOR:
                resizingType==(Const.SOUTH|Const.EAST)?Cursor.SE_RESIZE_CURSOR:
                resizingType==Const.SOUTH?             Cursor.S_RESIZE_CURSOR:
                resizingType==(Const.SOUTH|Const.WEST)?Cursor.SW_RESIZE_CURSOR:
                                                       Cursor.DEFAULT_CURSOR
        ));
    }
    public void mouseDragged(MouseEvent e){
        int prevX=frame.getX();
        int prevY=frame.getY();
        minW=Math.max(50,(int)frame.getMinimumSize().getWidth());
        minH=Math.max(50,(int)frame.getMinimumSize().getHeight());
        newX=prevX;
        newY=prevY;
        if((resizingType & (Const.WEST|Const.EAST|Const.NORTH|Const.SOUTH))!=0){
            double xDelta=0,yDelta=0;
            if(((resizingType & Const.WEST) != 0) || (resizingType & Const.EAST) != 0)
                xDelta=e.getXOnScreen()-xPressed;
            if(((resizingType & Const.NORTH) != 0) || (resizingType & Const.SOUTH) != 0)
                yDelta=e.getYOnScreen()-yPressed;


            if((resizingType & Const.WEST) != 0 && prevW-(int)xDelta>=minW){
                newW=prevW-(int)xDelta;
                newX=xPrevLocation+xDelta;
            }
            if((resizingType & Const.EAST) != 0 && prevW+(int)xDelta>=minW){
                newW=prevW+(int)xDelta;
            }
            if((resizingType & Const.NORTH) != 0 && prevH-(int)yDelta>=minH){
                newH=prevH-(int)yDelta;
                newY=yPrevLocation+yDelta;
            }
            if((resizingType & Const.SOUTH) != 0 && prevH+(int)yDelta>=minH){
                newH=prevH+(int)yDelta;
            }
            frame.setSize(newW,newH);
            frame.setLocation((int)newX,(int)newY);
        }
        xDragged=e.getXOnScreen();
        yDragged=e.getYOnScreen();
    }
}
