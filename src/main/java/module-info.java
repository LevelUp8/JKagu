module com.kagu.edit.jkagu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.kagu.edit.jkagu to javafx.fxml;
    exports com.kagu.edit.jkagu;
}