package com.kagu.edit.jkagu.engine.query;

import com.kagu.edit.jkagu.engine.query.components.SelectParser;
import com.kagu.edit.jkagu.engine.query.components.select.SelectOperation;
import com.kagu.edit.jkagu.engine.query.components.where.Operation;

import java.util.ArrayList;
import java.util.List;

public class SearchHandler {
    public static void fix(Button go, TextArea textArea, TextField textField, Stage stage, List<String> rows)
    {
        go.setOnAction((e) ->
        {
            String queryString = textField.getText();

            if(queryString != null && !queryString.isEmpty())
            {
                List<String> result = new ArrayList<>();

                Operation operation = operationCreation(queryString);
                SelectOperation selectOperation = selectOperationCreation(queryString);

                for(String row : rows)
                {
                    if(searchEngineOne(row, operation))
                    {
                        String showPart = processRow(row, selectOperation);
                        result.add(showPart);
                    }
                }

                OpenFilesHandler.fillTextArea(textArea, result);
            }
            else
            {
                OpenFilesHandler.fillTextArea(textArea, rows);
            }
        });
    }



    private static SelectOperation selectOperationCreation(String queryString)
    {
        return SelectParser.findSelect(queryString);
    }



    private static String processRow(String row, SelectOperation selectOperation)
    {
        return selectOperation.execute(row);
    }



    private static Operation operationCreation(String queryString)
    {
		/*if(row.contains(queryString))
		{
			return true;
		}
		else
		{
			return false;
		}*/

        //final Operation operation = Parser.parse(queryString, false);

        String wherePart;
        if(queryString.contains(Cons.WHERE))
        {
            wherePart = queryString.split(Cons.WHERE)[1];
        }
        else
        {
            wherePart = queryString;
        }

        final Operation operation = BraketHandler.creator(wherePart);
        return operation;
    }



    public static boolean searchEngineOne(String row, final Operation operation)
    {
        return operation.executeOperation(row);
    }

}
