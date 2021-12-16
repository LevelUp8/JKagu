package com.kagu.edit.jkagu.engine.actions;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterByFromUntilString extends Command {

    private final String until;
    private final String from;
    private final Label statusMessage;

    public FilterByFromUntilString(ObservableList<String> observableList, String from, String until, Label statusMessage) {
        super(observableList);
        this.from = from;
        this.until = until;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
        List<String> rows = new ArrayList<>();

        int counter = 0;
        for(String row : observableList)
        {
            int startUntil = row.indexOf(until);
            int startFrom = row.indexOf(from);
            if(startFrom != -1)
            {
                counter++;
                if(counter == 1)
                {
                    String contentFrom = row.substring(startFrom);
                    int startUntilOnSameRow = contentFrom.indexOf(until);

                    if(startUntilOnSameRow != -1)
                    {
                        contentFrom = contentFrom.substring(0, startUntilOnSameRow + until.length());
                        counter--;
                    }
                    rows.add(contentFrom);
                }
                else
                {
                    if(counter > 0)
                    {
                        rows.add(row);
                    }
                }
            }
            else if(startUntil != -1)
            {
                counter--;

                if(counter > 0)
                {
                    rows.add(row);
                }

                if(counter == 0)
                {
                   String endContentUntil = row.substring(0, startUntil + until.length());
                   rows.add(endContentUntil);
                }
            }
            else
            {
                if (counter > 0)
                {
                    rows.add(row);
                }
            }

        }

        observableList.clear();
        observableList.addAll(rows);
        statusMessage.setText("Performing search from until");
        return true;
    }
}
