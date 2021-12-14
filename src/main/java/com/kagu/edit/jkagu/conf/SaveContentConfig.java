package com.kagu.edit.jkagu.conf;

import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class SaveContentConfig implements ComponentConf {

    private ObservableList<String> observableList;
    private MenuItem saveFile;

    public SaveContentConfig(ObservableList<String> observableList, MenuItem saveFile)
    {
        this.observableList = observableList;
        this.saveFile = saveFile;
    }

    @Override
    public void configure() {
        this.saveFile.setOnAction((e) ->
        {
            try {
                File file = new File(System.getProperty("user.home") + "/out.txt");
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(observableList.stream().collect(Collectors.joining("\n")));
                myWriter.close();
            } catch (IOException err) {
                err.printStackTrace();
            }
        });
    }

}
