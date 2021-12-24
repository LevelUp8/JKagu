package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.HelloApplication;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public record ViewThemeConfig(MenuItem defaultTheme, MenuItem darkTheme,
                              Label statusMessage) implements ComponentConf {

    private static String selectedTheme = "DEFAULT";

    @Override
    public void configure() {
        defaultTheme.setOnAction(e -> {
            Stage stage = (Stage) defaultTheme.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("dark-theme.css").toString();
            stage.getScene().getStylesheets().remove(cssFile);
            statusMessage.setText("Default theme is selected!");
            selectedTheme = "DEFAULT";
        });

        darkTheme.setOnAction(e -> {
            Stage stage = (Stage) darkTheme.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("dark-theme.css").toString();
            stage.getScene().getStylesheets().add(cssFile);
            statusMessage.setText("Dark theme is selected!");
            selectedTheme = "DARK";
        });
    }

    public static String getSelectedTheme()
    {
        return selectedTheme;
    }
}
