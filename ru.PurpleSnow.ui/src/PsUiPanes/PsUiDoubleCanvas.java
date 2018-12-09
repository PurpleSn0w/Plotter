package PsUiPanes;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class PsUiDoubleCanvas extends StackPane{
    public Canvas canvasUnder = new Canvas(10,10);
    public Canvas canvasUpper = new Canvas(10,10);
    protected GraphicsContext gcUnder = canvasUnder.getGraphicsContext2D();
    protected GraphicsContext gcUpper = canvasUpper.getGraphicsContext2D();
    Scene scene;

    boolean drawIfResize;

    public JFXPanel fxRoot = new JFXPanel();

    public PsUiDoubleCanvas(boolean drawIfResize){
        this.drawIfResize=drawIfResize;
        getChildren().addAll(canvasUnder,canvasUpper);
        widthProperty().addListener(event -> setSize());
        heightProperty().addListener(event -> setSize());
    }
    public JFXPanel getSwingRoot(){
        StackPane thisRoot=this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //scene = new Scene(root);
                scene = new Scene(thisRoot);
                fxRoot.setScene(scene);
            }
        });
        return fxRoot;
    }
    public void draw(){}
    public void setSize(double w,double h){
        canvasUnder.setWidth(w);
        canvasUnder.setHeight(h);
        canvasUpper.setWidth(w);
        canvasUpper.setHeight(h);
        if(drawIfResize)draw();
    }
    private void setSize(){
        setSize(getWidth(),getHeight());
    }
}
