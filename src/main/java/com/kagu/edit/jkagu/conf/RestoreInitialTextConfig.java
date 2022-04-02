package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.engine.actions.RestoreAllText;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.util.List;

public class RestoreInitialTextConfig implements ComponentConf {

    private final MenuItem restoreInitialText;
    private final ObservableList<Row> observableList;
    private final List<Row> initialList;
    private final Label statusMessage;

    public RestoreInitialTextConfig(ObservableList<Row> observableList, List<Row> initialList, Label statusMessage, MenuItem restoreInitialText) {
        this.observableList = observableList;
        this.initialList = initialList;
        this.statusMessage = statusMessage;
        this.restoreInitialText = restoreInitialText;
    }

    @Override
    public void configure() {

        this.restoreInitialText.setOnAction( e -> {
            RestoreAllText restoreAllText = new RestoreAllText(this.observableList, this.initialList, this.statusMessage);
            restoreAllText.execute();
        });

    }
}
