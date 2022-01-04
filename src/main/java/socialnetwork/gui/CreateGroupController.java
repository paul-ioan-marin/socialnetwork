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
import javafx.stage.Stage;
import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.PersonalConstants;
import socialnetwork.gui.utils.CellButton;
import socialnetwork.gui.utils.Data;
import socialnetwork.gui.utils.Window;
import socialnetwork.service.AbstractService;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static socialnetwork.domain.constants.PersonalConstants.PASSWORD;
import static socialnetwork.domain.constants.PersonalConstants.USERNAME;

public class CreateGroupController implements Initializable {
    private AbstractService service;
    private User user;

    private List<User> currentGroup;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<FriendshipWithStatus> createGroupTable;
    @FXML
    private TableColumn<FriendshipWithStatus, String> foundFriendsColumn;
    @FXML
    private TableColumn<FriendshipWithStatus, Button> addColumn;
    @FXML
    private TableColumn<FriendshipWithStatus, Button> removeColumn;

    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Data.logged_user;
        service = new UserService(new AbstractService(PersonalConstants.URL, USERNAME, PASSWORD), user);
        currentGroup = new ArrayList<>();
    }

    public static Stage show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(socialnetwork.gui.UserController.class.getResource("create-group-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Create Group");
            stage.setScene(scene);
            stage.show();
            return stage;
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
        }
        return null;
    }

    private void setTable(List<FriendshipWithStatus> currentList) {
        foundFriendsColumn.setCellValueFactory(friendship -> { User other_user = friendship.getValue().theOtherFriend(user);
            return new SimpleStringProperty(other_user.getFirstName() + " " + other_user.getLastName()); });
        addColumn.setCellFactory(CellButton.forTableColumn("Add", (FriendshipWithStatus f) -> {
            User currentUser = f.theOtherFriend(user);
            currentGroup.add(currentUser);
            return null;
        }));
        removeColumn.setCellFactory(CellButton.forTableColumn("Remove", (FriendshipWithStatus f) -> {
            User currentUser = f.theOtherFriend(user);
            currentGroup.remove(currentUser);
            return null;
        }));
        ObservableList<FriendshipWithStatus> modelUser = FXCollections.observableArrayList(currentList);
        createGroupTable.setItems(modelUser);
    }

    @FXML
    private void search() {
        try {
        List<FriendshipWithStatus> found = service.acceptedFriendships().stream()
                .filter(friendship -> {
                    User currentUser = friendship.theOtherFriend(user);
                    String str = currentUser.getEmail() + ";" + currentUser.getFirstName() + " " + currentUser.getLastName();
                    return str.toLowerCase(Locale.ROOT).contains(searchField.getText().toLowerCase(Locale.ROOT));
                })
                .collect(Collectors.toList());
        setTable(found);
        } catch (Exception e) {
            Window.ALERT(e.getMessage());
        }
    }

    @FXML
    private void create() {
        if (currentGroup.size() < 3) {
            Window.ALERT("The group needs at least 3 users");
        } else {
            Data.finalList = currentGroup;
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
