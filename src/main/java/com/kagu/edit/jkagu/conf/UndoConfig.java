package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.Command;
import com.kagu.edit.jkagu.engine.actions.CommandHistory;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public record UndoConfig(MenuItem undo,
                         Label statusMessage) implements ComponentConf {

    @Override
    public void configure() {
        undo.setOnAction((e) -> {
            if (!CommandHistory.isEmpty()) {
                Command commandToUndo = CommandHistory.pop();
                commandToUndo.undo();
                statusMessage.setText("Undo is executed!");
            }
        });
    }
}
