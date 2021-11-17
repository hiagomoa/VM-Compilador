package com.example.vm;

import com.example.vm.Consts.Command;

import java.io.IOException;
import java.util.LinkedList;

public class ReadLines {

    public LinkedList<Commands> Reader(byte[] file) throws IOException {
        LinkedList<Commands> linesCommands = new LinkedList<>();
        int numberLine = 0;
        String line = "";
        for (int i = 0; i < file.length; i += 32) {
            for (int j = 0; j < 32; j++) {
                line += (char) file[i + j];
            }
            linesCommands.add(new Commands(String.format("%d", numberLine), line.substring(0, 8), line.substring(8, 16), line.substring(16, 24), line.substring(24, 32)));
            line = "";
            numberLine++;
            i += 2;
        }
        return linesCommands;
    }

    public LinkedList<Commands> correctionLabels(LinkedList<Commands> linesCommands) throws IOException {

        linesCommands.forEach(element -> {
            element.setAttribute_numberLine(element.attribute_numberLine.trim());
            element.setAttribute_command(element.attribute_command.trim());
            element.setAttribute_1(element.attribute_1.trim());
            element.setAttribute_2(element.attribute_2.trim());
        });
        int o = 0;
        o = 1;
        linesCommands.forEach(element -> {
            if (element.attribute_command.contains(Command.JMPF) || element.attribute_command.contains(Command.JMP) || element.attribute_command.contains(Command.CALL)) {
                Commands finded = linesCommands.stream()
                        .filter(element_2 -> element_2.attribute_numberLine.equals(element.attribute_1))
                        .findFirst().get();
                System.out.println(" COMAND " + element.attribute_command + " numberLine: " + finded.attribute_numberLine + " elemento 2: " + finded.line + " atribute1 elem1: " + element.attribute_1);
                element.setAttribute_1(finded.line);
            }
        });

        linesCommands.forEach(element -> {
            element.setAttribute_numberLine(element.line);
            element.setAttribute_command(element.attribute_command);
        });
        return linesCommands;
    }
}
