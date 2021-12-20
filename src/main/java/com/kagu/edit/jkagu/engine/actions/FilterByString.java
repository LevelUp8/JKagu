package com.kagu.edit.jkagu.engine.actions;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByString extends Command {

    private String filter;
    private Label statusMessage;

    public FilterByString(ObservableList<String> observableList, String filter, Label statusMessage) {
        super(observableList);
        this.filter = filter;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        List<String> filteredList = observableList.stream()
                                        .filter(s -> s.contains(this.filter))
                                            .collect(Collectors.toList());
        observableList.clear();
        observableList.addAll(filteredList);
        statusMessage.setText("Performing search by string");
        return true;
    }
}
