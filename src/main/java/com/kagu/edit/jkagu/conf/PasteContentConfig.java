package com.kagu.edit.jkagu.conf;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;

import java.util.List;

public class PasteContentConfig implements  ComponentConf{

    private MenuItem pasteText;
    private ObservableList<String> observableList;
    private Label statusMessage;
    private List<String> initialList;
    private ListView<String> listView;

    public PasteContentConfig(MenuItem pasteText,
                              ObservableList<String> observableList,
                              Label statusMessage,
                              List<String> initialList,
                              ListView<String> listView) {

        this.pasteText = pasteText;
        this.observableList = observableList;
        this.statusMessage = statusMessage;
        this.initialList = initialList;
        this.listView = listView;
    }

    @Override
    public void configure() {
        pasteText.setOnAction( e -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final String text = clipboard.getString();

            initialList.clear();
            observableList.clear();
            String[] lines = text.split("\\r?\\n");
            for(String line: lines)
            {
                observableList.add(line);
                initialList.add(line);
            }

            listView.setItems(observableList);

            statusMessage.setText("Paste successfully done!");
        });
    }

}
