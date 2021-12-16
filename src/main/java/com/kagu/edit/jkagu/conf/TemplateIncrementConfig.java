package com.kagu.edit.jkagu.conf;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class TemplateIncrementConfig implements ComponentConf {

    private TextField templateValue;
    private TextField initialValue;
    private TextField increment;
    private ObservableList<String> observableList;
    private Button buttonRefactorTemplateInc;

    public TemplateIncrementConfig(TextField templateValue,
                                   TextField initialValue,
                                   TextField increment,
                                   ObservableList<String> observableList,
                                   Button buttonRefactorTemplateInc)
    {
        this.templateValue = templateValue;
        this.initialValue = initialValue;
        this.increment = increment;
        this.observableList = observableList;
        this.buttonRefactorTemplateInc = buttonRefactorTemplateInc;
    }

    @Override
    public void configure() {

        // force the field to be numeric only
        initialValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    initialValue.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // force the field to be numeric only
        increment.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    increment.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        buttonRefactorTemplateInc.setOnAction(e -> {

            String initialValueString = this.initialValue.getText();
            String incrementString = this.increment.getText();

            Integer initialValue = Integer.valueOf(initialValueString);
            IntegerContainer container = new IntegerContainer(initialValue);
            Integer increment = Integer.valueOf(incrementString);

            List<String> replacedLinesList = observableList.stream().map(l -> {

                String template = templateValue.getText();

                return getString(container, increment, l, template);


            }).collect(Collectors.toList());

            observableList.clear();
            observableList.addAll(replacedLinesList);

        });
    }

    private String getString(IntegerContainer container, Integer increment, String l, String template) {
        int index = l.indexOf(template);
        if(index != -1)
        {
            String firstPart = l.substring(0, index);
            String lastPart = l.substring(index + template.length());
            StringBuilder sb = new StringBuilder(firstPart);
            sb.append(container.getNum());
            container.addToCurrentNum(increment);
            sb.append(lastPart);

            return getString(container, increment, sb.toString(), template);
        }
        else
        {
            return l;
        }
    }
}
