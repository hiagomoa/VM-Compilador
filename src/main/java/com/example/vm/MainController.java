package com.example.vm;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    LinkedList<Commands> listCommand;
    @FXML
    private MenuItem menuItemOpenFile;

    @FXML
    public void menuItemOpenFileAction(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.obj"));
        File f = fileChooser.showOpenDialog(null);
         if(f!=null){
           System.out.println("xxxx: "+f.getAbsolutePath());
        }
    }
    @FXML
    private TableView<Commands> table;

    @FXML
    private TableColumn<Commands, String> atributo;

    @FXML
    private TableColumn<Commands, String> atributo2;

    @FXML
    private TableColumn<Commands, String> instrucao;

    @FXML
    private TableColumn<Commands, String> linha;

    @FXML
    private TableView<?> memoriaTable;
    @FXML
    private TableColumn<?, ?> endereco;
    @FXML
    private TableColumn<?, ?> valor;

    public ObservableList<Commands> convertToObservableList(){
        try {
            byte[] file = new FileReader().reader();
            LinkedList<Commands> commandsLines = new ReadLines().Reader(file);
            listCommand = new ReadLines().correctionLabels(commandsLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(
                listCommand
                );
    }
//    ObservableList<Commands> list = FXCollections.observableArrayList(
//            new Commands("xx", "a", "ddd", "cc", "ggg")
//    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atributo.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_1"));
        atributo2.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_2"));
        instrucao.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_command"));
        linha.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_numberLine"));
        table.setItems(convertToObservableList());
    }
}