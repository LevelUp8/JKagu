package com.kagu.edit.jkagu.engine.actions;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;

public class RestoreAllText extends Command {

    private List<String> initialList;
    private Label statusMessage;

    public RestoreAllText(ObservableList<String> observableList, List<String> initialList, Label statusMessage) {
        super(observableList);
        this.initialList = initialList;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        observableList.clear();
        observableList.addAll(this.initialList);
        this.statusMessage.setText("Initial text loaded!");
        return true;
    }
}
