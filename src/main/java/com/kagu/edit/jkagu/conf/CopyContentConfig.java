package com.kagu.edit.jkagu.conf;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.stream.Collectors;

public record CopyContentConfig(MenuItem copyText,
                                ObservableList<String> observableList,
                                Label statusMessage) implements ComponentConf {


    @Override
    public void configure() {
        copyText.setOnAction(e -> {

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            String text = observableList.stream().collect(Collectors.joining(System.lineSeparator()));
            content.putString(text);
            clipboard.setContent(content);

            this.statusMessage.setText("Visible Content copied!");

        });
    }
}
