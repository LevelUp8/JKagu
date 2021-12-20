package com.kagu.edit.jkagu.conf;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class SwitchSearchConfig implements ComponentConf {

    private final HBox searchMultilineBox;
    private final HBox searchBox;
    private final MenuItem search;
    private final MenuItem searchMultiline;
    private final Label statusMessage;

    public SwitchSearchConfig(HBox searchBox,
                              HBox searchMultilineBox,
                              MenuItem search,
                              MenuItem searchMultiline,
                              Label statusMessage)
    {
        this.searchBox = searchBox;
        this.searchMultilineBox = searchMultilineBox;
        this.search = search;
        this.searchMultiline = searchMultiline;
        this.statusMessage = statusMessage;
    }

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
