package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.Utils;
import com.kagu.edit.jkagu.conf.model.MuliLineSearchContext;
import com.kagu.edit.jkagu.conf.model.QuerySearchContext;
import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.conf.model.SingleLineSearchContext;
import com.kagu.edit.jkagu.engine.actions.FilterByFromUntilString;
import com.kagu.edit.jkagu.engine.actions.FilterByQuery;
import com.kagu.edit.jkagu.engine.actions.FilterByString;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class SearchContentConfig implements ComponentConf {

    public static final String USE_SELECTED_LINES = "useSelectedLines";
    public static final String USE_ADVANCED_SELECT = "useAdvancedSelect";
    public static final String USE_SELECTED_LINES_MULTILINE = "useSelectedLinesMultiline";
    private final RadioButton useSelectedLines;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Button searchButton;

    private final ObservableList<Row> observableList;
    private final List<Row> initialList;
    private final TextField searchField;
    private final ComboBox<String> searchComboBox;

    private final Label statusMessage;
    private final CheckBox caseSensitiveSearch;

    private final TextField searchFieldFrom;
    private final TextField searchFieldUntil;

    private final HBox searchFieldContainer;
    private final HBox searchFromUntilContainer;
    private final HBox searchComboBoxContainer;


    public SearchContentConfig(SingleLineSearchContext singleLineSearchContext,
                               QuerySearchContext querySearchContext,
                               MuliLineSearchContext muliLineSearchContext,
                               Button searchButton,
                               ObservableList<Row> observableList,
                               List<Row> initialList,
                               TextField searchField,
                               Label statusMessage,
                               CheckBox caseSensitiveSearch) {

        muliLineSearchContext.useSelectedLinesMultiline().setToggleGroup(toggleGroup);
        singleLineSearchContext.useSelectedLines().setToggleGroup(toggleGroup);
        querySearchContext.useAdvancedSelect().setToggleGroup(toggleGroup);

        this.searchButton = searchButton;
        this.observableList = observableList;
        this.initialList = initialList;
        this.searchField = searchField;
        this.statusMessage = statusMessage;

        this.useSelectedLines = singleLineSearchContext.useSelectedLines();

        this.caseSensitiveSearch = caseSensitiveSearch;

        this.searchFieldFrom = muliLineSearchContext.searchFieldFrom();
        this.searchFieldUntil = muliLineSearchContext.searchFieldUntil();
        this.searchComboBox = querySearchContext.searchComboBox();

        this.searchFieldContainer = singleLineSearchContext.searchFieldContainer();
        this.searchFromUntilContainer = muliLineSearchContext.searchFromUntilContainer();
        this.searchComboBoxContainer = querySearchContext.searchComboBoxContainer();
    }


    @Override
    public void configure() {

        searchFieldContainer.managedProperty().bind(searchFieldContainer.visibleProperty());
        searchFromUntilContainer.managedProperty().bind(searchFromUntilContainer.visibleProperty());
        searchComboBoxContainer.managedProperty().bind(searchComboBoxContainer.visibleProperty());

        searchComboBox.setEditable(true);
        searchComboBox.getItems().addAll(
                            "select row where row has 'A' 'B' 'C' -- row must have this 3 words",
                                "select row+2 where row has 'A' -- show row + 2 rows below that have 'A'",
                                "remove row where row has 'A' 'B' 'C' -- show row that does not have all 3 words",
                                "select row where row start 'A' -- trimmed row must start with A ",
                                "remove row where row end 'A' -- trimmed row must not end with A ",
                                "select row unique -- get the unique rows");


        toggleGroup.selectedToggleProperty()
                .addListener((observable, oldVal, newVal) -> {
                    // System.out.println(newVal + " was selected");

                    Toggle t = toggleGroup.getSelectedToggle();
                    RadioButton rb = (RadioButton) t;

                    String radioButtonID = rb.getId();
                    switch (radioButtonID) {
                        case USE_SELECTED_LINES -> setupSingleLineSelect();
                        case USE_ADVANCED_SELECT -> setupQuerySelect();
                        case USE_SELECTED_LINES_MULTILINE -> setupMultiLineSelect();
                        default -> throw new UnsupportedOperationException("Not supported logic for: " + radioButtonID);
                    }
                });

        this.searchButton.setOnAction(e -> {
            Toggle t = toggleGroup.getSelectedToggle();
            RadioButton rb = (RadioButton) t;

            String radioButtonID = rb.getId();
            switch (radioButtonID) {
                case USE_SELECTED_LINES -> singleLineSearch();
                case USE_ADVANCED_SELECT -> querySearch();
                case USE_SELECTED_LINES_MULTILINE -> multiLineSearch();
                default -> throw new UnsupportedOperationException("Not supported logic for: " + radioButtonID);
            }
            
        });

        toggleGroup.selectToggle(useSelectedLines);
    }

    private void setupQuerySelect() {

        this.searchComboBoxContainer.setVisible(true);
        this.searchFieldContainer.setVisible(false);
        this.searchFromUntilContainer.setVisible(false);

        this.caseSensitiveSearch.setDisable(true);
        this.caseSensitiveSearch.setSelected(true);
    }

    private void setupMultiLineSelect() {
        this.caseSensitiveSearch.setDisable(true);
        this.caseSensitiveSearch.setSelected(true);

        this.searchComboBoxContainer.setVisible(false);
        this.searchFieldContainer.setVisible(false);
        this.searchFromUntilContainer.setVisible(true);

        this.caseSensitiveSearch.setDisable(true);
        this.caseSensitiveSearch.setSelected(true);
    }

    private void setupSingleLineSelect() {
        this.caseSensitiveSearch.setDisable(false);

        this.searchComboBoxContainer.setVisible(false);
        this.searchFieldContainer.setVisible(true);
        this.searchFromUntilContainer.setVisible(false);
    }

    private void multiLineSearch() {
        String fromText = searchFieldFrom.getText();
        String untilText = searchFieldUntil.getText();
        if (Utils.isStringNOTEmpty(fromText) && Utils.isStringNOTEmpty(untilText))
        {
            FilterByFromUntilString filterByFromUntilString = new FilterByFromUntilString(this.observableList, this.initialList, fromText, untilText, this.statusMessage);
            filterByFromUntilString.execute();
        } else {
            this.statusMessage.setText("The search fields are empty. Search will not be performed!");
        }
    }

    private void querySearch() {
        //String query = searchField.getText();
        String query = searchComboBox.getEditor().getText();
        if (Utils.isStringNOTEmpty(query))
        {
            FilterByQuery filterByQuery = new FilterByQuery(this.observableList, query, this.statusMessage);
            filterByQuery.execute();
        } else {
            this.statusMessage.setText("The search field is empty. Search will not be performed!");
        }
    }

    private void singleLineSearch() {
        String text = searchField.getText();
        if (Utils.isStringNOTEmpty(text))
        {
            FilterByString filterByString = new FilterByString(this.observableList, this.initialList, text, this.statusMessage, this.caseSensitiveSearch);
            filterByString.execute();
        } else {
            this.statusMessage.setText("The search field is empty. Search will not be performed!");
        }
    }
}
