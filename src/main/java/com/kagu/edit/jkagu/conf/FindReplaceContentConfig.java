package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.engine.actions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class FindReplaceContentConfig implements ComponentConf {

    private static final String ALL_OCCURRENCES = "All word occurrences";
    private static final String FIRST_OCCURRENCE = "First word occurrence";
    private static final String LAST_OCCURRENCE = "Last word occurrence";
    private static final String REMOVE_FROM_START_UNTIL_END = "Remove start to end of row";
    private static final String WHOLE_ROW_WITH_OCCURRENCES = "Change row containing";
    private static final String SPLIT_TO_NEW_ROWS_ON_WORD ="Split to new rows on word";
    private static final String ALL_CONTENT_IN_ONE_ROW = "All content in one row";
    private static final String ADD_TO_BEGINNING_OF_THE_ROW = "Add to start of each row";
    private static final String ADD_TO_END_OF_THE_ROW = "Add to end of each row";

    // string array
    private static final String st[] = { ALL_OCCURRENCES,
                                        FIRST_OCCURRENCE,
                                        LAST_OCCURRENCE,
                                        REMOVE_FROM_START_UNTIL_END,
                                        WHOLE_ROW_WITH_OCCURRENCES,
                                        SPLIT_TO_NEW_ROWS_ON_WORD,
                                        ALL_CONTENT_IN_ONE_ROW,
                                        ADD_TO_BEGINNING_OF_THE_ROW,
                                        ADD_TO_END_OF_THE_ROW };

    private final ChoiceBox<String> replaceWhere;
    private final TextField changeFrom;
    private final TextField changeTo;
    private final Button buttonRefactor;
    private final CheckBox caseSensitive;

    private ObservableList<Row> observableList;
    private String currentSelected;

    private static final Map<String, BiFunction<String, String, Command>> selectionActionMap = new HashMap<>();
    private static final Map<String, Class<? extends Command>> classRelationActionMap = new HashMap<>();

    public FindReplaceContentConfig(ObservableList<Row> observableList,
                                    ChoiceBox<String> replaceWhere,
                                    TextField changeFrom,
                                    TextField changeTo,
                                    Button buttonRefactor,
                                    Label statusMessage,
                                    CheckBox caseSensitive) {
        this.replaceWhere = replaceWhere;
        this.changeFrom = changeFrom;
        this.changeTo = changeTo;
        this.buttonRefactor = buttonRefactor;
        this.observableList = observableList;
        this.caseSensitive = caseSensitive;


        selectionActionMap.put(ALL_OCCURRENCES, (target, replacement) -> new ReplaceAll(this.observableList, target, replacement, statusMessage, caseSensitive));
        classRelationActionMap.put(ALL_OCCURRENCES, ReplaceAll.class);

        selectionActionMap.put(FIRST_OCCURRENCE, (target, replacement) -> new ReplaceFirst(this.observableList, target, replacement, statusMessage, caseSensitive));
        classRelationActionMap.put(FIRST_OCCURRENCE, ReplaceFirst.class);

        selectionActionMap.put(LAST_OCCURRENCE, (target, replacement) -> new ReplaceLast(this.observableList, target, replacement, statusMessage, caseSensitive));
        classRelationActionMap.put(LAST_OCCURRENCE, ReplaceLast.class);

        selectionActionMap.put(REMOVE_FROM_START_UNTIL_END, (start, end) -> new RemoveFromStartUntilEnd(this.observableList, start, end, statusMessage));
        classRelationActionMap.put(REMOVE_FROM_START_UNTIL_END, RemoveFromStartUntilEnd.class);

        selectionActionMap.put(WHOLE_ROW_WITH_OCCURRENCES, (target, replacement) -> new ReplaceWholeRowContainingElement(this.observableList, target, replacement, statusMessage, caseSensitive));
        classRelationActionMap.put(WHOLE_ROW_WITH_OCCURRENCES, ReplaceWholeRowContainingElement.class);

        selectionActionMap.put(SPLIT_TO_NEW_ROWS_ON_WORD, (target, replacement) -> new SplitToRowsBasedOnWord(this.observableList, target, replacement, statusMessage, caseSensitive));
        classRelationActionMap.put(SPLIT_TO_NEW_ROWS_ON_WORD, SplitToRowsBasedOnWord.class);

        selectionActionMap.put(ALL_CONTENT_IN_ONE_ROW, (target, replacement) -> new AllContentInOneRow(this.observableList, replacement, statusMessage));
        classRelationActionMap.put(ALL_CONTENT_IN_ONE_ROW, AllContentInOneRow.class);

        selectionActionMap.put(ADD_TO_BEGINNING_OF_THE_ROW, (target, replacement) -> new AddBeginningOfTheRow(this.observableList, replacement, statusMessage));
        classRelationActionMap.put(ADD_TO_BEGINNING_OF_THE_ROW, AddBeginningOfTheRow.class);

        selectionActionMap.put(ADD_TO_END_OF_THE_ROW, (target, replacement) -> new AddEndOfTheRow(this.observableList, replacement, statusMessage));
        classRelationActionMap.put(ADD_TO_END_OF_THE_ROW, AddEndOfTheRow.class);

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

            Class<? extends Command> relatedActionClazz = classRelationActionMap.get(currentSelected);
            if(null != relatedActionClazz && CaseSensitiveCommand.class.isAssignableFrom(relatedActionClazz))
            {
                caseSensitive.setDisable(false);
            }
            else
            {
                caseSensitive.setDisable(true);
                caseSensitive.setSelected(true);
            }

            //System.out.println(st[newValue.intValue()]);
            if(currentSelected.equals(ALL_CONTENT_IN_ONE_ROW) || currentSelected.equals(ADD_TO_BEGINNING_OF_THE_ROW) || currentSelected.equals(ADD_TO_END_OF_THE_ROW))
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

                    BiFunction<String, String, Command> currentAction = selectionActionMap.get(currentSelected);

                    if (currentAction != null) {
                        Command command = currentAction.apply(target, replacement);
                        command.execute();
                    } else {
                        throw new UnsupportedOperationException("Does not support currentSelected: " + currentSelected);
                    }

                }
        );
    }

}