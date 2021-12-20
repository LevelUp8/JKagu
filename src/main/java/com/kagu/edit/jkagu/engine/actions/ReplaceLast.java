package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.Utils;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;

public class ReplaceLast extends Command {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceLast(ObservableList<String> observableList, String target, String replacement, Label statusMessage) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
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
        statusMessage.setText("Replace last occurrence of element on every row executed!");
        return true;
    }

}
