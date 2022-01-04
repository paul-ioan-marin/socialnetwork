package socialnetwork.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.gui.utils.CellButton;
import socialnetwork.gui.utils.Data;
import socialnetwork.gui.utils.Window;
import socialnetwork.service.AbstractService;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;
import static socialnetwork.domain.constants.PersonalConstants.*;

public class SentRequestsController implements Initializable {
    private AbstractService service;
    private User user;

    @FXML
    private TableView<FriendshipWithStatus> table;
    @FXML
    private TableColumn<FriendshipWithStatus, String> emailColumn;
    @FXML
    private TableColumn<FriendshipWithStatus, String> dateColumn;
    @FXML
    private TableColumn<FriendshipWithStatus, Button> buttonColumn;

    @FXML
    private Button cancelButton;


    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        user = Data.logged_user;
        service = new UserService(new AbstractService(URL, USERNAME, PASSWORD), user);
        try {
            setTable();
        } catch (Exception e) {
            Window.ALERT(e.getMessage());
        }
    }

    public static void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(socialnetwork.gui.UserController.class.getResource("sent-requests-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Requests Sent");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }

    private void setTable() throws Exception {
        emailColumn.setCellValueFactory(friendship -> { User other_user = friendship.getValue().theOtherFriend(user);
            return new SimpleStringProperty(other_user.getFirstName() + " " + other_user.getLastName()); });
        dateColumn.setCellValueFactory(friendship -> new SimpleStringProperty
                (friendship.getValue().getDate().format(DATEFORMATTER)));
        buttonColumn.setCellFactory(CellButton.forTableColumn("View User", (FriendshipWithStatus f) -> {
            Data.other_user = f.theOtherFriend(user);
            FriendController.show();
            return null;
        }));
        ObservableList<FriendshipWithStatus> modelUser = FXCollections.observableArrayList(service.requestsSent());
        table.setItems(modelUser);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}