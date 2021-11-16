package com.example.vm;

import com.example.vm.Consts.Command;
import com.example.vm.Utils.CompleteWithSpaces;

import java.io.IOException;
import java.util.LinkedList;

public class ReadLines {

    public LinkedList<Commands> Reader(byte[] file) throws IOException {
        LinkedList<Commands> linesCommands = new LinkedList<>();
        int numberLine=0;
        String line = "";
        for(int i=0; i < file.length; i+=32){
            for(int j = 0; j<32; j++){
                line += (char)file[i+j];
            }
            linesCommands.add(new Commands(String.format("%d", numberLine), line.substring(0,8), line.substring(8,16), line.substring(16,24), line.substring(24,32)));
            line="";
            numberLine++;
            i+=2;
        }
        return linesCommands;
    }

    public LinkedList<Commands> correctionLabels(LinkedList<Commands> linesCommands) throws IOException {
        linesCommands.forEach(element->{
            if(element.attribute_command.contains(Command.JMPF)||element.attribute_command.contains(Command.JMP)||element.attribute_command.contains(Command.CALL)){
                linesCommands.forEach(element_2->{
                    if(element_2.attribute_numberLine.contains(element.attribute_1)){
                        element.setAttribute_1(new CompleteWithSpaces().CompleteWithSpaces(element_2.line));
                        element_2.setAttribute_numberLine(element_2.line);
                    }
                });
            }
        });
        linesCommands.forEach(element->{
          element.setAttribute_numberLine(new CompleteWithSpaces().CompleteWithSpaces(element.line));
        });
        return linesCommands;
    }
}
