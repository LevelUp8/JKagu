package com.kagu.edit.jkagu.engine.query.components.select;

public class FromIndexToEnd implements SelectOperation
{
    private String index;

    public FromIndexToEnd(String index)
    {
        this.index = index;
    }

    @Override
    public String execute(String row)
    {
        int indexInt = row.lastIndexOf(index);

        if (indexInt == -1)
        {
            return "";
        }
        return row.substring(indexInt, row.length());
    }

}
