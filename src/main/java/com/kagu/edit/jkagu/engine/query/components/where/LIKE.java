package com.kagu.edit.jkagu.engine.query.components.where;

public class LIKE implements Operation
{

    private String like;

    public LIKE(String like)
    {
        this.like = like;
    }

    @Override
    public boolean executeOperation(String row)
    {
        return row.contains(like);
    }

}
