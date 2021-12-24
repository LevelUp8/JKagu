package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.Utils;
import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;

public class ReplaceFirst extends Command {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceFirst(ObservableList<Row> observableList, String target, String replacement, Label statusMessage) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    int firstOccurrenceIndex = row.content().indexOf(target);
                    final String result = Utils.getReplacedString(row.content(), firstOccurrenceIndex, target, replacement);
                    return new Row(row.rowNumber(), result);
                }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace first occurrence of the element on every row executed!");

        return true;
    }
}
