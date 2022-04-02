package com.kagu.edit.jkagu.engine.query;

import com.kagu.edit.jkagu.engine.query.components.where.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static Operation parse(String text, boolean wholeClause)
    {

        String[] andParts = text.split(" and ");
        String[] orParts = text.split(" or ");

        if(andParts.length > 1)
        {
            List<Operation> children = new ArrayList<>();
            for(String part : andParts)
            {
                final Operation operation = parse(part, wholeClause);
                children.add(operation);
            }
            CompositeOperation and = new AND(children);

            return and;
        }


        if(orParts.length > 1)
        {
            List<Operation> children = new ArrayList<>();
            for(String part : orParts)
            {
                final Operation operation = parse(part, wholeClause);
                children.add(operation);
            }
            CompositeOperation or = new OR(children);

            return or;
        }

        String[] likeParts = text.split(" like ");
        Operation like = new LIKE(likeParts[1]);
        return like;

    }

}
