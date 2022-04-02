package com.kagu.edit.jkagu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static final String APP_FXML_FILE_PATH = "hello-view.fxml";
    public static final String APP_CSS_FILE_PATH = "style.css";
    public static final String APP_CSS_DEFAULT_TEXT_FILE_PATH = "default-text.css";
    public static final String APP_TITLE = "JKagu v4.0.0";

    public static HelloApplication helloApplication;
    public HelloApplication()
    {
        helloApplication = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(APP_FXML_FILE_PATH));

        Scene scene = new Scene(fxmlLoader.load());
        String cssFile = this.getClass().getResource(APP_CSS_FILE_PATH).toString();
        scene.getStylesheets().add(cssFile);

        String cssTextDefaultFile = this.getClass().getResource(APP_CSS_DEFAULT_TEXT_FILE_PATH).toString();
        scene.getStylesheets().add(cssTextDefaultFile);

        stage.setTitle(APP_TITLE);

        HelloController controller = (HelloController) fxmlLoader.getController();
        controller.configureScene(scene);

        controller.configureStage(stage);

        stage.setScene(scene);
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static HelloApplication getApplication()
    {
        return helloApplication;
    }
}