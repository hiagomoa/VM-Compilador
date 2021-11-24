package com.example.vm;
import com.example.vm.Consts.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    LinkedList<Commands> listCommand;
    int top = 0;
    boolean flagStop = false;
    Integer i = 0;
    LinkedList<Integer> stack = new LinkedList<>();
    int select=0;

    @FXML
    private MenuItem menuItemOpenFile;

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
    private TableColumn<Stack, String> address;

    @FXML
    private TableColumn<Stack, String> value;

    @FXML
    private TableView<Stack> memoryTable;

    @FXML
    private Button run;

    @FXML
    private Button stop;

    @FXML
    private RadioButton runNormal;

    @FXML
    private RadioButton runStepByStep;

    @FXML
    private ListView<String> listOutput;

    @FXML
    private ObservableList<String> observableListView;

    public List<String> list = new ArrayList<>();


    @FXML
    public void menuItemResetProgram() {
        top = 0;
        flagStop = false;
        i = 0;
        stop.setDisable(false);
        run.setDisable(false);

        list.clear();
        observableListView=FXCollections.observableArrayList(list);
        listOutput.setItems(observableListView);

        listStack.clear();
        observableListStack=FXCollections.observableArrayList(listStack);
        memoryTable.setItems(observableListStack);
    }

    public ObservableList<Commands> convertToObservableList() {
        return FXCollections.observableArrayList(
                listCommand
        );
    }

    @FXML
    private void setListOutput(String exit) {
        list.add(exit);
        observableListView=FXCollections.observableArrayList(list);
        listOutput.setItems(observableListView);
    }

    public List<Stack> listStack = new ArrayList<>();
    @FXML
    private ObservableList<Stack> observableListStack;


    private void setStack() {
        listStack.clear();

        for(int i=0; i<=top; i++){
           Integer element = stack.get(i);
            if (element != null){
                listStack.add(new Stack(String.valueOf(i), String.valueOf(element)));
            }else{
                listStack.add(new Stack(String.valueOf(i), ""));
            }
        }

        observableListStack=FXCollections.observableArrayList(listStack);
        memoryTable.setItems(observableListStack);
    }

    @FXML
    private void handlerCheckNormal(){
        runStepByStep.setSelected(false);
        select = 1;

        if(!runNormal.isSelected()){
            runNormal.setSelected(true);
        }

    }

    @FXML
    private void  handlerCheckStepByStep(){
        runNormal.setSelected(false);
        select = 0;
        if(!runStepByStep.isSelected()){
            runStepByStep.setSelected(true);
        }
    }

    @FXML
    private void  handleButtonStop(){
        stop.setDisable(true);
        run.setDisable(true);
    }

    @FXML
    private void handleButtonRun() {
        if(runNormal.isSelected()){
            while(flagStop==false){
                Controller();
                setStack();
                i++;
            }
            handleButtonStop();
        }else{
            if(flagStop==false){
                Controller();
                setStack();
                i++;
            }else{
                handleButtonStop();
            }
        }
    }

    @FXML
    public int openInput() {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("VM");
        td.setGraphic(null);
        td.setHeaderText("Insira um Inteiro");
        td.showAndWait();
        Integer value = Integer.valueOf(td.getEditor().getText());
        return value;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atributo.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_1"));
        atributo2.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_2"));
        instrucao.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_command"));
        linha.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_numberLine"));

        value.setCellValueFactory(new PropertyValueFactory<Stack, String>("value"));
        address.setCellValueFactory(new PropertyValueFactory<Stack, String>("address"));
        runNormal.setSelected(true);
    }

    @FXML
    private int walker(Commands instruction) {
        Integer attribute_1 = null;
        Integer attribute_2 = null;
        if (!instruction.attribute_1.equals("")) {
            attribute_1 = Integer.valueOf(instruction.attribute_1);
        }
        if (!instruction.attribute_2.equals("")) {
            attribute_2 = Integer.valueOf(instruction.attribute_2);
        }
        switch (instruction.attribute_command) {
            case Command.LDC:
                top++;
                stack.set(top, attribute_1);
                break;
            case Command.LDV:
                top++;
                stack.set(top, stack.get(attribute_1));
                break;
            case Command.ADD:
                stack.set(top - 1, stack.get(top - 1) + stack.get(top));
                top--;
                break;
            case Command.SUB:
                stack.set(top - 1, stack.get(top - 1) - stack.get(top));
                top--;
                break;
            case Command.MULT:
                stack.set(top - 1, stack.get(top - 1) * stack.get(top));
                top--;
                break;
            case Command.DIVI:
                stack.set(top - 1, stack.get(top - 1) / stack.get(top));
                top--;
                break;
            case Command.INV:
                stack.set(top, -stack.get(top));
                break;
            case Command.AND:
                if (stack.get(top - 1) == 1 && stack.get(top) == 1) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.OR:
                if (stack.get(top - 1) == 1 || stack.get(top) == 1) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.NEG:
                stack.set(top, 1 - stack.get(top));
                break;
            case Command.CME:
                if (stack.get(top - 1) < stack.get(top)) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.CMA:
                if (stack.get(top - 1) > stack.get(top)) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.CEQ:
                if (stack.get(top - 1) == stack.get(top)) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.CDIF:
                if (stack.get(top - 1) != stack.get(top)) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.CMEQ:
                if (stack.get(top - 1) <= stack.get(top)) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;
            case Command.CMAQ:
                if (stack.get(top - 1) >= stack.get(top)) {
                    stack.set(top - 1, 1);
                } else {
                    stack.set(top - 1, 0);
                }
                top--;
                break;

            case Command.STR:
                stack.set(attribute_1, stack.get(top));
                top--;
                break;
            case Command.JMP:
                i = attribute_1;
                break;
            case Command.JMPF:
                if (stack.get(top) == 0) {
                    i = attribute_1;
                }
                top--;
                break;
            case Command.RD:
                top++;
                Integer readed = openInput();
                stack.set(top, readed);
                break;
            case Command.PRN:
                setListOutput(String.valueOf(stack.get(top)));
                top--;
                break;
            case Command.START:
                top = -1;
                break;
            case Command.ALLOC:
                for (int k = 0; k < attribute_2; k++) {
                    top++;
                    stack.set(top, stack.get(attribute_1 + k));
                }
                break;
            case Command.DALLOC:
                for (int k = attribute_2 - 1; k >= 0; k--) {
                    stack.set(attribute_1 + k, stack.get(top));
                    top--;
                }
                break;
            case Command.HLT:
                flagStop = true;
                //TODO: parar execução;
                break;
            case Command.CALL:
                top++;
                stack.set(top, i);
                i = attribute_1;
                break;
            case Command.RETURN:
                i = stack.get(top);
                top--;
                break;
            default:
                System.out.println("DEFAULT");
                break;
        }
        return 0;
    }
    @FXML
    public void menuItemOpenFileAction() throws IOException {
        menuItemResetProgram();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.obj"));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            byte[] file = new FileReader().reader(f.getAbsolutePath());
            Read(file);
            ObservableList obListCommands = convertToObservableList();
            table.setItems(obListCommands);
        }

    }

    private void Read(byte[] file ) {
        try {
            LinkedList<Commands> commandsLines = new ReadLines().Reader(file);
            listCommand = new ReadLines().correctionLabels(commandsLines);
            for (int j = 0; j < 500; j++) {
                stack.add(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void Controller() {
        table.requestFocus();
        table.getSelectionModel().select(i);
        table.getFocusModel().focus(i);
        table.scrollTo(i);
        walker(listCommand.get(i));
    }
}