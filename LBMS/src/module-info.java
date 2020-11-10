module LBMS {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens gui to javafx.fxml;

    exports system to javafx.graphics;
    exports gui to javafx.fxml;

    exports data_classes;
}