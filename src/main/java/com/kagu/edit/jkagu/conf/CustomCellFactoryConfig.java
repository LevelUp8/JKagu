package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.RowListViewCell;
import com.kagu.edit.jkagu.conf.model.Row;
import com.kagu.edit.jkagu.engine.actions.EditOneRow;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CustomCellFactoryConfig implements ComponentConf {


    private final ListView<Row> listView;
    private final AnchorPane rootContainer;
    private final ObservableList<Row> observableList;

    public CustomCellFactoryConfig(ListView<Row> listView, AnchorPane rootContainer, ObservableList<Row> observableList) {
        this.listView = listView;
        this.rootContainer = rootContainer;
        this.observableList = observableList;
    }


    @Override
    public void configure() {
        listView.setCellFactory(studentListView -> {

            ContextMenu contextMenu = new ContextMenu();

            RowListViewCell cell = new RowListViewCell();
            MenuItem editItem = new MenuItem();
            //editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
            editItem.textProperty().bind(Bindings.format("Edit row"));
            editItem.setOnAction(event -> {

                HBox container =  cell.getMainContainer();
                Bounds boundsInScene = container.localToScene(container.getBoundsInLocal());
                TextArea editField = new TextArea();
                editField.setTranslateX(boundsInScene.getMinX());
                editField.setTranslateY(boundsInScene.getMinY());
                editField.setMinWidth(boundsInScene.getWidth());
                editField.setMinHeight(boundsInScene.getHeight());
                //editField.setMaxHeight(boundsInScene.getHeight());
                editField.setWrapText(true);

                Row selectedRow = listView.getSelectionModel().getSelectedItem();
                String text = selectedRow.content();

                editField.setText(text);
                rootContainer.getChildren().add(editField);

                ChangeListener<Boolean> focusListener = (observable, oldValue, newValue) -> {
                    if (!newValue) {
                        EditOneRow editOneRow = new EditOneRow(observableList, selectedRow, editField);
                        editOneRow.execute();
                        rootContainer.getChildren().remove(editField);
                    }
                };
                editField.focusedProperty().addListener(focusListener);
                // code to edit item...
                System.out.println("code to edit item...");
            });

            MenuItem copyItem = new MenuItem();
            //copyItem.textProperty().bind(Bindings.format("Copy \"%s\"", cell.itemProperty()));
            copyItem.textProperty().bind(Bindings.format("Copy row"));
            copyItem.setOnAction(event -> {
                Row selectedRow = listView.getSelectionModel().getSelectedItem();
                String text = selectedRow.content();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(text);
                clipboard.setContent(content);

                System.out.println("code to copy item...");
            });


            contextMenu.getItems().addAll(editItem, copyItem);

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });
    }
}
