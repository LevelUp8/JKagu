package com.kagu.edit.jkagu.engine.actions;

import javafx.collections.ObservableList;

import java.util.List;

public class ReplaceAll extends Command {

    private String target;
    private String replacement;

    public ReplaceAll(ObservableList<String> observableList, String target, String replacement) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public boolean execute() {
       List<String> replaced = observableList.stream()
                                    .map(s -> s.replace(this.target, this.replacement)).toList();

        observableList.clear();
        observableList.addAll(replaced);

        return true;
    }
}
