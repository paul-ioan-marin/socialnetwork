package socialnetwork.gui;

import javafx.fxml.FXML;

public class AbstractController {
    @FXML
    protected void registerButtonClick() {
        RegisterController.show();
    }

    @FXML
    protected void loginButtonClick() {
        LoginController.show();
    }

    @FXML
    protected void adminButtonClick() {}
}
