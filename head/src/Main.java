import PsPlotterWrapper.PsPlotterWrapper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        PsPlotterWrapper plotterWrapper=PsPlotterWrapper.invokeNew(stage,1000,300,300,200);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
