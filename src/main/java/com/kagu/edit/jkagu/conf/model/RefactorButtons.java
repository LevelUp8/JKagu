package com.kagu.edit.jkagu.conf.model;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public record RefactorButtons(Button buttonRefactor, Button buttonRefactorTemplateInc, MenuItem findAndReplace,
                              MenuItem templateCounter) {
}
