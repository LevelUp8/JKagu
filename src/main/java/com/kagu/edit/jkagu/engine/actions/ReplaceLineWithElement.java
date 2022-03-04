package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReplaceLineWithElement extends Command {

    private String replacement;
    private Label statusMessage;

    public ReplaceLineWithElement(ObservableList<Row> observableList, String replacement, Label statusMessage) {
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

        String rowContent = replaced.stream().map(row -> row.content()).collect(Collectors.joining());

        List<Row> result = new ArrayList<>();
        result.add(new Row(1, rowContent));
        observableList.clear();
        observableList.addAll(result);
        statusMessage.setText("Replace new line with element was executed!");
        return true;
    }
}
