package socialnetwork.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.PersonalConstants;
import socialnetwork.gui.utils.Data;
import socialnetwork.gui.utils.Window;
import socialnetwork.service.AbstractService;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.PersonalConstants.PASSWORD;
import static socialnetwork.domain.constants.PersonalConstants.USERNAME;

public class FriendController implements Initializable {
    private AbstractService service;
    private User user;
    private User other_user;

    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Data.logged_user;
        other_user = Data.other_user;
        service = new UserService(new AbstractService(PersonalConstants.URL, USERNAME, PASSWORD), user);
        welcomeText.setText("ANA ARE MERE");
    }

    @FXML
    protected void onHelloButtonClick() throws Exception {
        if (service.isFriendWith(other_user) != null)
            welcomeText.setText("sunt prieteni");
        else welcomeText.setText("nu sunt prieteni");
    }

    public static void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FriendController.class.getResource("friend-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Friend");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }
}