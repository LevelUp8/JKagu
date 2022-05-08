package com.kagu.edit.jkagu.conf.model;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public record QuerySearchContext (HBox searchComboBoxContainer, ComboBox<String> searchComboBox) {}
