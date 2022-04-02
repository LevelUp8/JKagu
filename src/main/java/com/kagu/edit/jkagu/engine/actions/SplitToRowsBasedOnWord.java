package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SplitToRowsBasedOnWord extends CaseSensitiveCommand {

    private String target;
    private Label statusMessage;
    private String replacement;

    public SplitToRowsBasedOnWord(ObservableList<Row> observableList, String target, String replacement, Label statusMessage, CheckBox caseSensitive) {
        super(observableList, caseSensitive);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }


    @Override
    public boolean executeCaseSensitive() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    List<Row> newRows = new ArrayList<>();
                    String newContent = row.content().replace(this.target, replacement + "\n");
                    long count = row.rowNumber();
                    for(String newRow : newContent.split("\n"))
                    {
                        newRows.add(new Row(count, newRow));
                        count++;
                    }
                    return newRows;
                }).flatMap(s -> s.stream()).collect(Collectors.toList());

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace with new line executed!");
        return true;
    }

    @Override
    public boolean executeCaseInsensitive() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    List<Row> newRows = new ArrayList<>();
                    String newContent = row.content().replaceAll("(?i)"+ Pattern.quote(this.target), replacement + "\n");
                    long count = row.rowNumber();
                    for(String newRow : newContent.split("\n"))
                    {
                        newRows.add(new Row(count, newRow));
                        count++;
                    }
                    return newRows;
                }).flatMap(s -> s.stream()).collect(Collectors.toList());

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace with new line executed!");
        return true;
    }
}
