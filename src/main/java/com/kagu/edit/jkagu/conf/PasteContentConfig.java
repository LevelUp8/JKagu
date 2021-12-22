package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;

import java.util.List;

public record PasteContentConfig(MenuItem pasteText,
                                 ObservableList<Row> observableList,
                                 Label statusMessage, List<Row> initialList,
                                 ListView<Row> listView) implements ComponentConf {

    @Override
    public void configure() {
        pasteText.setOnAction(e -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final String text = clipboard.getString();

            initialList.clear();
            observableList.clear();
            String[] lines = text.split("\\r?\\n");
            long rowNumber = 0;
            for (String line : lines) {
                rowNumber++;
                Row row = new Row(rowNumber, line);
                observableList.add(row);
                initialList.add(row);
            }

            listView.setItems(observableList);

            statusMessage.setText("Paste successfully done!");
        });
    }

}
