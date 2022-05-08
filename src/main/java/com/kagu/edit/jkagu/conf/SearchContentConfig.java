package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.Utils;
import com.kagu.edit.jkagu.conf.model.MuliLineSearchContext;
import com.kagu.edit.jkagu.conf.model.QuerySearchContext;
import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.conf.model.SingleLineSearchContext;
import com.kagu.edit.jkagu.engine.actions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class SearchContentConfig implements ComponentConf {

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

    private ChoiceBox<String> selectedStrategyBox;

    private static final String SELECT_LINE = "Select Line";
    private static final String ADVANCED_SELECT = "Advanced Select";
    private static final String SELECT_LINES = "Select Lines";
    private static final String REMOVE_LINES = "Remove Lines";

    private String currentSelected;

    // string array
    private static final String st[] = { SELECT_LINE,
            ADVANCED_SELECT,
            SELECT_LINES,
            REMOVE_LINES};


    public SearchContentConfig(ChoiceBox<String> selectedStrategyBox,
                               SingleLineSearchContext singleLineSearchContext,
                               QuerySearchContext querySearchContext,
                               MuliLineSearchContext muliLineSearchContext,
                               Button searchButton,
                               ObservableList<Row> observableList,
                               List<Row> initialList,
                               TextField searchField,
                               Label statusMessage,
                               CheckBox caseSensitiveSearch) {

        this.selectedStrategyBox = selectedStrategyBox;

        this.searchButton = searchButton;
        this.observableList = observableList;
        this.initialList = initialList;
        this.searchField = searchField;
        this.statusMessage = statusMessage;

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
                "select row where row has 'A' 'B' 'C' --row must have this 3 words",
                "select row+2 where row has 'AAA' --row + 2 rows below that have AAA",
                "remove row where row has 'A' 'B' 'C' --row that does not have all 3 words",
                "select row where row start 'AAA' --trimmed row must start with AAA",
                "remove row where row end 'AAA' --trimmed row must not end with AAA",
                "select row unique --get the unique rows");


        selectedStrategyBox.setItems(FXCollections.observableArrayList(st));

        selectedStrategyBox.setValue(st[0]);
        currentSelected = st[0];
        setupSingleLineSelect();
        // add a listener
        selectedStrategyBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) ->
        {
            // set the text for the label to the selected item
            //l1.setText(st[new_value.intValue()] + " selected");
            currentSelected = st[newValue.intValue()];

            switch (currentSelected) {
                case SELECT_LINE -> setupSingleLineSelect();
                case ADVANCED_SELECT -> setupQuerySelect();
                case SELECT_LINES, REMOVE_LINES -> setupMultiLine();
                default -> throw new UnsupportedOperationException("Not supported logic for: " + currentSelected);
            }
        });



        this.searchButton.setOnAction(e -> {
            switch (currentSelected) {
                case SELECT_LINE -> singleLineSearch();
                case ADVANCED_SELECT -> querySearch();
                case SELECT_LINES -> multiLineSearch();
                case REMOVE_LINES -> multilineRemove();
                default -> throw new UnsupportedOperationException("Not supported logic for: " + currentSelected);
            }
            
        });

        //toggleGroup.selectToggle(useSelectedLines);
    }

    private void setupQuerySelect() {

        this.searchComboBoxContainer.setVisible(true);
        this.searchFieldContainer.setVisible(false);
        this.searchFromUntilContainer.setVisible(false);

        this.caseSensitiveSearch.setDisable(true);
        this.caseSensitiveSearch.setSelected(true);
    }

    private void setupMultiLine() {
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

    private void multilineRemove() {
        String fromText = searchFieldFrom.getText();
        String untilText = searchFieldUntil.getText();
        if (Utils.isStringNOTEmpty(fromText) && Utils.isStringNOTEmpty(untilText))
        {
            RemoveByFromUntilString removeByFromUntilString = new RemoveByFromUntilString(this.observableList, this.initialList, fromText, untilText, this.statusMessage);
            removeByFromUntilString.execute();
        } else {
            this.statusMessage.setText("The remove fields are empty. Remove will not be performed!");
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
