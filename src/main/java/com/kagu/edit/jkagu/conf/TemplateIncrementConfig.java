package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.IntegerContainer;
import com.kagu.edit.jkagu.conf.model.Row;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public record TemplateIncrementConfig(TextField templateValue,
                                      TextField initialValue,
                                      TextField increment,
                                      ObservableList<Row> observableList,
                                      Button buttonRefactorTemplateInc,
                                      Label statusMessage) implements ComponentConf {

    @Override
    public void configure() {

        setUpOnlyNumbersForTextField(initialValue);
        setUpOnlyNumbersForTextField(increment);

        buttonRefactorTemplateInc.setOnAction(e -> {

            String initialValueString = this.initialValue.getText();
            String incrementString = this.increment.getText();

            Integer initialValue = Integer.valueOf(initialValueString);
            IntegerContainer container = new IntegerContainer(initialValue);
            Integer increment = Integer.valueOf(incrementString);

            List<Row> replacedLinesList = observableList.stream().map(row -> {

                String template = templateValue.getText();

                String newContent = getString(container, increment, row.content(), template);

                return new Row(row.rowNumber(), newContent);

            }).collect(Collectors.toList());

            observableList.clear();
            observableList.addAll(replacedLinesList);
            statusMessage.setText("Template string is replaced by incremented number!");
        });
    }

    private void setUpOnlyNumbersForTextField(final TextField numericTextField) {
        // force the field to be numeric only
        numericTextField.textProperty().addListener((ObservableValue<? extends String> o, String oldValue, String newValue) ->
                {
                    if (!newValue.matches("\\d*")) {
                        numericTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
        );
    }

    private String getString(IntegerContainer container, Integer increment, String l, String template) {
        int index = l.indexOf(template);
        if (index != -1) {
            String firstPart = l.substring(0, index);
            String lastPart = l.substring(index + template.length());
            StringBuilder sb = new StringBuilder(firstPart);
            sb.append(container.getNum());
            container.addToCurrentNum(increment);
            sb.append(lastPart);

            return getString(container, increment, sb.toString(), template);
        } else {
            return l;
        }
    }
}
