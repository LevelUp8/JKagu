package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.conf.model.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public abstract class CaseSensitiveCommand extends Command {

    private CheckBox caseSensitive;


    CaseSensitiveCommand(ObservableList<Row> observableList, CheckBox caseSensitive) {
        super(observableList);
        this.caseSensitive = caseSensitive;
    }

    /**
     * If CaseSensitiveCommand then executeCaseSensitive() and executeCaseInsensitive() must be implemented
     * @return
     */
    @Override
    public boolean execute() {
        if (caseSensitive.isSelected())
        {
           return executeCaseSensitive();
        }
        else
        {
           return executeCaseInsensitive();
        }
    }


    public abstract boolean executeCaseSensitive();

    public abstract boolean executeCaseInsensitive();

}
