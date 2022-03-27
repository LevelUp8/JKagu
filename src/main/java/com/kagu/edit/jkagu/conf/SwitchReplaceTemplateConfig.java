package com.kagu.edit.jkagu.conf;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public record SwitchReplaceTemplateConfig(HBox findReplaceBox,
                                          HBox templateIncrementBox,
                                          MenuItem findAndReplace,
                                          MenuItem templateCounter,
                                          Label statusMessage,
                                          ChoiceBox<String> replaceWhere,
                                          CheckBox caseSensitive) implements ComponentConf {

    @Override
    public void configure() {

        findReplaceBox.setVisible(true);
        templateIncrementBox.setVisible(false);

        findAndReplace.setOnAction((e) -> {
            findReplaceBox.setVisible(true);
            templateIncrementBox.setVisible(false);

            statusMessage.setText("Incrementing template with replacement");
            // Workaround to activate the event on change
            replaceWhere.getSelectionModel().selectLast();
            replaceWhere.getSelectionModel().selectFirst();
        });

        templateCounter.setOnAction((e) -> {
            findReplaceBox.setVisible(false);
            templateIncrementBox.setVisible(true);

            statusMessage.setText("Find replace functionality");
            caseSensitive.setDisable(true);
            caseSensitive.setSelected(true);
        });

    }
}
