package com.kagu.edit.jkagu;

import com.kagu.edit.jkagu.conf.*;
import com.kagu.edit.jkagu.conf.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Serializable, Initializable {

    @FXML
    private Menu file;

    @FXML
    public Button searchButton;

    //-- Search single line functionality
    @FXML
    public RadioButton useSelectedLines;

    @FXML
    public TextField searchField;

    @FXML
    public HBox searchFieldContainer;

    //--- Search functionality query
    @FXML
    public RadioButton useAdvancedSelect;

    @FXML
    public HBox searchComboBoxContainer;

    @FXML
    public ComboBox<String> searchComboBox;

    //--- Search functionality from until
    @FXML
    public HBox searchFromUntilContainer;

    @FXML
    public RadioButton useSelectedLinesMultiline;

    @FXML
    public TextField searchFieldFrom;

    @FXML
    public TextField searchFieldUntil;


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
    public MenuItem copySelectedText;

    @FXML
    public MenuItem pasteText;

    @FXML
    public MenuItem undo;

    @FXML
    public MenuItem restoreInitialText;

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

    @FXML
    public MenuItem defaultTextSize;

    @FXML
    public MenuItem bigTextSize;

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
    public MenuItem about;

    @FXML
    public CheckBox caseSensitive;

    @FXML
    public CheckBox caseSensitiveSearch;

    @FXML
    public AnchorPane rootContainer;


    private final List<Row> initialList = new ArrayList<>();
    private final List<Row> list = new ArrayList<>();
    // Now add observability by wrapping it with ObservableList.
    private final ObservableList<Row> observableList = FXCollections.observableList(list);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<ComponentConf> configurationList = new ArrayList<>();

        configurationList.add(new SwitchReplaceTemplateConfig(findReplaceBox, templateIncrementBox, findAndReplace, templateCounter, statusMessage, replaceWhere, caseSensitive));

        SingleLineSearchContext singleLineSearchContext = new SingleLineSearchContext(useSelectedLines, searchField, searchFieldContainer);
        MuliLineSearchContext muliLineSearchContext = new MuliLineSearchContext(useSelectedLinesMultiline, searchFieldFrom, searchFieldUntil, searchFromUntilContainer);
        QuerySearchContext querySearchContext = new QuerySearchContext(useAdvancedSelect, searchComboBoxContainer, searchComboBox);
        configurationList.add(new SearchContentConfig(singleLineSearchContext, querySearchContext, muliLineSearchContext, searchButton, observableList, initialList, searchField, statusMessage, caseSensitiveSearch));

        configurationList.add(new OpenFileConfig(progressBar, statusMessage, appendFile, listView, observableList, initialList));

        configurationList.add(new SaveContentConfig(observableList, saveFile, statusMessage));

        configurationList.add(new PasteContentConfig(pasteText, observableList, statusMessage, initialList, listView));

        configurationList.add(new CopyContentConfig(copyText, observableList, statusMessage));

        configurationList.add(new CopySelectedTextContentConfig(copySelectedText, statusMessage, listView));

        configurationList.add(new FindReplaceContentConfig(observableList, replaceWhere, changeFrom, changeTo, buttonRefactor, statusMessage, caseSensitive));

        configurationList.add(new TemplateIncrementConfig(templateValue, initialValue, increment, observableList, buttonRefactorTemplateInc, statusMessage));

        configurationList.add(new UndoConfig(undo, statusMessage));

        configurationList.add(new RestoreInitialTextConfig(observableList, initialList, statusMessage, restoreInitialText));

        configurationList.add(new CloseAppConfig(closeApp));

        configurationList.add(new ViewThemeConfig(defaultTheme, darkTheme, statusMessage));

        configurationList.add(new ViewTextSizeConfig(defaultTextSize, bigTextSize, statusMessage));

        configurationList.add(new CustomCellFactoryConfig(listView, rootContainer, observableList));

        configurationList.forEach( c -> c.configure() );
    }


    public void configureScene(Scene scene) {

        RefactorButtons refactorButtons = new RefactorButtons(buttonRefactor, buttonRefactorTemplateInc, findAndReplace, templateCounter);
        InputOutputButtons inputOutputButtons = new InputOutputButtons(file, appendFile, saveFile, copyText, pasteText);
        ThemeButtons themeButtons = new ThemeButtons(defaultTheme, darkTheme);

        SceneConfigurator sceneConfigurator = new SceneConfigurator(scene, refactorButtons, inputOutputButtons, themeButtons, undo, closeApp);
        sceneConfigurator.configure();

    }

    public void configureStage(Stage stage) {

        HelpConfig helpConfig = new HelpConfig(about, stage);
        helpConfig.configure();
    }
}