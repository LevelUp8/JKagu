package com.kagu.edit.jkagu.conf;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.stream.Collectors;

public class CopyContentConfig implements  ComponentConf{


    private MenuItem copyText;
    private ObservableList<String> observableList;
    private Label statusMessage;

    public CopyContentConfig(MenuItem copyText,
                             ObservableList<String> observableList,
                             Label statusMessage) {

        this.copyText = copyText;
        this.observableList = observableList;
        this.statusMessage = statusMessage;
    }

    @Override
    public void configure() {
        copyText.setOnAction( e -> {

            System.out.println("Copy");
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            String text = observableList.stream().collect(Collectors.joining(System.lineSeparator()));
            content.putString(text);
            clipboard.setContent(content);

        });
    }
}
