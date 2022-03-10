package com.kagu.edit.jkagu;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class RowListViewCell extends ListCell<Row> {

    @FXML
    private Label number;

    @FXML
    private Label contentRow;

    private FXMLLoader rowLoader;

    @FXML
    private HBox rowContainer;

    @Override
    protected void updateItem(Row row, boolean empty) {
        super.updateItem(row, empty);

        if (empty || row == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (rowLoader == null) {
                rowLoader = new FXMLLoader(RowListViewCell.class.getResource("row-cell.fxml"));
                rowLoader.setController(this);

                try {
                    rowLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            number.setText(String.valueOf(row.rowNumber()));
            contentRow.setText(row.content());
            contentRow.setWrapText(true);

            setText(null);
            setGraphic(rowContainer);
        }

    }

}
