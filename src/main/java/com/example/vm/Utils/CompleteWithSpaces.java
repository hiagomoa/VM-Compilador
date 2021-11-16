package com.example.vm.Utils;

public class CompleteWithSpaces {
    public String CompleteWithSpaces(String label) {
        while (label.length() < 8) {
            label += " ";
        }
        return label;
    }
}
