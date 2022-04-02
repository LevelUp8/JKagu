package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.Utils;
import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.List;

public class ReplaceLast extends CaseSensitiveCommand {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceLast(ObservableList<Row> observableList, String target, String replacement, Label statusMessage, CheckBox caseSensitive) {
        super(observableList, caseSensitive);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean executeCaseSensitive() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    int lastOccurrenceIndex = row.content().lastIndexOf(target);
                    final String result = Utils.getReplacedString(row.content(), lastOccurrenceIndex, target, replacement);
                    return new Row(row.rowNumber(), result);
                }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace last occurrence of element on every row executed!");
        return true;
    }

    @Override
    public boolean executeCaseInsensitive() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    String oneRow = row.content();
                    String oneRowLowercase = oneRow.toLowerCase();
                    String targetLowerCase = target.toLowerCase();
                    int lastOccurrenceIndex = oneRowLowercase.lastIndexOf(targetLowerCase);
                    final String result = Utils.getReplacedString(row.content(), lastOccurrenceIndex, target, replacement);
                    return new Row(row.rowNumber(), result);
                }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace last occurrence of element on every row executed!");
        return true;
    }

}
