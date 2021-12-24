package com.kagu.edit.jkagu.engine.query;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleQueryParser implements QueryParser {

    public static final String WHERE = "where";
    public static final String SELECT = "select";
    private Integer additionalRowCounter = 0;
    private List<String> keyWords;
    private Integer selectedAdditionalRows;
    private Label statusMessage;

    public SimpleQueryParser(String query, Label statusMessage) {
        this.keyWords = parseQuery(query);
        this.selectedAdditionalRows = selectPartParser(query);
        this.statusMessage = statusMessage;
    }

    public Optional<String> execute(String row) {
        if (additionalRowCounter > 0) {
            additionalRowCounter--;
            return Optional.of(row);
        }

        Optional<String> keyWordNotFoundOnRow = keyWords.stream().filter(w -> !row.contains(w)).findFirst();

        if (keyWordNotFoundOnRow.isEmpty()) {
            if (additionalRowCounter < selectedAdditionalRows) {
                additionalRowCounter = selectedAdditionalRows;
            }
            return Optional.of(row);
        }

        return Optional.empty();
    }

    private List<String> parseQuery(String query) {
        int indexOfWhere = query.indexOf(WHERE);
        if (indexOfWhere == -1) {
            throw new IllegalStateException("the query must have Where clause");
        }
        String selectQueryPart = query.substring(0, indexOfWhere);
        if (!selectQueryPart.startsWith(SELECT)) {
            this.statusMessage.setText("the query must start with select");
            throw new IllegalStateException("the query must start with select");
        }

        String whereQueryPart = query.substring(indexOfWhere + WHERE.length());


        int firstRowIndex = whereQueryPart.indexOf("row");
        int firstHasIndex = whereQueryPart.indexOf("has");

        if (firstRowIndex == -1 || firstHasIndex == -1) {
            this.statusMessage.setText("where clause must has: row has");
            throw new IllegalStateException("where clause must has: row has");
        }

        List<String> keyWords = new ArrayList<>();

        while (whereQueryPart.contains("'")) {
            int startOfQuotesIndex = whereQueryPart.indexOf("'");

            if (firstRowIndex > firstHasIndex) {
                this.statusMessage.setText("where clause must has: row has");
                throw new IllegalStateException("where clause must has: row has");
            }

            int endOfQuotesIndex = whereQueryPart.indexOf("'", startOfQuotesIndex + 1);

            String searchKeyword = whereQueryPart.substring(startOfQuotesIndex + 1, endOfQuotesIndex);
            keyWords.add(searchKeyword);

            whereQueryPart = whereQueryPart.substring(endOfQuotesIndex + 1);
        }

        return keyWords;
    }

    private Integer selectPartParser(String query) {
        int indexOfWhere = query.indexOf(WHERE);
        String selectQueryPart = query.substring(0, indexOfWhere);
        Integer selectedAdditionalRows = 0;
        String selected = selectQueryPart.substring(SELECT.length());

        if (selected.indexOf("row") == -1) {
            this.statusMessage.setText("In the select clause must have row");
            throw new IllegalStateException("In the select clause must have row");
        }

        int plus = selected.indexOf("+");
        if (plus != -1) {
            String numberOfAdditionalRows = selected.substring(plus);
            selectedAdditionalRows = Integer.valueOf(numberOfAdditionalRows.strip());
        }

        return selectedAdditionalRows;
    }
}
