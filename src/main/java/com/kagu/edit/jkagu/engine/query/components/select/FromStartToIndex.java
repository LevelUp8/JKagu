package com.kagu.edit.jkagu.engine.query.components.select;


public class FromStartToIndex implements SelectOperation
{
    private String index;

    public FromStartToIndex(String index)
    {
        this.index = index;
    }

    @Override
    public String execute(String row)
    {
        int indexInt = row.indexOf(index);

        if (indexInt == -1)
        {
            return "";
        }
        return row.substring(0, indexInt + index.length());
    }

}
