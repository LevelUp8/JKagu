package com.kagu.edit.jkagu.conf;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public record SwitchReplaceTemplateConfig(HBox findReplaceBox,
                                          HBox templateIncrementBox,
                                          MenuItem findAndReplace,
                                          MenuItem templateCounter,
                                          Label statusMessage) implements ComponentConf {

    @Override
    public void configure() {

        findReplaceBox.setVisible(true);
        templateIncrementBox.setVisible(false);

        findAndReplace.setOnAction((e) -> {
            findReplaceBox.setVisible(true);
            templateIncrementBox.setVisible(false);
            statusMessage.setText("Incrementing template with replacement");
        });

        templateCounter.setOnAction((e) -> {
            findReplaceBox.setVisible(false);
            templateIncrementBox.setVisible(true);
            statusMessage.setText("Find replace functionality");
        });

    }
}
