package com.kagu.edit.jkagu.engine.query;

import com.kagu.edit.jkagu.Utils;
import javafx.scene.control.Label;

import java.util.*;

public class SimpleQueryParser implements QueryParser {

    public static final String UNIQUE = "unique";
    public static final String WHERE = "where";
    public static final String SELECT = "select";
    public static final String REMOVE = "remove";
    private final boolean unique;
    private Integer additionalRowCounter = 0;
    private final List<String> keyWords;
    private final Integer selectedAdditionalRows;
    private final Label statusMessage;
    private final String originalQuery;
    private final Set<String> uniqueSetRow = new LinkedHashSet<>();
    private String keywordType;

    public SimpleQueryParser(String query, Label statusMessage) {
        this.keyWords = parseQuery(query);
        this.unique = parseUnique(query);
        this.selectedAdditionalRows = selectPartParser(query);
        this.statusMessage = statusMessage;
        originalQuery = query;
    }

    public Optional<String> execute(String row) {
        if (additionalRowCounter > 0) {
            additionalRowCounter--;
            return Optional.of(row);
        }

        if(originalQuery.startsWith(SELECT))
        {

            if(unique)
            {
                if(uniqueSetRow.add(row))
                {
                    return Optional.of(row);
                }
                else
                {
                    return Optional.empty();
                }
            }
            else
            {
                if(keywordType.equals("has"))
                {
                    Optional<String> keyWordNotFoundOnRow = keyWords.stream().filter(w -> !row.contains(w)).findFirst();

                    if (keyWordNotFoundOnRow.isEmpty()) {
                        if (additionalRowCounter < selectedAdditionalRows) {
                            additionalRowCounter = selectedAdditionalRows;
                        }
                        return Optional.of(row);
                    }
                }
                else if(keywordType.equals("start"))
                {
                    Optional<String> keyWordNotFoundOnRow = keyWords.stream().filter(w -> !row.startsWith(w)).findFirst();

                    if (keyWordNotFoundOnRow.isEmpty()) {
                        if (additionalRowCounter < selectedAdditionalRows) {
                            additionalRowCounter = selectedAdditionalRows;
                        }
                        return Optional.of(row);
                    }
                }
                else if(keywordType.equals("end"))
                {
                    Optional<String> keyWordNotFoundOnRow = keyWords.stream().filter(w -> !row.endsWith(w)).findFirst();

                    if (keyWordNotFoundOnRow.isEmpty()) {
                        if (additionalRowCounter < selectedAdditionalRows) {
                            additionalRowCounter = selectedAdditionalRows;
                        }
                        return Optional.of(row);
                    }
                }
                else
                {
                    throw new UnsupportedOperationException("Not supported operation for keywordType: " + keywordType);
                }

            }
        }
        else if(originalQuery.startsWith(REMOVE))
        {
            if(keywordType.equals("has"))
            {
                Optional<String> keyWordFoundOnRow = keyWords.stream().filter(w -> row.contains(w)).findFirst();

                if (keyWordFoundOnRow.isEmpty()) {
                    if (additionalRowCounter < selectedAdditionalRows) {
                        additionalRowCounter = selectedAdditionalRows;
                    }
                    return Optional.of(row);
                }
            }
            else if(keywordType.equals("start"))
            {
                Optional<String> keyWordFoundOnRow = keyWords.stream().filter(w -> row.startsWith(w)).findFirst();

                if (keyWordFoundOnRow.isEmpty()) {
                    if (additionalRowCounter < selectedAdditionalRows) {
                        additionalRowCounter = selectedAdditionalRows;
                    }
                    return Optional.of(row);
                }
            }
            else if(keywordType.equals("end"))
            {
                Optional<String> keyWordFoundOnRow = keyWords.stream().filter(w -> row.endsWith(w)).findFirst();

                if (keyWordFoundOnRow.isEmpty()) {
                    if (additionalRowCounter < selectedAdditionalRows) {
                        additionalRowCounter = selectedAdditionalRows;
                    }
                    return Optional.of(row);
                }
            }
            else
            {
                throw new UnsupportedOperationException("Not supported operation for keywordType: " + keywordType);
            }

        }
        else
        {
            throw new UnsupportedOperationException("query: " + originalQuery);
        }


        return Optional.empty();
    }

