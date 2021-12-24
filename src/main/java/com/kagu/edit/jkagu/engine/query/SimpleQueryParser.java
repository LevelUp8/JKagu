package com.kagu.edit.jkagu.engine.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleQueryParser implements  QueryParser {

    private Integer additionalRowCounter = 0;
    private List<String> keyWords;
    private Integer selectedAdditionalRows;

    public SimpleQueryParser(String query)
    {
        this.keyWords = parseQuery(query);
        this.selectedAdditionalRows = selectPartParser(query);
    }

    public Optional<String> execute(String row)
    {
        if(additionalRowCounter > 0)
        {
            additionalRowCounter--;
            return Optional.of(row);
        }

        Optional<String> keyWordNotFoundOnRow = keyWords.stream().filter(w -> !row.contains(w)).findFirst();

        if(keyWordNotFoundOnRow.isEmpty())
        {
            if(additionalRowCounter < selectedAdditionalRows)
            {
                additionalRowCounter = selectedAdditionalRows;
            }
            return Optional.of(row);
        }

        return Optional.empty();
    }

    private List<String> parseQuery(String query) {
        int indexOfWhere = query.indexOf("where");
        if(indexOfWhere == -1)
        {
            throw new IllegalStateException("the query must have Where clause");
        }
        String selectQueryPart = query.substring(0, indexOfWhere);
        if(!selectQueryPart.startsWith("select"))
        {
            throw new IllegalStateException("the query must start with Select");
        }

        String whereQueryPart = query.substring(indexOfWhere + "where".length());


        int firstRowIndex = whereQueryPart.indexOf("row");
        int firstHasIndex = whereQueryPart.indexOf("has");

        if(firstRowIndex == -1 || firstHasIndex == -1)
        {
            throw new IllegalStateException("where clause must has: row has");
        }

        List<String> keyWords = new ArrayList<>();

        while(whereQueryPart.contains("'"))
        {
            int startOfQuotesIndex = whereQueryPart.indexOf("'");

            if(firstRowIndex > firstHasIndex)
            {
                throw new IllegalStateException("where clause must has: row has");
            }

            int endOfQuotesIndex = whereQueryPart.indexOf("'", startOfQuotesIndex+1);

            String searchKeyword = whereQueryPart.substring(startOfQuotesIndex+1, endOfQuotesIndex);
            keyWords.add(searchKeyword);

            whereQueryPart = whereQueryPart.substring(endOfQuotesIndex+1);
        }

        return keyWords;
    }

    private Integer selectPartParser(String query) {
        int indexOfWhere = query.indexOf("where");
        String selectQueryPart = query.substring(0, indexOfWhere);
        Integer selectedAdditionalRows = 0;
        String selected = selectQueryPart.substring("select".length());

        if(selected.indexOf("row") == -1)
        {
            throw new IllegalStateException("In the select clause must have row");
        }

        int plus = selected.indexOf("+");
        if(plus != -1)
        {
            String numberOfAdditionalRows = selected.substring(plus);
            selectedAdditionalRows = Integer.valueOf(numberOfAdditionalRows.strip());
        }

        return selectedAdditionalRows;
    }
}
