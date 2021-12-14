package com.kagu.edit.jkagu.engine.actions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    private List<String> backup;
    protected ObservableList<String> observableList;

    Command(ObservableList<String> observableList) {
        this.observableList = observableList;
        CommandHistory.push(this);
    }

    public void backup()
    {
        backup = new ArrayList<>(observableList);
    }

    public void undo() {
        this.observableList.removeAll();
        this.observableList.setAll(backup);
    }

    public abstract boolean execute();

    public ObservableList<String> getObservableList() {
        return observableList;
    }
}
