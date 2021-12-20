package com.kagu.edit.jkagu.engine.actions;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;

public class ReplaceAll extends Command {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceAll(ObservableList<String> observableList, String target, String replacement, Label statusMessage) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
       List<String> replaced = observableList.stream()
                                    .map(s -> s.replace(this.target, this.replacement)).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace all occurrences executed!");
        return true;
    }
}
