package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.HelloApplication;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ViewThemeConfig implements ComponentConf {

    private MenuItem defaultTheme;
    private MenuItem darkTheme;
    private Label statusMessage;

    public ViewThemeConfig(MenuItem defaultTheme, MenuItem darkTheme, Label statusMessage)
    {
        this.defaultTheme = defaultTheme;
        this.darkTheme = darkTheme;
        this.statusMessage = statusMessage;
    }

    @Override
    public void configure() {
        defaultTheme.setOnAction(e -> {
            Stage stage = (Stage) defaultTheme.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("dark-theme.css").toString();
            stage.getScene().getStylesheets().remove(cssFile);
            statusMessage.setText("Default theme is selected!");
        });

        darkTheme.setOnAction(e -> {
            Stage stage = (Stage) darkTheme.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("dark-theme.css").toString();
            stage.getScene().getStylesheets().add(cssFile);
            statusMessage.setText("Dark theme is selected!");
        });
    }
}
