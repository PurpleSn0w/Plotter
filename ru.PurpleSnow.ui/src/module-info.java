module ru.PurpleSnow.ui {
    requires javafx.base;
    requires javafx.swing;

    requires ru.PurpleSnow.Interfaces;

    exports ru.PurpleSnow.ui.wrappers;
    exports ru.PurpleSnow.ui.core;
    exports ru.PurpleSnow.ui.canvas;
    exports PsUiPanes;
}