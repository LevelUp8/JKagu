package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.HelloApplication;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ViewThemeConfig implements ComponentConf {

    private MenuItem defaultTheme;
    private MenuItem darkTheme;


    public ViewThemeConfig(MenuItem defaultTheme, MenuItem darkTheme)
    {
        this.defaultTheme = defaultTheme;
        this.darkTheme = darkTheme;
    }

    @Override
    public void configure() {
        defaultTheme.setOnAction(e -> {
            Stage stage = (Stage) defaultTheme.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("dark-theme.css").toString();
            stage.getScene().getStylesheets().remove(cssFile);
        });

        darkTheme.setOnAction(e -> {
            Stage stage = (Stage) darkTheme.getParentPopup().getOwnerWindow();
            String cssFile = HelloApplication.class.getResource("dark-theme.css").toString();
            stage.getScene().getStylesheets().add(cssFile);
        });
    }
}
