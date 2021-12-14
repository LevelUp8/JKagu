package com.kagu.edit.jkagu;

import com.kagu.edit.jkagu.conf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Serializable, Initializable {

    @FXML
    public RadioButton useWholeFile;

    @FXML
    public RadioButton useSelectedLines;

    @FXML
    public Button searchButton;

    @FXML
    public TextField searchField;

    @FXML
    public ProgressBar progressBar;

    @FXML
    public Label statusMessage;

    @FXML
    public MenuItem openFile;

    @FXML
    public MenuItem saveFile;

    @FXML
    public MenuItem copyText;

    @FXML
    public MenuItem pasteText;

    @FXML
    public ListView<String> listView;

    @FXML
    public ChoiceBox<String> replaceWhere;

    @FXML
    public TextField changeTo;

    @FXML
    public TextField changeFrom;

    @FXML
    public Button buttonRefactor;


    // Use Java Collections to create the List.
    private List<String> initialList = new ArrayList<>();

    // Use Java Collections to create the List.
    private List<String> list = new ArrayList<>();

    // Now add observability by wrapping it with ObservableList.
    private ObservableList<String> observableList = FXCollections.observableList(list);


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        FilterContentConfig filterContentConfig = new FilterContentConfig(useWholeFile,
                                                                        useSelectedLines,
                                                                        searchButton,
                                                                        observableList,
                                                                        initialList,
                                                                        searchField,
                                                                        statusMessage);
        filterContentConfig.configure();

        OpenContentConfig openContentConfig = new OpenContentConfig(progressBar, statusMessage, openFile, listView, observableList, initialList);
        openContentConfig.configure();

        SaveContentConfig saveContentConfig = new SaveContentConfig(observableList, saveFile);
        saveContentConfig.configure();

        PasteContentConfig pasteContentConfig = new PasteContentConfig(pasteText, observableList, statusMessage, initialList, listView);
        pasteContentConfig.configure();

        CopyContentConfig copyContentConfig = new CopyContentConfig(copyText, observableList, statusMessage);
        copyContentConfig.configure();

        FindReplaceContentConfig findReplaceContentConfig = new FindReplaceContentConfig(observableList, replaceWhere, changeFrom, changeTo, buttonRefactor);
        findReplaceContentConfig.configure();
    }


    public void configureScene(Scene scene) {
        // TODO add shortcut keys to to scene
    }
}