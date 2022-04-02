package com.kagu.edit.jkagu.engine.query.components.where;

public interface CompositeOperation extends Operation
{
    public void addChildOperation(Operation operation);
}
