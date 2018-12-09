package ru.PurpleSnow.ui.canvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class PsCuiElement {

    static final int STATE_DEFAULT  = 0;
    static final int STATE_HOVER    = 1;
    static final int STATE_PRESSED  = 2;

    public int state = STATE_DEFAULT;
    public int x=0,y=0,w=10,h=10;
    public Component container;

    int opacity=255;
    public Color colorBckgnd = new Color(62,62,62,255);
    public Color colorWork = new Color(223,223,223,255);

    //interfaces - default actions
    PsCuiPainter painter        = (Graphics g) -> {
        g.setColor(colorBckgnd);
        g.fillRect(x,y,w-1,h-1);
        g.setColor(colorWork);
        g.drawRect(x,y,w-1,h-1);
        if(state==STATE_DEFAULT)g.drawOval(x+w/4,y+h/4,Math.min(w,h)/2,Math.min(w,h)/2);
        else if(state==STATE_HOVER)g.drawRect(x+w/4,y+h/4,Math.min(w,h)/2,Math.min(w,h)/2);
        else if(state==STATE_PRESSED)g.fillRect(x+w/4,y+h/4,Math.min(w,h)/2,Math.min(w,h)/2);
    };
    PsCuiAction actionClicked   = () -> {};
    PsCuiAction actionPressed   = () -> {};
    PsCuiAction actionReleased  = () -> {};
    PsCuiAction actionEntered   = () -> {};
    PsCuiAction actionExited    = () -> {};
    PsCuiAction actionDragged   = () -> {};
    PsCuiAction actionMoved     = () -> {};

    //API---------------------------------------------------

    public PsCuiElement(Component container,int x, int y, int w, int h){
        this.container=container;
        setLocation(x,y);
        setSize(w,h);
        container.addMouseListener(mouseListener);
        container.addMouseMotionListener(mouseMotionListener);
    }

    public void setDraw(PsCuiPainter painter){
        this.painter=painter;
    }
    public void setActionClicked(PsCuiAction action){
        this.actionClicked=action;
    }
    public void setActionPressed(PsCuiAction action){
        this.actionPressed=action;
    }
    public void setActionReleased(PsCuiAction action){
        this.actionReleased=action;
    }
    public void setActionEntered(PsCuiAction action){
        this.actionEntered=action;
    }
    public void setActionExited(PsCuiAction action){
        this.actionExited=action;
    }
    public void setActionDragged(PsCuiAction action){
        this.actionDragged=action;
    }
    public void setActionMoved(PsCuiAction action){
        this.actionMoved=action;
    }

    public void setOpacity(int opacity){
        this.opacity=opacity;
        colorBckgnd=new Color(colorBckgnd.getRed(),colorBckgnd.getGreen(),colorBckgnd.getBlue(),opacity);
        colorWork=new Color(colorWork.getRed(),colorWork.getGreen(),colorWork.getBlue(),opacity);
    }
    public void setLocation(int x,int y){this.x=x;this.y=y;}
    public void setSize(int w,int h){this.w=w;this.h=h;}
    public void draw(Graphics g){painter.draw(g);}
    public void changeState(int newState){
        state=newState;
        container.repaint(x,y,w,h);
        //draw(container.getGraphics());
    }
    public boolean isInside(Point p){
        if(p.getX()>=x && p.getX()<=x+w-1 && p.getY()>=y && p.getY()<=y+h-1)return true;
        return false;
    }

    //processing--------------------------------------------

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            actionClicked.act();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(isInside(e.getPoint()))changeState(STATE_PRESSED);
            actionPressed.act();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isInside(e.getPoint())){
                if(state==STATE_PRESSED)actionReleased.act();
                changeState(STATE_HOVER);
            }
            else changeState(STATE_DEFAULT);
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    };
    MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            if(isInside(e.getPoint()) && state==STATE_DEFAULT)changeState(STATE_HOVER);
            else if(!isInside(e.getPoint()) && state==STATE_HOVER)changeState(STATE_DEFAULT);
            actionDragged.act();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(isInside(e.getPoint()) && state==STATE_DEFAULT)changeState(STATE_HOVER);
            else if(!isInside(e.getPoint()) && state==STATE_HOVER)changeState(STATE_DEFAULT);
            actionMoved.act();
        }
    };

    public interface PsCuiPainter{
        void draw(Graphics g);
    }
    public interface PsCuiAction{void act();}
}
