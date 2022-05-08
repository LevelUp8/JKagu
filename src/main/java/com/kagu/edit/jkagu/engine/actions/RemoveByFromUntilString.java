package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class RemoveByFromUntilString extends Command {

    private final String until;
    private final String from;
    private final Label statusMessage;
    private final List<Row> initialList;

    public RemoveByFromUntilString(ObservableList<Row> observableList, List<Row> initialList, String from, String until, Label statusMessage) {
        super(observableList);
        this.initialList = initialList;
        this.from = from;
        this.until = until;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        //observableList.clear();
        //observableList.addAll(initialList);

        List<Row> rows = new ArrayList<>();
        int counter = 0;
        for (Row row : observableList) {
            int until = row.content().indexOf(this.until);
            int startFrom = row.content().indexOf(this.from);
            if (startFrom != -1) {

                counter++;
                String contentFrom = row.content().substring(0, startFrom);
                int startUntilOnSameRow = row.content().indexOf(this.until);

                if (startUntilOnSameRow != -1 && startUntilOnSameRow > startFrom) {
                    contentFrom = contentFrom + row.content().substring(startUntilOnSameRow + this.until.length());
                    counter--;
                    rows.add(new Row(row.rowNumber(), contentFrom));
                }

            } else if (until != -1) {

                counter--;
                if(counter == 0)
                {
                    String contentUntil = row.content().substring(until + this.until.length());
                    rows.add(new Row(row.rowNumber(), contentUntil));
                }

            } else {
                if (counter == 0) {
                    rows.add(row);
                }
            }

        }

        observableList.clear();
        observableList.addAll(rows);
        statusMessage.setText("Performing remove from until");
        return true;
    }
}
