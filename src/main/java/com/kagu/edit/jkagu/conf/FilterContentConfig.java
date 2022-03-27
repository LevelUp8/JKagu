package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.engine.actions.FilterByQuery;
import com.kagu.edit.jkagu.engine.actions.FilterByString;
import com.kagu.edit.jkagu.engine.actions.RestoreAllText;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.List;

public class FilterContentConfig implements ComponentConf {

    private final RadioButton useWholeFile;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Button searchButton;

    private final ObservableList<Row> observableList;
    private final List<Row> initialList;
    private final TextField searchField;

    private final Label statusMessage;
    private final CheckBox caseSensitiveSearch;


    public FilterContentConfig(RadioButton useWholeFile,
                               RadioButton useSelectedLines,
                               RadioButton useAdvancedSelect,
                               Button searchButton,
                               ObservableList<Row> observableList,
                               List<Row> initialList,
                               TextField searchField,
                               Label statusMessage,
                               CheckBox caseSensitiveSearch) {

        useWholeFile.setToggleGroup(toggleGroup);
        useSelectedLines.setToggleGroup(toggleGroup);
        useAdvancedSelect.setToggleGroup(toggleGroup);
        this.searchButton = searchButton;
        this.observableList = observableList;
        this.initialList = initialList;
        this.searchField = searchField;
        this.statusMessage = statusMessage;
        this.useWholeFile = useWholeFile;
        this.caseSensitiveSearch = caseSensitiveSearch;
    }


    @Override
    public void configure() {
        toggleGroup.selectedToggleProperty()
                .addListener((observable, oldVal, newVal) -> {
                    System.out.println(newVal + " was selected");

                    Toggle t = toggleGroup.getSelectedToggle();
                    RadioButton rb = (RadioButton) t;
                    if ("useSelectedLines".equals(rb.getId())) {
                        this.caseSensitiveSearch.setDisable(false);
                        this.searchField.setVisible(true);
                        this.searchButton.setVisible(true);
                    } else if ("useWholeFile".equals(rb.getId())) {
                        this.caseSensitiveSearch.setDisable(true);
                        this.caseSensitiveSearch.setSelected(true);
                        this.searchField.clear();
                        this.searchField.setVisible(false);
                        this.searchButton.setVisible(false);
                        RestoreAllText restoreAllText = new RestoreAllText(this.observableList, this.initialList, this.statusMessage);
                        restoreAllText.execute();
                    } else if ("useAdvancedSelect".equals(rb.getId())) {
                        this.caseSensitiveSearch.setDisable(true);
                        this.caseSensitiveSearch.setSelected(true);
                        this.searchField.setVisible(true);
                        this.searchButton.setVisible(true);
                    } else {
                        throw new UnsupportedOperationException("Not supported logic for: " + rb.getId());
                    }
                });

        this.searchButton.setOnAction(e -> {
            Toggle t = toggleGroup.getSelectedToggle();
            RadioButton rb = (RadioButton) t;
            if ("useSelectedLines".equals(rb.getId())) {
                String text = searchField.getText();
                if (text != null && !text.trim().isEmpty()) {
                    FilterByString filterByString = new FilterByString(this.observableList, this.initialList, text, this.statusMessage, this.caseSensitiveSearch);
                    filterByString.execute();
                } else {
                    this.statusMessage.setText("The search field is empty. Search will not be performed!");
                }

            } else if ("useAdvancedSelect".equals(rb.getId())) {
                String query = searchField.getText();
                if (query != null && !query.trim().isEmpty()) {
                    FilterByQuery filterByQuery = new FilterByQuery(this.observableList, this.initialList, query, this.statusMessage);
                    filterByQuery.execute();
                } else {
                    this.statusMessage.setText("The search field is empty. Search will not be performed!");
                }
            } else if ("useWholeFile".equals(rb.getId())) {
                RestoreAllText restoreAllText = new RestoreAllText(this.observableList, this.initialList, this.statusMessage);
                restoreAllText.execute();
            } else {
                throw new UnsupportedOperationException("Not supported logic for: " + rb.getId());
            }
        });


        toggleGroup.selectToggle(useWholeFile);

    }
}
