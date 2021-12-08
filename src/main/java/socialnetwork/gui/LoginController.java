package socialnetwork.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import socialnetwork.domain.User;
import socialnetwork.service.AbstractService;

public class LoginController {
    private AbstractService service;
    private User user_app = null;

    public void setService(AbstractService service) {
        this.service = service;
    }
    //textfields
    @FXML
    TextField textFieldFirstName;

    @FXML
    TextField textFieldLastName;


    //buttons
    @FXML
    Button btnLogin;

    @FXML
    Button btnCancel;

    @FXML
    public void initialize() {


        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(textFieldFirstName.getText().isEmpty() || textFieldLastName.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Numele si prenumele nu trebuie sa fie nule!");
                    alert.showAndWait();
                }
                else{
                    try {
                        user_app = service.findUserByEmail(textFieldFirstName.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //user_app = loginUserbyLastFirstName(textFieldFirstName.getText(),textFieldLastName.getText());
                    if(user_app == null){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Userul introdus nu exista!");
                        alert.showAndWait();
                        textFieldFirstName.clear();
                        textFieldLastName.clear();
                    }
                    else{
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();
                    }
                }
            }
        });

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
            }
        });
    }

    public User getUtilizatorAplicatie(){
        return user_app;
    }
}
