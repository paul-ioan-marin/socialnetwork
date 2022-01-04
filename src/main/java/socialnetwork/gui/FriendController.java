package socialnetwork.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.Constants;
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
    private User other_user;

    @FXML
    private Text name;
    @FXML
    private Text email;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button chatButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = Data.logged_user;
        other_user = Data.other_user;
        service = new UserService(new AbstractService(PersonalConstants.URL, USERNAME, PASSWORD), user);
        chatButton.setDisable(true);
        FriendshipWithStatus friendship = null;
        try {
            friendship = service.isFriendWith(other_user);
        } catch (Exception e) {
            Window.ALERT(e.getMessage());
        }
        name.setText(other_user.getFirstName() + " " + other_user.getLastName());
        email.setText(other_user.getEmail());
        if (friendship == null) {
            standard();
        } else {
            if (friendship.status().equals(Constants.Status.ACCEPTED)) {
                chatButton.setDisable(false);
                existing();
            }
            else {
                if (friendship.getLeft().equals(user))
                    requested();
                else request();
            }
        }
    }

    public static void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FriendController.class.getResource("friend-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("User Profile");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }

    private void standard() {
        addButton.setText("Send request");
        addButton.setOnAction(e -> {
            try {
                service.sendRequest(other_user.getEmail());
                addButton.setDisable(true);
            } catch (Exception ex) {
                Window.ALERT(ex.getMessage());
            }
        });
        deleteButton.setText("Delete request");
        deleteButton.setDisable(true);
    }

    private void request() {
        addButton.setText("Accept request");
        addButton.setOnAction(e -> {
            try {
                service.acceptRequest(other_user.getEmail());
                addButton.setDisable(true);
                deleteButton.setDisable(true);
            } catch (Exception ex) {
                Window.ALERT(ex.getMessage());
            }
        });
        deleteButton.setText("Delete request");
        deleteButton.setOnAction(e -> {
            try {
                service.declineRequest(other_user.getEmail());
                addButton.setDisable(true);
                deleteButton.setDisable(true);
            } catch (Exception ex) {
                Window.ALERT(ex.getMessage());
            }
        });
    }

    private void requested() {
        addButton.setText("Accept request");
        addButton.setDisable(true);
        deleteButton.setText("Delete request");
        deleteButton.setOnAction(e -> {
            try {
                service.deleteRequest(other_user.getEmail());
                deleteButton.setDisable(true);
            } catch (Exception ex) {
                Window.ALERT(ex.getMessage());
            }
        });
    }

    private void existing() {
        addButton.setText("Accept friend");
        addButton.setDisable(true);
        deleteButton.setText("Delete friend");
        deleteButton.setOnAction(e -> {
            try {
                service.deleteFriend(other_user.getEmail());
                deleteButton.setDisable(true);
            } catch (Exception ex) {
                Window.ALERT(ex.getMessage());
            }
        });
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Data.other_user = null;
    }

    @FXML
    private void chat() throws Exception {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        ChatController.show();
    }
}