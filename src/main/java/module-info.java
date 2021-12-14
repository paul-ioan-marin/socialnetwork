module socialnetwork.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens socialnetwork.gui to javafx.fxml;
    exports socialnetwork.gui;

    opens socialnetwork.domain to javafx.graphics, javafx.fxml, javafx.base;
}