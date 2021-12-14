package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.ReplaceAll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class FindReplaceContentConfig implements ComponentConf {

    // string array
    String st[] = { "All occurrences", "First the beginning" };

    private ChoiceBox<String> replaceWhere;
    private TextField changeFrom;
    private TextField changeTo;
    private Button buttonRefactor;
    private ObservableList<String> observableList;


    public FindReplaceContentConfig(ObservableList<String> observableList, ChoiceBox<String> replaceWhere, TextField changeFrom, TextField changeTo, Button buttonRefactor) {
        this.replaceWhere = replaceWhere;
        this.changeFrom = changeFrom;
        this.changeTo = changeTo;
        this.buttonRefactor = buttonRefactor;
        this.observableList = observableList;
    }

    @Override
    public void configure() {
        replaceWhere.setItems(FXCollections.observableArrayList(st));

        replaceWhere.setValue(st[0]);
        // add a listener
        replaceWhere.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) ->
            {

                // set the text for the label to the selected item
                //l1.setText(st[new_value.intValue()] + " selected");

                System.out.println(st[newValue.intValue()]);
            });

        buttonRefactor.setOnAction((ActionEvent e) -> {
                String target = this.changeFrom.getText();
                String replacement = this.changeTo.getText();
                ReplaceAll replaceAll = new ReplaceAll(this.observableList, target, replacement);
                replaceAll.execute();
            }
        );
    }
}
