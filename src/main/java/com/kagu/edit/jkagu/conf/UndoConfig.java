package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.Command;
import com.kagu.edit.jkagu.engine.actions.CommandHistory;
import javafx.scene.control.MenuItem;

public class UndoConfig implements ComponentConf {

    MenuItem undo;

    public UndoConfig(MenuItem undo) {
        this.undo = undo;
    }

    @Override
    public void configure() {
        undo.setOnAction((e) -> {
            if(!CommandHistory.isEmpty())
            {
                Command commandToUndo = CommandHistory.pop();
                commandToUndo.undo();
            }
        });
    }
}
