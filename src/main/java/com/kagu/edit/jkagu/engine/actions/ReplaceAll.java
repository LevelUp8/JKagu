package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.List;
import java.util.regex.Pattern;

public class ReplaceAll extends CaseSensitiveCommand {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceAll(ObservableList<Row> observableList, String target, String replacement, Label statusMessage, CheckBox caseSensitive) {
        super(observableList, caseSensitive);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }


    @Override
    public boolean executeCaseSensitive() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    String newContent = row.content().replace(this.target, this.replacement);
                    return new Row(row.rowNumber(), newContent);
                }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace all occurrences executed!");
        return true;
    }

    @Override
    public boolean executeCaseInsensitive() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    String newContent = row.content().replaceAll("(?i)"+ Pattern.quote(this.target), this.replacement);;
                    return new Row(row.rowNumber(), newContent);
                }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace all occurrences executed!");
        return true;
    }
}
