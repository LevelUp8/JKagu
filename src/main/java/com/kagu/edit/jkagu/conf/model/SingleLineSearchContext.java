package com.kagu.edit.jkagu.conf.model;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public record SingleLineSearchContext(TextField searchField,
                                      HBox searchFieldContainer) {
}
