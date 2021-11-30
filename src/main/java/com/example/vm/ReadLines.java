package com.example.vm;

import com.example.vm.Consts.Command;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Class ReadLines responsavel pela leitura das instruções e manipulações necessárias
 */
public class ReadLines {

    /**
     * Método Reader responsavel pela leitura do array byte transformando o em uma lista com os comandos
     * contendo linha, label, comando e 2 parametros.
     * É lido de 32 em 32 bytes a cada 32 bytes é setado de 8 em 8 em ordem 5 parametros a linha, pelo contador "line"
     * a label na posição de 0-8, o commando 8-16, atributo_1 16-24, atributo_2 24-32.
     * @param file parametro contendo o array de byte lido do arquivo selecionado em "menuItemOpenFileAction"
     * @return linesCommands que é um LinkedList<Commands>
     * @see Commands
     * @throws IOException
     */
    public LinkedList<Commands> Reader(byte[] file) throws IOException {
        LinkedList<Commands> linesCommands = new LinkedList<>();
        int numberLine = 0;
        String line = "";
        for (int i = 0; i < file.length-1; i += 32) {
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

    /**
     * O método correctionLabels faz a correção das label presente inicialmente no comando
     * para a linha que o commando em questão esta
     * Ex:
     * "JMP     3 "  ---> "7      JMP     30", 7 é o numero da linha do comando JMP e 30 para onde o JMP ira.
     * @param linesCommands linesCommands é um LinkedList<Commands>
     * @return linesCommands é um LinkedList<Commands>
     * @see Commands
     * @throws IOException
     */
    public LinkedList<Commands> correctionLabels(LinkedList<Commands> linesCommands) throws IOException {
        linesCommands.forEach(element -> {
            element.setAttribute_numberLine(element.attribute_numberLine.trim());
            element.setAttribute_command(element.attribute_command.trim());
            element.setAttribute_1(element.attribute_1.trim());
            element.setAttribute_2(element.attribute_2.trim());
        });

        linesCommands.forEach(element -> {
            if (element.attribute_command.contains(Command.JMPF) || element.attribute_command.contains(Command.JMP) || element.attribute_command.contains(Command.CALL)) {
                Commands finded = linesCommands.stream()
                        .filter(element_2 -> element_2.attribute_numberLine.equals(element.attribute_1))
                        .findFirst().get();
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
