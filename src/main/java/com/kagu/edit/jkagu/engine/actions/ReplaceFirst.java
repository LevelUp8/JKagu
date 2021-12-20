package com.kagu.edit.jkagu.engine.actions;

import com.kagu.edit.jkagu.Utils;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;

public class ReplaceFirst extends Command {

    private String target;
    private String replacement;
    private Label statusMessage;

    public ReplaceFirst(ObservableList<String> observableList, String target, String replacement, Label statusMessage) {
        super(observableList);
        this.target = target;
        this.replacement = replacement;
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean execute() {
       List<String> replaced = observableList.stream()
                                    .map(s -> {
                                         int firstOccurrenceIndex = s.indexOf(target);
                                         final String result = Utils.getReplacedString(s, firstOccurrenceIndex, target, replacement);
                                         return result;
                                    }).toList();

        observableList.clear();
        observableList.addAll(replaced);
        statusMessage.setText("Replace first occurrence of the element on every row executed!");

        return true;
    }
}
