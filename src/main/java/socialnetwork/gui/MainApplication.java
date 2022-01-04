package socialnetwork.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        AbstractController.show();
    }

    public static void main(String[] args) {
        launch();
    }
}