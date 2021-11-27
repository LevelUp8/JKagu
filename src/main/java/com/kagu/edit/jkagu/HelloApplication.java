package com.kagu.edit.jkagu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static final double APP_WIDTH = 1300;
    public static final double APP_HEIGHT = 1000;

    public static final String APP_FXML_FILE_PATH = "hello-view.fxml";
    public static final String APP_CSS_FILE_PATH = "style.css";
    public static final String APP_TITLE = "JKagu v1.0";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(APP_FXML_FILE_PATH));

        Scene scene = new Scene(fxmlLoader.load(), APP_WIDTH, APP_HEIGHT);
        String cssFile = this.getClass().getResource(APP_CSS_FILE_PATH).toString();
        scene.getStylesheets().add(cssFile);


        stage.setTitle(APP_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}