package socialnetwork.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import socialnetwork.gui.utils.Window;

import java.io.IOException;

public class AbstractController {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void registerButtonClick() {
        RegisterController.show((Stage) registerButton.getScene().getWindow());
    }

    @FXML
    private void loginButtonClick() {
        LoginController.show((Stage) loginButton.getScene().getWindow());
    }

    @FXML
    private void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public static void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AbstractController.class.getResource("abstract-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }
}
