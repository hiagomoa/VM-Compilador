package com.example.vm;

public class Commands{
    String line;
    String attribute_numberLine;
    String attribute_command;
    String attribute_1;
    String attribute_2;

    public Commands(String line, String attribute_numberLine, String attribute_command, String attribute_1, String attribute_2) {
        this.line = line;
        this.attribute_numberLine = attribute_numberLine;
        this.attribute_command = attribute_command;
        this.attribute_1 = attribute_1;
        this.attribute_2 = attribute_2;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getAttribute_numberLine() {
        return attribute_numberLine;
    }

    public void setAttribute_numberLine(String attribute_numberLine) {
        this.attribute_numberLine = attribute_numberLine;
    }

    public String getAttribute_command() {
        return attribute_command;
    }

    public void setAttribute_command(String attribute_command) {
        this.attribute_command = attribute_command;
    }

    public String getAttribute_1() {
        return attribute_1;
    }

    public void setAttribute_1(String attribute_1) {
        this.attribute_1 = attribute_1;
    }

    public String getAttribute_2() {
        return attribute_2;
    }

    public void setAttribute_2(String attribute_2) {
        this.attribute_2 = attribute_2;
    }
}
