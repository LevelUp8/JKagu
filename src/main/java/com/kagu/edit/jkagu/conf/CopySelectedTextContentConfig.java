package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CopySelectedTextContentConfig implements ComponentConf {

    private final ListView<Row> listView;
    private MenuItem copySelectedText;
    private Label statusMessage;

    public CopySelectedTextContentConfig(MenuItem copySelectedText, Label statusMessage, ListView<Row> listView) {
        this.copySelectedText = copySelectedText;
        this.statusMessage = statusMessage;
        this.listView = listView;
    }

    public Label getStatusMessage() {
        return statusMessage;
    }

    @Override
    public void configure() {
        copySelectedText.setOnAction(e -> {

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            Row selectedRow = listView.getSelectionModel().getSelectedItem();
            String text = selectedRow.content();
            content.putString(text);
            clipboard.setContent(content);

            this.statusMessage.setText("Selected Content copied!");

        });
    }
}
