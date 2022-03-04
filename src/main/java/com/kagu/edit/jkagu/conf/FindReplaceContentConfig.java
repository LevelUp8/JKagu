package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.engine.actions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class FindReplaceContentConfig implements ComponentConf {

    public static final String ALL_OCCURRENCES = "All occurrences";
    public static final String FIRST_OCCURRENCE = "First occurrence";
    public static final String LAST_OCCURRENCE = "Last occurrence";
    public static final String WHOLE_ROW_WITH_OCCURRENCES = "Whole row containing";
    public static final String WITH_NEW_LINE = "With new line";
    public static final String NEW_LINE_WITH_ELEMENT = "New line with element";
    public static final String ADD_TO_BEGINNING_OF_THE_ROW = "Add to start";
    public static final String ADD_TO_END_OF_THE_ROW = "Add to end";
    // string array
    private static final String st[] = { ALL_OCCURRENCES,
            FIRST_OCCURRENCE,
            LAST_OCCURRENCE,
            WHOLE_ROW_WITH_OCCURRENCES,
            WITH_NEW_LINE,
            NEW_LINE_WITH_ELEMENT,
            ADD_TO_BEGINNING_OF_THE_ROW,
            ADD_TO_END_OF_THE_ROW };

    private final ChoiceBox<String> replaceWhere;
    private final TextField changeFrom;
    private final TextField changeTo;
    private final Button buttonRefactor;

    private ObservableList<Row> observableList;
    private String currentSelected;
    private Label statusMessage = null;

    private Map<String, BiFunction<String, String, Boolean>> selectionActionMap = new HashMap<>();

    {
        selectionActionMap.put(ALL_OCCURRENCES, (target, replacement) -> {
            ReplaceAll replace = new ReplaceAll(this.observableList, target, replacement, statusMessage);
            return replace.execute();
        });

        selectionActionMap.put(FIRST_OCCURRENCE, (target, replacement) -> {
            ReplaceFirst replace = new ReplaceFirst(this.observableList, target, replacement, statusMessage);
            return replace.execute();
        });

        selectionActionMap.put(LAST_OCCURRENCE, (target, replacement) -> {
            ReplaceLast replace = new ReplaceLast(this.observableList, target, replacement, statusMessage);
            return replace.execute();
        });

        selectionActionMap.put(WHOLE_ROW_WITH_OCCURRENCES, (target, replacement) -> {
            ReplaceLineContainingElement replace = new ReplaceLineContainingElement(this.observableList, target, replacement, statusMessage);
            return replace.execute();
        });


        selectionActionMap.put(WITH_NEW_LINE, (target, replacement) -> {
            ReplaceWithNewLine replace = new ReplaceWithNewLine(this.observableList, target, replacement, statusMessage);
            return replace.execute();
        });

        selectionActionMap.put(NEW_LINE_WITH_ELEMENT, (target, replacement) -> {
            ReplaceLineWithElement replace = new ReplaceLineWithElement(this.observableList, replacement, statusMessage);
            return replace.execute();
        });

        selectionActionMap.put(ADD_TO_BEGINNING_OF_THE_ROW, (target, replacement) -> {
            AddBeginningOfTheRow replace = new AddBeginningOfTheRow(this.observableList, replacement, statusMessage);
            return replace.execute();
        });

        selectionActionMap.put(ADD_TO_END_OF_THE_ROW, (target, replacement) -> {
            AddEndOfTheRow replace = new AddEndOfTheRow(this.observableList, replacement, statusMessage);
            return replace.execute();
        });

    }


    public FindReplaceContentConfig(ObservableList<Row> observableList,
                                    ChoiceBox<String> replaceWhere,
                                    TextField changeFrom,
                                    TextField changeTo,
                                    Button buttonRefactor,
                                    Label statusMessage) {
        this.replaceWhere = replaceWhere;
        this.changeFrom = changeFrom;
        this.changeTo = changeTo;
        this.buttonRefactor = buttonRefactor;
        this.observableList = observableList;
        this.statusMessage = statusMessage;
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
            //System.out.println(st[newValue.intValue()]);
            if(currentSelected.equals(NEW_LINE_WITH_ELEMENT) || currentSelected.equals(ADD_TO_BEGINNING_OF_THE_ROW) || currentSelected.equals(ADD_TO_END_OF_THE_ROW))
            {
                changeFrom.setDisable(true);
            }
            else
            {
                changeFrom.setDisable(false);
            }
        });

        buttonRefactor.setOnAction((ActionEvent e) -> {
                    String target = this.changeFrom.getText();
                    String replacement = this.changeTo.getText();

                    BiFunction<String, String, Boolean> currentAction = selectionActionMap.get(currentSelected);

                    if (currentAction != null) {
                        currentAction.apply(target, replacement);
                    } else {
                        throw new UnsupportedOperationException("Does not support currentSelected: " + currentSelected);
                    }

                }
        );
    }
}