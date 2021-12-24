package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.engine.query.QueryParser;
import com.kagu.edit.jkagu.engine.query.SimpleQueryParser;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByQuery extends Command {

    private final List<Row> initialList;
    private String query;
    private Label statusMessage;

    public FilterByQuery(ObservableList<Row> observableList, List<Row> initialList, String query, Label statusMessage) {
        super(observableList);
        this.initialList = initialList;
        this.query = query;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        observableList.clear();
        observableList.addAll(initialList);

        QueryParser parser = new SimpleQueryParser(query);
        List<Row> filteredList = observableList.stream()
                                        .filter(r -> parser.execute(r.content()).isPresent())
                                            .collect(Collectors.toList());
        observableList.clear();
        observableList.addAll(filteredList);
        statusMessage.setText("Performing search by query");
        return true;
    }
}
