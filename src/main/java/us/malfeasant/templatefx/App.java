package us.malfeasant.templatefx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}