package com.kagu.edit.jkagu.engine.actions;


import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.stream.Collectors;

public class EditOneRow extends Command {

    private final Row selectedRow;
    private final TextArea editField;

    public EditOneRow(ObservableList<Row> observableList, Row selectedRow, TextArea editField) {
        super(observableList);
        this.selectedRow = selectedRow;
        this.editField = editField;
    }

    @Override
    public boolean execute() {

        Long rowNumber =  selectedRow.rowNumber();
        List<Row> replaced = observableList.stream()
                .map(row -> {
                    if(rowNumber.equals(row.rowNumber()))
                    {
                        String forReplacement =  editField.getText();
                        return new Row(row.rowNumber(), forReplacement);
                    }
                    return new Row(row.rowNumber(), row.content());
                }).collect(Collectors.toList());

        observableList.clear();
        observableList.addAll(replaced);

        return true;
    }
}
