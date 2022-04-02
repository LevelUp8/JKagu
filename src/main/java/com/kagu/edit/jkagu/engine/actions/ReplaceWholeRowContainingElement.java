package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class ReplaceWholeRowContainingElement extends Command {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceWholeRowContainingElement(ObservableList<Row> observableList, String target, String replacement, Label statusMessage) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        List<Row> replaced = observableList.stream()
                .map(row -> {

                    String newContent;
                    if(row.content().contains(this.target))
                    {
                        newContent = this.replacement;
                    }
                    else
                    {
                        newContent = row.content();
                    }

                    return new Row(row.rowNumber(), newContent);
                }).collect(Collectors.toList());

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace all lines with occurrences with new content!");
        return true;
    }
}
