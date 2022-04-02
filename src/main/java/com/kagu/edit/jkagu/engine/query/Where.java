package com.kagu.edit.jkagu.engine.query;

public class Where {
    public final String whereClause;

    public Where(final String whereClause)
    {
        this.whereClause = whereClause;
    }

    public String getWhereClause()
    {
        return whereClause;
    }

}
