package com.kagu.edit.jkagu.conf;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public record SaveContentConfig(ObservableList<String> observableList,
                                MenuItem saveFile,
                                Label statusMessage) implements ComponentConf {

    @Override
    public void configure() {
        this.saveFile.setOnAction((e) ->
        {
            FileChooser fileChooser = new FileChooser();
            //only allow text files to be selected using chooser
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
            );
            //set initial directory somewhere user will recognise
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            //let user select file
            File file = fileChooser.showSaveDialog(null);

            String text = observableList.stream().collect(Collectors.joining("\n"));
            if (file != null) {
                saveTextToFile(text, file);
            } else {
                statusMessage.setText("Text is empty! There is nothing for saving");
            }
        });
    }

    private void saveTextToFile(String content, File file) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(content);
            myWriter.close();
            statusMessage.setText("Text successfully saved!");
        } catch (IOException err) {
            err.printStackTrace();
            statusMessage.setText("Internal error while saving");
        }
    }


}
