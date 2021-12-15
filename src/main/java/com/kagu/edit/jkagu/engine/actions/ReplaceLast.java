package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.Utils;
import javafx.collections.ObservableList;

import java.util.List;

public class ReplaceLast extends Command {

    private String target;
    private String replacement;

    public ReplaceLast(ObservableList<String> observableList, String target, String replacement) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public boolean execute() {
        List<String> replaced = observableList.stream()
                .map(s -> {
                    int lastOccurrenceIndex = s.lastIndexOf(target);
                    final String result = Utils.getReplacedString(s, lastOccurrenceIndex, target, replacement);
                    return result;
                }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        return true;
    }

}
