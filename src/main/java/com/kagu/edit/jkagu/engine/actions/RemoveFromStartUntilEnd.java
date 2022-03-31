package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveFromStartUntilEnd extends Command {

    private String start;
    private String end;
    private Label statusMessage;

    public RemoveFromStartUntilEnd(ObservableList<Row> observableList, String start, String end, Label statusMessage) {
        super(observableList);
        this.start = start;
        this.end = end;
        this.statusMessage = statusMessage;
    }


    @Override
    public boolean execute() {
        List<Row> removed = observableList.stream()
                .map(row -> {
                    int firstOccurrenceIndex = row.content().indexOf(start);
                    int lastOccurrenceIndex = row.content().lastIndexOf(end);

                    String str = row.content();
                    if(firstOccurrenceIndex != -1 && lastOccurrenceIndex != -1 )
                    {
                        str = str.substring(0, firstOccurrenceIndex) + str.substring(lastOccurrenceIndex + end.length() + 1);
                    }
                    return new Row(row.rowNumber(), str);
                }).collect(Collectors.toList());

        observableList.clear();
        observableList.addAll(removed);
        statusMessage.setText("Remove from start until end on every row executed!");

        return true;
    }
}
