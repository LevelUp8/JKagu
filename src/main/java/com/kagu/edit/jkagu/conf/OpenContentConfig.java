package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public record OpenContentConfig(ProgressBar progressBar, Label statusMessage,
                                MenuItem openFile,
                                ListView<Row> listView,
                                ObservableList<Row> observableList,
                                List<Row> initialList) implements ComponentConf {

    @Override
    public void configure() {
        openFile.setOnAction((e) ->
        {
            FileChooser fileChooser = new FileChooser();
            //only allow text files to be selected using chooser
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
            );
            //set initial directory somewhere user will recognise
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            //let user select file
            File fileToLoad = fileChooser.showOpenDialog(null);
            //if file has been chosen, load it using asynchronous method (define later)
            if (fileToLoad != null) {
                loadFile(fileToLoad);
            }
        });
    }

    private void loadFile(File fileToLoad) {
        Task<Boolean> loadTask = fileLoaderTask(fileToLoad);
        progressBar.progressProperty().bind(loadTask.progressProperty());
        loadTask.run();
    }

    private Task<Boolean> fileLoaderTask(File fileToLoad) {
        //Create a task to load the file asynchronously
        Task<Boolean> loadFileTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));
                //Use Files.lines() to calculate total lines - used for progress
                long lineCount;
                try (Stream<String> stream = Files.lines(fileToLoad.toPath())) {
                    lineCount = stream.count();
                }
                //Load in all lines one by one into a StringBuilder separated by "\n" - compatible with TextArea
                String line;
                //StringBuilder totalFile = new StringBuilder();
                long linesLoaded = 0;
                long rowNumber = 0;
                while ((line = reader.readLine()) != null) {
                    rowNumber++;
                    //totalFile.append(line);
                    //totalFile.append("\n");
                    Row row = new Row(rowNumber, line);
                    initialList.add(row);
                    observableList.add(row);
                    updateProgress(++linesLoaded, lineCount);
                }
                return Boolean.TRUE;
            }
        };
        //If successful, update the text area, display a success message and store the loaded file reference
        loadFileTask.setOnSucceeded(workerStateEvent -> {
            try {
                if (loadFileTask.get()) {
                    listView.setItems(observableList);
                }

                statusMessage.setText("File loaded: " + fileToLoad.getName());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                statusMessage.setText("Error!! ");
                // listView.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
            }
        });
        //If unsuccessful, set text area with error message and status message to failed
        loadFileTask.setOnFailed(workerStateEvent -> {
            //textArea.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
            statusMessage.setText("Failed to load file");
        });
        return loadFileTask;
    }
}
