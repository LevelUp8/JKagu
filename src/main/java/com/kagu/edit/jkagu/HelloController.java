package com.kagu.edit.jkagu;

import com.kagu.edit.jkagu.conf.OpenContentConfig;
import com.kagu.edit.jkagu.conf.SaveContentConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloController implements Serializable, Initializable {

    @FXML
    public ProgressBar progressBar;

    @FXML
    public Label statusMessage;

    @FXML
    public MenuItem openFile;

    @FXML
    public MenuItem saveFile;

    @FXML
    public ListView<String> listView;

    @FXML
    public ChoiceBox<String> replaceWhere;


    // Use Java Collections to create the List.
    List<String> list = new ArrayList<>();

    // Now add observability by wrapping it with ObservableList.
    ObservableList<String> observableList = FXCollections.observableList(list);

    // string array
    String st[] = { "In the end", "In the beginning" };


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        OpenContentConfig openContentConfig = new OpenContentConfig(progressBar, statusMessage, openFile, listView, observableList);
        openContentConfig.configure();

        SaveContentConfig saveContentConfig = new SaveContentConfig(observableList, saveFile);
        saveContentConfig.configure();

        replaceWhere.setItems(FXCollections.observableArrayList(st));

        replaceWhere.setValue(st[0]);
        // add a listener
        replaceWhere.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {

                // set the text for the label to the selected item
                //l1.setText(st[new_value.intValue()] + " selected");

                System.out.println(st[new_value.intValue()]);
            }
        });




    }


    public void configureScene(Scene scene) {
        // TODO add shortcut keys to to scene
    }
}