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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.User;
import socialnetwork.gui.utils.CellButton;
import socialnetwork.gui.utils.Data;
import socialnetwork.gui.utils.Window;
import socialnetwork.service.AbstractService;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.PersonalConstants.*;

public class UserController implements Initializable {
    private AbstractService service;

    @FXML
    private Text welcomeText;
    @FXML
    private TextField search;

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Button> buttonColumn;

    @FXML
    private Button logoutButton;



    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        User user = Data.logged_user;
        headerSet();
        service = new UserService(new AbstractService(URL, USERNAME, PASSWORD), user);
    }

    public static void show(Stage oldStage) {
        try {
            oldStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("user-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Feed");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
    }

    private void headerSet() {
        welcomeText.setText("Welcome " + Data.logged_user.getFirstName() + " " + Data.logged_user.getLastName() + "!");
    }

    @FXML
    private void pressSearch() throws Exception {
        emailColumn.setCellValueFactory(user -> new SimpleStringProperty
                (user.getValue().getFirstName() + " " + user.getValue().getLastName()));
        buttonColumn.setCellFactory(CellButton.forTableColumn("View User", (User u) -> {
            Data.other_user = u;
            FriendController.show();
            return null;
        }));
        ObservableList<User> modelUser = FXCollections.observableArrayList(service.itContains(search.getText()));
        table.setItems(modelUser);
    }

    @FXML
    private void logout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void receivedRequests() {
        ReceivedRequestsController.show();
    }

    @FXML
    private void sentRequests() {
        SentRequestsController.show();
    }

    @FXML
    private void friendships() { FriendshipsController.show(); }

    @FXML
    private void chats() throws Exception { ChatController.show(); }
}
