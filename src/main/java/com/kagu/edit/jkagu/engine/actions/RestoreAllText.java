package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;

public class RestoreAllText extends Command {

    private List<Row> initialList;
    private Label statusMessage;

    public RestoreAllText(ObservableList<Row> observableList, List<Row> initialList, Label statusMessage) {
        super(observableList);
        this.initialList = initialList;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        observableList.clear();
        observableList.addAll(this.initialList);
        statusMessage.setText("Initial text loaded!");
        return true;
    }
}
