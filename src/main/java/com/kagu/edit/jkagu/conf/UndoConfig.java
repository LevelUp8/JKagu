package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.Command;
import com.kagu.edit.jkagu.engine.actions.CommandHistory;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class UndoConfig implements ComponentConf {

    private MenuItem undo;
    private Label statusMessage;

    public UndoConfig(MenuItem undo, Label statusMessage) {
        this.undo = undo;
        this.statusMessage = statusMessage;
    }

    @Override
    public void configure() {
        undo.setOnAction((e) -> {
            if(!CommandHistory.isEmpty())
            {
                Command commandToUndo = CommandHistory.pop();
                commandToUndo.undo();
                statusMessage.setText("Undo is executed!");
            }
        });
    }
}
