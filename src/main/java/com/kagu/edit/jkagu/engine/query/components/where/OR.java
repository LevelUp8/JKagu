package com.kagu.edit.jkagu.engine.query.components.where;

import java.util.List;

public class OR implements CompositeOperation
{
    List<Operation> children;

    public OR(final List<Operation> children)
    {
        this.children = children;
    }

    @Override
    public boolean executeOperation(String row)
    {
        for(Operation child: children)
        {
            if(child.executeOperation(row))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addChildOperation(Operation operation)
    {
        children.add(operation);
    }

}
