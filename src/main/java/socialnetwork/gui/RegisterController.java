package socialnetwork.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import socialnetwork.gui.utils.Window;
import socialnetwork.service.AbstractService;

import java.io.IOException;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.PersonalConstants.*;

public class RegisterController implements Initializable {
    private AbstractService service;

    @FXML
    private TextField emailField;
    @FXML
    private TextField fistNameField;
    @FXML
    private TextField lastNameField;

    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        service = new AbstractService(URL, USERNAME, PASSWORD);
    }

    public static void show(Stage oldStage) {
        try {
            oldStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(RegisterController.class.getResource("register-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }

    @FXML
    private void register() {
        try {
            service.register(emailField.getText(), fistNameField.getText(), lastNameField.getText());
            Window.CONFIRMATION("User successfully added");
        } catch (Exception e) {
            Window.ALERT(e.getMessage());
        }
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
