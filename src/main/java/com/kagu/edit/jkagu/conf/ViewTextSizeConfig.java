package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.HelloApplication;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ViewTextSizeConfig implements ComponentConf {

    private MenuItem defaultTextSize;
    private MenuItem bigTextSize;
    private Label statusMessage;

    public ViewTextSizeConfig(MenuItem defaultTextSize, MenuItem bigTextSize, Label statusMessage) {
        this.defaultTextSize = defaultTextSize;
        this.bigTextSize = bigTextSize;
        this.statusMessage = statusMessage;
    }


    @Override
    public void configure() {
        defaultTextSize.setOnAction(e -> {
            Stage stage = (Stage) defaultTextSize.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("big-text.css").toString();
            stage.getScene().getStylesheets().remove(cssFile);

            String cssFileDefault = HelloApplication.class.getResource("default-text.css").toString();
            stage.getScene().getStylesheets().add(cssFileDefault);
            statusMessage.setText("Default theme is selected!");
        });

        bigTextSize.setOnAction(e -> {
            Stage stage = (Stage) bigTextSize.getParentPopup().getOwnerWindow();
            String cssFileDefault = HelloApplication.class.getResource("default-text.css").toString();
            stage.getScene().getStylesheets().remove(cssFileDefault);

            String cssFile = HelloApplication.class.getResource("big-text.css").toString();
            stage.getScene().getStylesheets().add(cssFile);
            statusMessage.setText("Big text size!");

        });
    }

    public Label getStatusMessage() {
        return statusMessage;
    }
}
