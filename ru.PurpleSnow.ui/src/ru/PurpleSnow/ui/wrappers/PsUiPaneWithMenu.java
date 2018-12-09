package ru.PurpleSnow.ui.wrappers;

import javax.swing.*;
import java.awt.*;

public class PsUiPaneWithMenu{
    public JPanel root=new JPanel();
    public JPanel menu=new JPanel();
    public JPanel main=new JPanel();
    public PsUiPaneWithMenu(int menuHeight){
        root.setLayout(new BorderLayout());
        root.add(menu,BorderLayout.PAGE_START);
        root.add(main,BorderLayout.CENTER);
        menu.setLayout(new BoxLayout(menu,BoxLayout.LINE_AXIS));
        menu.setPreferredSize(new Dimension(1,menuHeight));
    }
}
/*
*
*
        PsUiPaneWithMenu mainPane=new PsUiPaneWithMenu(70);
        mainPane.menu.setBackground(Color.CYAN);
        JButton btn0=new JButton("123");
        btn0.setPreferredSize(new Dimension(100,(int)mainPane.menu.getPreferredSize().getHeight()));
        btn0.setMinimumSize(btn0.getPreferredSize());
        btn0.setMaximumSize(btn0.getPreferredSize());
        mainPane.menu.add(btn0);
*
* */