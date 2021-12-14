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
import socialnetwork.domain.containers.UserList;
import socialnetwork.gui.utils.CellButton;
import socialnetwork.gui.utils.Data;
import socialnetwork.service.AbstractService;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.PersonalConstants.*;

public class UserController implements Initializable {
    private AbstractService service;
    private User user;

    @FXML
    protected Text welcomeText;
    @FXML
    protected TextField search;

    @FXML
    private TableView<User> table;
    @FXML
    protected TableColumn<User, String> emailColumn;
    @FXML
    protected TableColumn<User, Button> buttonColumn;
    @FXML
    protected Button searchButton;



    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        user = Data.logged_user;
        headerSet();
        service = new UserService(new AbstractService(URL, USERNAME, PASSWORD), user);
    }

    public static void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("User");
        stage.setScene(scene);
        stage.show();
    }

    private void headerSet() {
        welcomeText.setText("Welcome " + Data.logged_user.getFirstName() + " " + Data.logged_user.getLastName() + "!");
    }

    @FXML
    protected void pressSearch() throws Exception {
        emailColumn.setCellValueFactory(user -> new SimpleStringProperty
                (user.getValue().getFirstName() + " " + user.getValue().getLastName()));
        buttonColumn.setCellFactory(CellButton.<User>forTableColumn("View User", (User u) -> {
            Data.other_user = u;
            FriendController.show();
            return null;
        }));
        String s = search.getText();
        UserList u = service.itContains(search.getText());
        ObservableList<User> modelUser = FXCollections.observableArrayList(service.itContains(search.getText()));
        table.setItems(modelUser);
    }
}
