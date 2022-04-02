module com.kagu.edit.jkagu {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kagu.edit.jkagu to javafx.fxml;
    exports com.kagu.edit.jkagu;
    exports com.kagu.edit.jkagu.conf.model;
}