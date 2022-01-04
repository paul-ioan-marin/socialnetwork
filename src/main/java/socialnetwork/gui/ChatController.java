package socialnetwork.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.PersonalConstants;
import socialnetwork.domain.containers.GroupMessage;
import socialnetwork.gui.utils.*;
import socialnetwork.service.AbstractService;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static socialnetwork.domain.constants.PersonalConstants.PASSWORD;
import static socialnetwork.domain.constants.PersonalConstants.USERNAME;

public class ChatController implements Initializable {
    private AbstractService service;
    private User user;

    private GroupMessage currentGroup;
    private ReplyMessage currentReply;

    @FXML
    private ListView<MessageChat> chatBox;
    @FXML
    private TextField messageField;
    @FXML
    private Text chatName;
    @FXML
    private Button sendButton;

    @FXML
    private TableView<GroupMessage> chatsTable;
    @FXML
    private TableColumn<GroupMessage, String> chatsColumn;
    @FXML
    private TableColumn<GroupMessage, Button> buttonColumn;

    @FXML
    private Text replyText;
    @FXML
    private Button disableButton;

    @FXML
    private Button searchButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Data.logged_user;
        service = new UserService(new AbstractService(PersonalConstants.URL, USERNAME, PASSWORD), user);
        sendButton.setDisable(true);
        searchButton.setDisable(true);
        disableButton.setDisable(true);
        currentReply = null;
        replyText.setText("");
        try {
            setTable();
        } catch (Exception e) {
            Window.ALERT(e.getMessage());
        }
        chatName.setText("");
        if (Data.other_user != null) {
            try {
                currentGroup = service.toGroup(Data.other_user.getEmail());
                setChatBox(currentGroup);
                setTable();
            } catch (Exception e) {
                Window.ALERT(e.getMessage());
            }
        }
    }

    public static void show() throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(socialnetwork.gui.UserController.class.getResource("chat-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Chats");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Window.ALERT(e.getMessage());
            throw e;
        }
    }

    private void setTable() throws Exception {
        chatsColumn.setCellValueFactory(groupMessage -> new SimpleStringProperty(Messages.chatTitle(groupMessage.getValue(), user)));
        buttonColumn.setCellFactory(CellButton.forTableColumn("Chat!", (GroupMessage g) -> {
            try {
                currentGroup = g;
                setChatBox(g);
            } catch (Exception e) {
                Window.ALERT(e.getMessage());
            }
            return null;
        }));
        ObservableList<GroupMessage> model = FXCollections.observableArrayList(service.userGroups());
        chatsTable.setItems(model);
    }

    private void setChatBox(GroupMessage groupMessage) throws Exception {
        sendButton.setDisable(false);
        chatName.setText(Messages.chatTitle(groupMessage, user));
        chatBox.getItems().clear();
        chatBox.getItems().addAll(Messages.messagesInGroup(service, groupMessage, user));
        chatBox.scrollTo(500);
    }

    @FXML
    private void sendMessage() {
        if (!messageField.getText().equals("")) {
            ReplyMessage message;
            if (currentReply == null)
                message = new ReplyMessage(user, currentGroup, messageField.getText(), LocalDateTime.now());
            else {
                message = new ReplyMessage(currentReply, user, currentGroup, messageField.getText(), LocalDateTime.now());
                disableReply();
            }
            chatBox.getItems().add(new MessageChat(message, user));
            try {
                service.MessageBox(message);
            } catch (Exception e) {
                Window.ALERT(e.getMessage());
            }
            messageField.setText("");
            chatBox.scrollTo(500);
        }
    }

    @FXML
    private void replyMemorate() {
        currentReply = chatBox.getSelectionModel().getSelectedItem().value();
        replyText.setText("Reply to " + Messages.replyForm(currentReply.getMessage()));
        disableButton.setDisable(false);
    }

    @FXML
    private void disableReply() {
        replyText.setText("");
        currentReply = null;
        disableButton.setDisable(true);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void createGroup() {
        Data.finalList = null;
        Objects.requireNonNull(CreateGroupController.show()).setOnHiding(event -> Platform.runLater(() -> {
            if (Data.finalList != null) {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < Data.finalList.size(); i++) {
                    result.append(Data.finalList.get(i).getEmail());
                    if (i + 1 != Data.finalList.size())
                        result.append(",");
                }
                try {
                    currentGroup = service.toGroup(result.toString());
                    setChatBox(currentGroup);
                    setTable();
                } catch (Exception e) {
                    Window.ALERT(e.getMessage());
                }
            }
        }));
    }

    @FXML
    private void search() {
    }
}