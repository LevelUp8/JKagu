package com.kagu.edit.jkagu.conf.model;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public record InputOutputButtons(Menu file, MenuItem openFile, MenuItem saveFile, MenuItem copyText, MenuItem pasteText) {}
