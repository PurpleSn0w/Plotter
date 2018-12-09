package ru.PurpleSnow.ui.core;

import javafx.scene.text.Text;

public final class Const {
    public static final Text   BASE_TEXT   =   new Text("123aA");
    public static final int    BASE_SIZE   =   Math.max(1,(int) BASE_TEXT.getFont().getSize()/3);
    public static final int    WEST        =   1;
    public static final int    NORTH       =   2;
    public static final int    EAST        =   4;
    public static final int    SOUTH       =   8;
}
