package com.kagu.edit.jkagu.conf;

import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class CloseAppConfig  implements ComponentConf {

    private MenuItem closeApp;

    public CloseAppConfig(MenuItem closeApp)
    {
        this.closeApp = closeApp;
    }


    @Override
    public void configure() {
        closeApp.setOnAction(event -> {
            Stage stage = (Stage) closeApp.getParentPopup().getOwnerWindow();
            stage.close();
        });
    }
}