    private boolean parseUnique(String query)
    {
        int indexOfUnique = query.indexOf(UNIQUE);
        int indexOfWhere = query.indexOf(WHERE);
        if (indexOfUnique != -1 && indexOfWhere == -1) {
            return true;
        }
        else
        {
            return false;
        }
    }

    private List<String> parseQuery(String query) {
        int indexOfUnique = query.indexOf(UNIQUE);
        int indexOfWhere = query.indexOf(WHERE);
        if (indexOfUnique != -1 && indexOfWhere == -1) {
            return Collections.emptyList();
        }

        if (indexOfWhere == -1) {
            this.statusMessage.setText("ERROR! The query must have where clause");
            return Collections.emptyList();
        }
        String selectQueryPart = query.substring(0, indexOfWhere);
        if (!selectQueryPart.startsWith(SELECT) && !selectQueryPart.startsWith(REMOVE)) {
            this.statusMessage.setText("ERROR! The query must start with select or remove");
            return Collections.emptyList();
        }

        String whereQueryPart = query.substring(indexOfWhere + WHERE.length());


        int firstRowIndex = whereQueryPart.indexOf("row");
        int firstHasIndex = whereQueryPart.indexOf("has");
        int firstStartIndex = whereQueryPart.indexOf("start");
        int firstEndIndex = whereQueryPart.indexOf("end");

        int startOfQuotesIndex = whereQueryPart.indexOf("'");

        if(firstRowIndex == -1 || startOfQuotesIndex < firstRowIndex)
        {
            this.statusMessage.setText("ERROR! Where clause must has row keyword");
            return Collections.emptyList();
        }

        if(firstHasIndex > firstRowIndex && firstHasIndex != -1 && startOfQuotesIndex > firstHasIndex)
        {
            keywordType = "has";
        }
        else if(firstStartIndex > firstRowIndex && firstStartIndex != -1 && startOfQuotesIndex > firstStartIndex)
        {
            keywordType = "start";
        }
        else if(firstEndIndex > firstRowIndex && firstEndIndex != -1 && startOfQuotesIndex > firstEndIndex)
        {
            keywordType = "end";
        }
        else
        {
            this.statusMessage.setText("ERROR! Where clause must has: row has, row start, row end");
            return Collections.emptyList();
        }

        List<String> keyWords = new ArrayList<>();

        while (whereQueryPart.contains("'")) {

            int endOfQuotesIndex = whereQueryPart.indexOf("'", startOfQuotesIndex + 1);

            String searchKeyword = whereQueryPart.substring(startOfQuotesIndex + 1, endOfQuotesIndex);
            keyWords.add(searchKeyword);

            whereQueryPart = whereQueryPart.substring(endOfQuotesIndex + 1);
        }

        if(keyWords.size() > 1 && (keywordType.equals("start") || keywordType.equals("end")))
        {
            this.statusMessage.setText("ERROR! Where clause must have only one search word when row start or row end command");
            return Collections.emptyList();
        }

        return keyWords;
    }

    private Integer selectPartParser(String query) {
        int indexOfWhere = query.indexOf(WHERE);
        int indexOfUnique = query.indexOf(UNIQUE);

        String selectQueryPart;
        if(indexOfWhere == -1)
        {
            selectQueryPart = query.substring(0, indexOfUnique);
        }
        else
        {
            selectQueryPart = query.substring(0, indexOfWhere);
        }

        Integer selectedAdditionalRows = 0;

        String selected;
        if(selectQueryPart.startsWith(SELECT))
        {
            selected = selectQueryPart.substring(SELECT.length());
        }
        else if(selectQueryPart.startsWith(REMOVE))
        {
            selected = selectQueryPart.substring(REMOVE.length());
        }
        else
        {
            this.statusMessage.setText("Query must start with select of remove");
            throw new IllegalStateException("Query must start with select of remove");
        }


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