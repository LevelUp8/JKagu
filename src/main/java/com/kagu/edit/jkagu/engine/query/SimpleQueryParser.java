package com.kagu.edit.jkagu.engine.query;

import javafx.scene.control.Label;

import java.util.*;
import java.util.function.Predicate;

public class SimpleQueryParser implements QueryParser {

    public static final String UNIQUE = "unique";
    public static final String WHERE = "where";
    public static final String SELECT = "select";
    public static final String REMOVE = "remove";
    public static final String HAS = "has";
    public static final String START = "start";
    public static final String END = "end";
    public static final String ROW = "row";
    private final boolean unique;
    private Integer additionalRowCounter = 0;
    private List<String> keyWords = Collections.emptyList();
    private final Integer selectedAdditionalRows;
    private final Label statusMessage;
    private final String originalQuery;
    private final Set<String> uniqueSetRow = new LinkedHashSet<>();
    private String keywordType;

    public SimpleQueryParser(String query, Label statusMessage) {
        this.statusMessage = statusMessage;
        originalQuery = query;

        this.unique = parseUnique(query);
        if(!unique)
        {
            this.keyWords = parseQuery(query);
        }

        this.selectedAdditionalRows = selectPartParser(query);
    }

    public Optional<String> execute(String row) {
        if(originalQuery.startsWith(SELECT))
        {
            return selectFiltering(row);
        }
        else if(originalQuery.startsWith(REMOVE))
        {
            return removeFiltering(row);
        }
        else
        {
            throw new UnsupportedOperationException("query: " + originalQuery);
        }
    }

