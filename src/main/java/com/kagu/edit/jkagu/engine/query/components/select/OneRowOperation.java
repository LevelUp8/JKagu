package com.kagu.edit.jkagu.engine.query.components.select;

public class OneRowOperation implements SelectOperation
{

    @Override
    public String execute(String row)
    {
        return row;
    }

}
