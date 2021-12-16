package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.engine.actions.FilterByString;
import com.kagu.edit.jkagu.engine.actions.RestoreAllText;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.List;

public class FilterContentConfig implements ComponentConf {

    private final RadioButton useWholeFile;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Button searchButton;

    private ObservableList<String> observableList;
    private List<String> initialList;
    private TextField searchField;

    private Label statusMessage;


    public FilterContentConfig(RadioButton useWholeFile,
                               RadioButton useSelectedLines,
                               Button searchButton,
                               ObservableList<String> observableList,
                               List<String> initialList,
                               TextField searchField,
                               Label statusMessage) {

        useWholeFile.setToggleGroup(toggleGroup);
        useSelectedLines.setToggleGroup(toggleGroup);
        this.searchButton = searchButton;
        this.observableList = observableList;
        this.initialList = initialList;
        this.searchField = searchField;
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
                    if("useSelectedLines".equals(rb.getId()))
                    {
                        this.searchField.setVisible(true);
                        this.searchButton.setVisible(true);
                    }
                    else if("useWholeFile".equals(rb.getId()))
                    {
                        this.searchField.clear();
                        this.searchField.setVisible(false);
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
            if("useSelectedLines".equals(rb.getId()))
            {
                String text = searchField.getText();
                if(text != null && !text.trim().isEmpty())
                {
                    FilterByString filterByString = new FilterByString(this.observableList, text, this.statusMessage);
                    filterByString.execute();
                }
                else
                {
                    this.statusMessage.setText("The search field is empty. Search will not be performed!");
                }

            }
            else if("useWholeFile".equals(rb.getId()))
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