    private Optional<String> selectFiltering(String row) {
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
            if(keywordType == null)
            {
                return Optional.empty();
            }

            return switch (keywordType) {
                case HAS -> getOptionalRow(row, (w -> !row.contains(w)));
                case START -> getOptionalRow(row, (w -> !row.strip().startsWith(w)));
                case END -> getOptionalRow(row, (w -> !row.strip().endsWith(w)));
                default -> throw new UnsupportedOperationException("Not supported operation for keywordType: " + keywordType);
            };
        }
    }

    private Optional<String> removeFiltering(String row) {

        if(keywordType == null)
        {
            this.statusMessage.setText("ERROR! There are no has, start, end in the query");
            return Optional.empty();
        }

        return switch (keywordType) {
            case HAS -> getOptionalRow(row, (w -> row.contains(w)));
            case START -> getOptionalRow(row, (w -> row.strip().startsWith(w)));
            case END -> getOptionalRow(row, (w -> row.strip().endsWith(w)));
            default -> throw new UnsupportedOperationException("Not supported operation for keywordType: " + keywordType);
        };
    }

    private Optional<String> getOptionalRow(String row, Predicate<String> filterPredicate) {

        if(keyWords.isEmpty())
        {
            this.statusMessage.setText("ERROR! There are no keywords to search with");
            return Optional.empty();
        }

        Optional<String> filteredKeyWordRow = keyWords.stream().filter(filterPredicate).findFirst();

        if (filteredKeyWordRow.isEmpty()) {
            additionalRowCounter = keywordIsFoundResetCounter(additionalRowCounter, selectedAdditionalRows);
            return Optional.of(row);
        }
        else
        {
            if (additionalRowCounter > 0) {
                additionalRowCounter--;
                return Optional.of(row);
            }

            return Optional.empty();
        }
    }

    private Integer keywordIsFoundResetCounter(Integer additionalRowCounter, Integer selectedAdditionalRows) {
        if (additionalRowCounter < selectedAdditionalRows) {
            return selectedAdditionalRows;
        }
        return additionalRowCounter;
    }

    private boolean parseUnique(String query)
    {
        int indexOfUnique = query.indexOf(UNIQUE);
        int indexOfWhere = query.indexOf(WHERE);
        return indexOfUnique != -1 && indexOfWhere == -1;
    }


    private List<String> parseQuery(String query) {
        int indexOfWhere = query.indexOf(WHERE);
        if(validateParseQuery(query, indexOfWhere))
        {
            return Collections.emptyList();
        }

        String whereQueryPart = query.substring(indexOfWhere + WHERE.length());

        int firstRowIndex = whereQueryPart.indexOf(ROW);
        int firstHasIndex = whereQueryPart.indexOf(HAS);
        int firstStartIndex = whereQueryPart.indexOf(START);
        int firstEndIndex = whereQueryPart.indexOf(END);

        int startOfQuotesIndex = whereQueryPart.indexOf("'");

        if(firstRowIndex == -1 || startOfQuotesIndex < firstRowIndex)
        {
            this.statusMessage.setText("ERROR! Where clause must has row keyword");
            return Collections.emptyList();
        }

        Optional<String> hasKeyword = checkKeyword(firstRowIndex, firstHasIndex, startOfQuotesIndex, HAS);
        Optional<String> startKeyword = checkKeyword(firstRowIndex, firstStartIndex, startOfQuotesIndex, START);
        Optional<String> endKeyword = checkKeyword(firstRowIndex, firstEndIndex, startOfQuotesIndex, END);

        List<Optional<String>> keywordOptionals = List.of(hasKeyword, startKeyword, endKeyword);
        List<String> result = new ArrayList<>();
        keywordOptionals.stream()
                .filter(k -> k.isPresent())
                .findFirst()
                .ifPresentOrElse(k -> {
                            keywordType = k.get();
                            result.addAll(getKeyword(whereQueryPart, startOfQuotesIndex, keywordType));
                        },
                        () -> this.statusMessage.setText("ERROR! Where clause must has: row has, row start, row end"));

        return result;
    }

    private boolean validateParseQuery(String query, int indexOfWhere) {
        boolean error = false;
        int indexOfUnique = query.indexOf(UNIQUE);
        if (indexOfUnique != -1 && indexOfWhere == -1) {
            error = true;
        }

        if (indexOfWhere == -1) {
            this.statusMessage.setText("ERROR! The query must have where clause");
            error = true;
        }
        String selectQueryPart = query.substring(0, indexOfWhere);
        if (!selectQueryPart.startsWith(SELECT) && !selectQueryPart.startsWith(REMOVE)) {
            this.statusMessage.setText("ERROR! The query must start with select or remove");
            error = true;
        }
        return error;
    }


    private Optional<String> checkKeyword(int firstRowIndex, int firstKeywordIndex, int startOfQuotesIndex, String keyword)
    {
        if(firstKeywordIndex > firstRowIndex && firstKeywordIndex != -1 && startOfQuotesIndex > firstKeywordIndex)
        {
            return Optional.of(keyword);
        }

        return Optional.empty();
    }


    private List<String> getKeyword(String whereQueryPartArg, int startOfQuotesIndexArg, String foundKeywordTypeArg) {
        List<String> keyWords = new ArrayList<>();

        String whereQueryPart = whereQueryPartArg;
        int startOfQuotesIndex = startOfQuotesIndexArg;
        while (whereQueryPart.contains("'")) {

            int endOfQuotesIndex = whereQueryPart.indexOf("'", startOfQuotesIndex + 1);

            String searchKeyword = whereQueryPart.substring(startOfQuotesIndex + 1, endOfQuotesIndex);
            keyWords.add(searchKeyword);

            whereQueryPart = whereQueryPart.substring(endOfQuotesIndex + 1);
            startOfQuotesIndex = whereQueryPart.indexOf("'");
        }

        if(keyWords.size() > 1 && (foundKeywordTypeArg.equals(START) || foundKeywordTypeArg.equals(END)))
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

        int selectedAdditionalRows = 0;

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
            this.statusMessage.setText("ERROR! Query must start with select or remove");
            throw new IllegalStateException("Query must start with select of remove");
        }


        if (selected.indexOf(ROW) == -1) {
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