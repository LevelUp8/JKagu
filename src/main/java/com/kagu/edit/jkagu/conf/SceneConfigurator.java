package com.kagu.edit.jkagu.conf;

import com.kagu.edit.jkagu.conf.model.InputOutputButtons;
import com.kagu.edit.jkagu.conf.model.RefactorButtons;
import com.kagu.edit.jkagu.conf.model.ThemeButtons;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;

public class SceneConfigurator implements ComponentConf {


    private MenuItem closeApp;
    private Scene scene;
    private RefactorButtons refactorButtons;
    private InputOutputButtons inputOutputButtons;
    private ThemeButtons themeButtons;
    private MenuItem undo;

    public SceneConfigurator(Scene scene,
                             RefactorButtons refactorButtons,
                             InputOutputButtons inputOutputButtons,
                             ThemeButtons themeButtons,
                             MenuItem undo,
                             MenuItem closeApp) {

        this.scene = scene;
        this.refactorButtons = refactorButtons;
        this.inputOutputButtons = inputOutputButtons;
        this.themeButtons = themeButtons;
        this.undo = undo;
        this.closeApp = closeApp;
    }

    @Override
    public void configure() {
        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
            inputOutputButtons.openFile().setAccelerator(kc);
        }
        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            inputOutputButtons.saveFile().setAccelerator(kc);
        }
        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
            inputOutputButtons.copyText().setAccelerator(kc);
        }
        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
            inputOutputButtons.pasteText().setAccelerator(kc);
        }

        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
            refactorButtons.findAndReplace().setAccelerator(kc);
        }
        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN);
            refactorButtons.templateCounter().setAccelerator(kc);
        }


        {
            KeyCombination kc = new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN);
            undo.setAccelerator(kc);
        }


    }
}
