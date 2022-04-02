package com.kagu.edit.jkagu.conf.model;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public record SingleLineSearchContext(RadioButton useSelectedLines,
                                      RadioButton useAdvancedSelect,
                                      TextField searchField,
                                      HBox searchFieldContainer) {
}
