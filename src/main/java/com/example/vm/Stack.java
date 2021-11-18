package com.example.vm;

public class Stack {
    private String address;
    private String value;

    public Stack(String addres, String value) {
        this.address = addres;
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addres) {
        this.address = addres;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
