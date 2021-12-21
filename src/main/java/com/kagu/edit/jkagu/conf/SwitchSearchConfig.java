package com.kagu.edit.jkagu.conf;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public record SwitchSearchConfig(HBox searchBox, HBox searchMultilineBox,
                                 MenuItem search, MenuItem searchMultiline,
                                 Label statusMessage) implements ComponentConf {

    @Override
    public void configure() {

        searchBox.setVisible(true);
        searchMultilineBox.setVisible(false);

        search.setOnAction((e) -> {
            searchBox.setVisible(true);
            searchMultilineBox.setVisible(false);
            statusMessage.setText("Use regular search");
        });

        searchMultiline.setOnAction((e) -> {
            searchBox.setVisible(false);
            searchMultilineBox.setVisible(true);
            statusMessage.setText("Use from until element search");
        });

    }
}
