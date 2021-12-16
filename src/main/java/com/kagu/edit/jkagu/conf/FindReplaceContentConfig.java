package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.ReplaceAll;
import com.kagu.edit.jkagu.engine.actions.ReplaceFirst;
import com.kagu.edit.jkagu.engine.actions.ReplaceLast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class FindReplaceContentConfig implements ComponentConf {

    public static final String ALL_OCCURRENCES = "All occurrences";
    public static final String FIRST_OCCURRENCE = "First occurrence";
    public static final String LAST_OCCURRENCE = "Last occurrence";
    // string array
    String st[] = {ALL_OCCURRENCES, FIRST_OCCURRENCE, LAST_OCCURRENCE};

    private ChoiceBox<String> replaceWhere;
    private TextField changeFrom;
    private TextField changeTo;
    private Button buttonRefactor;
    private ObservableList<String> observableList;
    private String currentSelected;

    private Map<String, BiFunction<String, String, Boolean>> selectionActionMap = new HashMap<>();
    {
        selectionActionMap.put(ALL_OCCURRENCES, (target, replacement) -> {
            ReplaceAll replace = new ReplaceAll(this.observableList, target, replacement);
            return replace.execute();
        });

        selectionActionMap.put(FIRST_OCCURRENCE, (target, replacement) -> {
            ReplaceFirst replace = new ReplaceFirst(this.observableList, target, replacement);
            return replace.execute();
        });

        selectionActionMap.put(LAST_OCCURRENCE, (target, replacement) -> {
            ReplaceLast replace = new ReplaceLast(this.observableList, target, replacement);
            return replace.execute();
        });
    }


    public FindReplaceContentConfig(ObservableList<String> observableList,
                                    ChoiceBox<String> replaceWhere,
                                    TextField changeFrom,
                                    TextField changeTo,
                                    Button buttonRefactor)
    {
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
        currentSelected = st[0];
        // add a listener
        replaceWhere.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) ->
        {

            // set the text for the label to the selected item
            //l1.setText(st[new_value.intValue()] + " selected");
            currentSelected = st[newValue.intValue()];
            System.out.println(st[newValue.intValue()]);
        });

        buttonRefactor.setOnAction((ActionEvent e) -> {
                String target = this.changeFrom.getText();
                String replacement = this.changeTo.getText();

                BiFunction<String, String, Boolean> currentAction = selectionActionMap.get(currentSelected);

                if(currentAction != null)
                {
                    currentAction.apply(target, replacement);
                }
                else {
                    throw new UnsupportedOperationException("Does not support currentSelected: " + currentSelected);
                }

            }
        );
    }
}
