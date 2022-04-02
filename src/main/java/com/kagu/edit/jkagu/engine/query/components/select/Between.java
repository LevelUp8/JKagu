package com.kagu.edit.jkagu.engine.query.components.select;

public class Between implements SelectOperation
{
    private String indexBeggining;
    private String indexEnd;

    public Between(String indexBeggining, String indexEnd)
    {
        this.indexBeggining = indexBeggining;
        this.indexEnd = indexEnd;
    }

    @Override
    public String execute(String row)
    {
        int indexBegginingInt = row.indexOf(indexBeggining);
        int indexEndInt = row.lastIndexOf(indexEnd);

        if (indexBegginingInt == -1 || indexEndInt == -1)
        {
            return "";
        }

        return row.substring(indexBegginingInt, indexEndInt + indexEnd.length());
    }

}
