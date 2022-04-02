package com.kagu.edit.jkagu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Serializable, Initializable {

    @FXML
    public Hyperlink repoHyperLink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        repoHyperLink.setOnAction((ActionEvent event) -> {
            Hyperlink h = (Hyperlink) event.getTarget();
            String s = h.getText();
            HelloApplication.getApplication().getHostServices().showDocument(s);
            event.consume();
        });
    }
}
