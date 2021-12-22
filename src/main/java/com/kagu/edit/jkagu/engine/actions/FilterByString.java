package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByString extends Command {

    private String filter;
    private Label statusMessage;

    public FilterByString(ObservableList<Row> observableList, String filter, Label statusMessage) {
        super(observableList);
        this.filter = filter;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        List<Row> filteredList = observableList.stream()
                                        .filter(r -> r.content().contains(this.filter))
                                            .collect(Collectors.toList());
        observableList.clear();
        observableList.addAll(filteredList);
        statusMessage.setText("Performing search by string");
        return true;
    }
}
