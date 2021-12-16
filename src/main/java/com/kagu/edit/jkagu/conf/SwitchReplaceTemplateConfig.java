package com.kagu.edit.jkagu.conf;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class SwitchReplaceTemplateConfig implements ComponentConf {

    private final HBox findReplaceBox;
    private final HBox templateIncrementBox;
    private final MenuItem findAndReplace;
    private final MenuItem templateCounter;

    public SwitchReplaceTemplateConfig(HBox findReplaceBox,
                                       HBox templateIncrementBox,
                                       MenuItem findAndReplace,
                                       MenuItem templateCounter)
    {
        this.findReplaceBox = findReplaceBox;
        this.templateIncrementBox = templateIncrementBox;
        this.findAndReplace = findAndReplace;
        this.templateCounter = templateCounter;
    }

    @Override
    public void configure() {

        findReplaceBox.setVisible(true);
        templateIncrementBox.setVisible(false);

        findAndReplace.setOnAction((e) -> {
            findReplaceBox.setVisible(true);
            templateIncrementBox.setVisible(false);
        });

        templateCounter.setOnAction((e) -> {
            findReplaceBox.setVisible(false);
            templateIncrementBox.setVisible(true);
        });

    }
}
