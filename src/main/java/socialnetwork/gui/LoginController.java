package socialnetwork.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import socialnetwork.domain.User;
import socialnetwork.gui.utils.Data;
import socialnetwork.gui.utils.Window;
import socialnetwork.service.AbstractService;

import java.io.IOException;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.PersonalConstants.*;

public class LoginController implements Initializable {
    private AbstractService service;

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        service = new AbstractService(URL, USERNAME, PASSWORD);
        passwordField.setDisable(true);
    }

    public static void show(Stage oldStage) {
        try {
            oldStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }

    @FXML
    private void login() {
        User user;
        try {
            user = service.findUserByEmail(emailField.getText());
            if (user == null)
                throw new Exception("User not found");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Data.logged_user = user;
            UserController.show((Stage) loginButton.getScene().getWindow());
        } catch (Exception e) {
            Window.ALERT(e.getMessage());
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
