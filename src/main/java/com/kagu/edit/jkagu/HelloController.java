package com.kagu.edit.jkagu;

import com.kagu.edit.jkagu.conf.*;
import com.kagu.edit.jkagu.conf.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Serializable, Initializable {

    @FXML
    private Menu file;

    //-- Search functionality
    @FXML
    public RadioButton useWholeFile;

    @FXML
    public RadioButton useSelectedLines;

    @FXML
    public RadioButton useAdvancedSelect;

    @FXML
    public Button searchButton;

    @FXML
    public TextField searchField;

    @FXML
    public HBox searchBox;

    @FXML
    public MenuItem search;

    //--- Search functionality from until
    @FXML
    public MenuItem searchMultiline;

    @FXML
    public HBox searchMultilineBox;

    @FXML
    public RadioButton useWholeFileMultiline;

    @FXML
    public RadioButton useSelectedLinesMultiline;

    @FXML
    public TextField searchFieldFrom;

    @FXML
    public TextField searchFieldUntil;

    @FXML
    public Button searchButtonMultiline;

    //--- Information functionality
    @FXML
    public ProgressBar progressBar;

    @FXML
    public Label statusMessage;

    //--- File buttons
    @FXML
    public MenuItem appendFile;

    @FXML
    public MenuItem saveFile;

    //--- Edit buttons
    @FXML
    public MenuItem copyText;

    @FXML
    public MenuItem pasteText;

    @FXML
    public MenuItem undo;

    //-- Tools button
    @FXML
    public MenuItem findAndReplace;

    @FXML
    public MenuItem templateCounter;

    @FXML
    public MenuItem closeApp;

    //-- View Button
    @FXML
    public MenuItem defaultTheme;

    @FXML
    public MenuItem darkTheme;

    //--- text place
    @FXML
    public ListView<Row> listView;

    //--- replace functionality
    @FXML
    public ChoiceBox<String> replaceWhere;

    @FXML
    public TextField changeTo;

    @FXML
    public TextField changeFrom;

    @FXML
    public Button buttonRefactor;

    @FXML
    public HBox findReplaceBox;

    //--- placeholder increment functionality
    @FXML
    public HBox templateIncrementBox;

    @FXML
    public TextField templateValue;

    @FXML
    public TextField initialValue;

    @FXML
    public TextField increment;

    @FXML
    public Button buttonRefactorTemplateInc;

    @FXML
    public Label fromLabel;

    @FXML
    public Label untilLabel;

    @FXML
    public MenuItem about;

    @FXML
    public CheckBox caseSensitive;


    // Use Java Collections to create the List.
    private final List<Row> initialList = new ArrayList<>();

    // Use Java Collections to create the List.
    private final List<Row> list = new ArrayList<>();

    // Now add observability by wrapping it with ObservableList.
    private final ObservableList<Row> observableList = FXCollections.observableList(list);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SwitchSearchConfig switchSearchConfig = new SwitchSearchConfig(searchBox, searchMultilineBox, search, searchMultiline, statusMessage);
        switchSearchConfig.configure();

        SwitchReplaceTemplateConfig switchReplaceTemplateConfig = new SwitchReplaceTemplateConfig(findReplaceBox,
                templateIncrementBox,
                findAndReplace,
                templateCounter,
                statusMessage);
        switchReplaceTemplateConfig.configure();

        FilterContentMultilineConfig filterContentMultilineConfig = new FilterContentMultilineConfig(useWholeFileMultiline,
                useSelectedLinesMultiline,
                searchButtonMultiline,
                observableList,
                initialList,
                searchFieldFrom,
                searchFieldUntil,
                statusMessage,
                fromLabel,
                untilLabel);
        filterContentMultilineConfig.configure();


        FilterContentConfig filterContentConfig = new FilterContentConfig(useWholeFile,
                useSelectedLines,
                useAdvancedSelect,
                searchButton,
                observableList,
                initialList,
                searchField,
                statusMessage);
        filterContentConfig.configure();

        AppendContentConfig openContentConfig = new AppendContentConfig(progressBar, statusMessage, appendFile, listView, observableList, initialList);
        openContentConfig.configure();

        SaveContentConfig saveContentConfig = new SaveContentConfig(observableList, saveFile, statusMessage);
        saveContentConfig.configure();

        PasteContentConfig pasteContentConfig = new PasteContentConfig(pasteText, observableList, statusMessage, initialList, listView);
        pasteContentConfig.configure();

        CopyContentConfig copyContentConfig = new CopyContentConfig(copyText, observableList, statusMessage);
        copyContentConfig.configure();

        FindReplaceContentConfig findReplaceContentConfig = new FindReplaceContentConfig(observableList, replaceWhere, changeFrom, changeTo, buttonRefactor, statusMessage, caseSensitive);
        findReplaceContentConfig.configure();


        TemplateIncrementConfig templateIncrementConfig = new TemplateIncrementConfig(templateValue,
                initialValue,
                increment,
                observableList,
                buttonRefactorTemplateInc,
                statusMessage);
        templateIncrementConfig.configure();

        UndoConfig undoConfig = new UndoConfig(undo, statusMessage);
        undoConfig.configure();

        CloseAppConfig closeAppConfig = new CloseAppConfig(closeApp);
        closeAppConfig.configure();

        ViewThemeConfig viewThemeConfig = new ViewThemeConfig(defaultTheme, darkTheme, statusMessage);
        viewThemeConfig.configure();

        listView.setCellFactory(studentListView -> new RowListViewCell());
    }


    public void configureScene(Scene scene) {

        RefactorButtons refactorButtons = new RefactorButtons(buttonRefactor, buttonRefactorTemplateInc, findAndReplace, templateCounter);
        InputOutputButtons inputOutputButtons = new InputOutputButtons(file, appendFile, saveFile, copyText, pasteText);
        SearchButtons searchButtons = new SearchButtons(searchButton, search, searchButtonMultiline, searchMultiline);
        ThemeButtons themeButtons = new ThemeButtons(defaultTheme, darkTheme);

        SceneConfigurator sceneConfigurator = new SceneConfigurator(scene, refactorButtons, inputOutputButtons, searchButtons, themeButtons, undo, closeApp);
        sceneConfigurator.configure();

    }

    public void configureStage(Stage stage) {

        HelpConfig helpConfig = new HelpConfig(about, stage);
        helpConfig.configure();
    }
}