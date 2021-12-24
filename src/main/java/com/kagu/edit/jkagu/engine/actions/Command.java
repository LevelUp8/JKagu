package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    private List<Row> backup;
    protected ObservableList<Row> observableList;

    Command(ObservableList<Row> observableList) {
        this.observableList = observableList;
        CommandHistory.push(this);
        backup();
    }

    public void backup() {
        backup = new ArrayList<>(observableList);
    }

    public void undo() {
        this.observableList.clear();
        this.observableList.setAll(backup);
    }

    public abstract boolean execute();

    public ObservableList<Row> getObservableList() {
        return observableList;
    }
}
