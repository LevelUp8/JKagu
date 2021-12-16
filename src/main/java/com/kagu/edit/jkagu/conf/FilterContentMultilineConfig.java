package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.FilterByFromUntilString;
import com.kagu.edit.jkagu.engine.actions.FilterByString;
import com.kagu.edit.jkagu.engine.actions.RestoreAllText;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.List;

public class FilterContentMultilineConfig implements ComponentConf {

    private final RadioButton useWholeFile;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Button searchButton;

    private ObservableList<String> observableList;
    private List<String> initialList;
    private TextField searchFieldFrom;
    private TextField searchFieldUntil;
    private Label statusMessage;


    public FilterContentMultilineConfig(RadioButton useWholeFile,
                                        RadioButton useSelectedLines,
                                        Button searchButton,
                                        ObservableList<String> observableList,
                                        List<String> initialList,
                                        TextField searchFieldFrom,
                                        TextField searchFieldUntil,
                                        Label statusMessage) {

        useWholeFile.setToggleGroup(toggleGroup);
        useSelectedLines.setToggleGroup(toggleGroup);
        this.searchButton = searchButton;
        this.observableList = observableList;
        this.initialList = initialList;
        this.searchFieldFrom = searchFieldFrom;
        this.searchFieldUntil = searchFieldUntil;
        this.statusMessage = statusMessage;
        this.useWholeFile = useWholeFile;
    }


    @Override
    public void configure() {
        toggleGroup.selectedToggleProperty()
                .addListener((observable, oldVal, newVal) -> {
                    System.out.println(newVal + " was selected");

                    Toggle t =  toggleGroup.getSelectedToggle();
                    RadioButton rb = (RadioButton) t;
                    if("useSelectedLinesMultiline".equals(rb.getId()))
                    {
                        this.searchFieldFrom.setVisible(true);
                        this.searchFieldUntil.setVisible(true);
                        this.searchButton.setVisible(true);
                    }
                    else if("useWholeFileMultiline".equals(rb.getId()))
                    {
                        this.searchFieldFrom.clear();
                        this.searchFieldFrom.setVisible(false);
                        this.searchFieldUntil.clear();
                        this.searchFieldUntil.setVisible(false);
                        this.searchButton.setVisible(false);
                    }
                    else
                    {
                        throw new UnsupportedOperationException("Not supported logic for: " + rb.getId());
                    }
                });

        this.searchButton.setOnAction(e -> {
            Toggle t =  toggleGroup.getSelectedToggle();
            RadioButton rb = (RadioButton) t;
            if("useSelectedLinesMultiline".equals(rb.getId()))
            {
                String fromText = searchFieldFrom.getText();
                String untilText = searchFieldUntil.getText();
                if(fromText != null && !fromText.trim().isEmpty()
                && untilText != null && !untilText.trim().isEmpty())
                {
                    FilterByFromUntilString filterByFromUntilString = new FilterByFromUntilString(this.observableList, fromText, untilText, this.statusMessage);
                    filterByFromUntilString.execute();
                }
                else
                {
                    this.statusMessage.setText("The search field is empty. Search will not be performed!");
                }

            }
            else if("useWholeFileMultiline".equals(rb.getId()))
            {
                RestoreAllText restoreAllText = new RestoreAllText(this.observableList, this.initialList, this.statusMessage);
                restoreAllText.execute();
            }
            else
            {
                throw new UnsupportedOperationException("Not supported logic for: " + rb.getId());
            }
        });


        toggleGroup.selectToggle(useWholeFile);

    }
}
