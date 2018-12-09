package ru.PurpleSnow.ui.wrappers;

import javafx.scene.canvas.GraphicsContext;
import ru.PurpleSnow.ui.core.Const;
import ru.PurpleSnow.ui.core.ResizingListener;

import javax.swing.*;
import java.awt.*;

public class PsuiFrameCommon extends JFrame {
    public PsuiFrameCommon(){
        ResizingListener mouseEventsListener = new ResizingListener(this,this,Const.BASE_SIZE);
        addMouseMotionListener(mouseEventsListener);
        addMouseListener(mouseEventsListener);
    }
    public void paintComponent(Graphics g) {
        System.err.println("repaint");
        repaint(3,3,getWidth()-6,getHeight()-6);

        // Draw Text
        g.drawString("This is my custom Panel!",10,50);
    }
}
