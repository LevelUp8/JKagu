package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.HelloApplication;
import com.kagu.edit.jkagu.RowListViewCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpConfig implements ComponentConf {

    private final Stage primaryStage;
    private MenuItem about;
    private FXMLLoader aboutLoader;
    public static final String APP_CSS_FILE_PATH = "style.css";

    public HelpConfig(MenuItem about, Stage primaryStage) {
        this.about = about;
        this.primaryStage = primaryStage;
    }

    @Override
    public void configure() {

        about.setOnAction(
                actionEvent -> {
                    aboutLoader = new FXMLLoader(HelloApplication.class.getResource("help-popup.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(aboutLoader.load(), 300, 200);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String cssFile = HelloApplication.class.getResource(APP_CSS_FILE_PATH).toString();
                    scene.getStylesheets().add(cssFile);

                    String selectedTheme = ViewThemeConfig.getSelectedTheme();
                    if ("DARK".equals(selectedTheme)) {
                        String cssFileTheme = HelloApplication.class.getResource("dark-theme.css").toString();
                        scene.getStylesheets().add(cssFileTheme);
                    } else if ("DEFAULT".equals(selectedTheme)) {
                        String cssFileTheme = HelloApplication.class.getResource("dark-theme.css").toString();
                        scene.getStylesheets().remove(cssFileTheme);
                    } else {
                        throw new IllegalStateException("The theme is not recognized: " + selectedTheme);
                    }

                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    dialog.setScene(scene);
                    dialog.show();
                });
    }
}
