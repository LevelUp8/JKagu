package com.kagu.edit.jkagu.conf.model;

import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

public record QuerySearchContext (RadioButton useAdvancedSelect, HBox searchComboBoxContainer, ComboBox<String> searchComboBox) {}
