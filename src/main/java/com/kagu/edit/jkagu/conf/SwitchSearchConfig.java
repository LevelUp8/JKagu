package com.kagu.edit.jkagu.conf;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class SwitchSearchConfig implements ComponentConf {

    private final HBox searchMultilineBox;
    private final HBox searchBox;
    private final MenuItem search;
    private final MenuItem searchMultiline;

    public SwitchSearchConfig(HBox searchBox,
                              HBox searchMultilineBox,
                              MenuItem search,
                              MenuItem searchMultiline)
    {
        this.searchBox = searchBox;
        this.searchMultilineBox = searchMultilineBox;
        this.search = search;
        this.searchMultiline = searchMultiline;
    }

    @Override
    public void configure() {

        searchBox.setVisible(true);
        searchMultilineBox.setVisible(false);

        search.setOnAction((e) -> {
            searchBox.setVisible(true);
            searchMultilineBox.setVisible(false);
        });

        searchMultiline.setOnAction((e) -> {
            searchBox.setVisible(false);
            searchMultilineBox.setVisible(true);
        });

    }
}
