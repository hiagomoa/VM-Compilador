package com.example.vm;


import com.example.vm.Consts.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.stream.IntStream;


public class MainController implements Initializable {

    LinkedList<Commands> listCommand;
    int top = 0;
    boolean flagStop = false;
    Integer i = 0;
    LinkedList<Integer> stack = new LinkedList<>();

    @FXML
    private MenuItem menuItemOpenFile;

    @FXML
    public void menuItemOpenFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.obj"));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            System.out.println("xxxx: " + f.getAbsolutePath());
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

    public ObservableList<Commands> convertToObservableList() {
        Controller();
        return FXCollections.observableArrayList(
                listCommand
        );
    }

    @FXML
    private void Controller() {
        try {
            byte[] file = new FileReader().reader();
            LinkedList<Commands> commandsLines = new ReadLines().Reader(file);
            listCommand = new ReadLines().correctionLabels(commandsLines);
            for (int i = 0; i < 1000; i++) {
                stack.add(0);
            }
            int gg = 0;
            while (gg == 0) {
                gg = walker(listCommand.get(i));
                //  table.setItems(convertToObservableList());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private int walker(Commands instruction) {
        Integer attribute_1 = null;
        Integer attribute_2 = null;
        System.out.println("Commands " + instruction.attribute_command);
        if (!instruction.attribute_1.equals("")) {
            attribute_1 = Integer.valueOf(instruction.attribute_1);
            System.out.println("attribute_1 " + attribute_1);
        }
        if (!instruction.attribute_2.equals("")) {
            attribute_2 = Integer.valueOf(instruction.attribute_2);
        }

        switch (instruction.attribute_command) {
            case Command.LDC:
                top++;
                stack.push(attribute_1);
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
                } else {
                    i++;
                }
                top--;
                break;
            case Command.RD:
                top++;
                Integer readed = openInput();
                stack.set(top, readed);
                break;
            case Command.PRN:
                System.out.println(stack.get(top));//TODO: PRINTAR CORRETAMENTE
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
                for (int k = attribute_2 - 1; k == 0; k--) {
                    stack.set(attribute_1 + k, stack.get(top));
                    top--;
                }
                break;
            case Command.HLT:
                flagStop = true;
                //TODO: parar execução;
                return -1;
            case Command.CALL:
                top++;
                stack.set(top, i + 1);
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
    public int openInput() {
        TextInputDialog td = new TextInputDialog();

        td.showAndWait();
        Integer value = Integer.valueOf(td.getEditor().getText());
        System.out.println("OPAAAAAAAAAAAAAA" + value);
        return value;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atributo.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_1"));
        atributo2.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_2"));
        instrucao.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_command"));
        linha.setCellValueFactory(new PropertyValueFactory<Commands, String>("attribute_numberLine"));
        table.setItems(convertToObservableList());
    }
}