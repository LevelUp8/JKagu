package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByString extends Command {

    private final List<Row> initialList;
    private String filter;
    private Label statusMessage;

    public FilterByString(ObservableList<Row> observableList, List<Row> initialList, String filter, Label statusMessage) {
        super(observableList);
        this.initialList = initialList;
        this.filter = filter;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        observableList.clear();
        observableList.addAll(initialList);

        List<Row> filteredList = observableList.stream()
                .filter(r -> r.content().contains(this.filter))
                .collect(Collectors.toList());
        observableList.clear();
        observableList.addAll(filteredList);
        statusMessage.setText("Performing search by string");
        return true;
    }
}
