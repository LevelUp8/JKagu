package com.kagu.edit.jkagu.engine.actions.file;

import com.kagu.edit.jkagu.engine.actions.Command;
import com.kagu.edit.jkagu.engine.actions.CommandHistory;

public class Undo {

    public void execute() {
        if (CommandHistory.isEmpty()) return;

        Command command = CommandHistory.pop();
        if (command != null) {
            command.undo();
        }
    }

}
