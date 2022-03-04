package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class AddEndOfTheRow extends Command {

    private String replacement;
    private Label statusMessage;

    public AddEndOfTheRow(ObservableList<Row> observableList, String replacement, Label statusMessage) {
        super(observableList);
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    String newContent = row.content() + this.replacement;
                    return new Row(row.rowNumber(), newContent);
                }).collect(Collectors.toList());

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Add to the end of the rows!");
        return true;
    }
}
