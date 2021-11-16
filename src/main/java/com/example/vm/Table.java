package com.example.vm;

public class Table {
    private String atributo;
    private String atributo2;
    private String instrucao;
    private String linha;
    private String atributo_linha;

    public Table(String atributo, String atributo2, String instrucao, String linha, String atributo_linha) {
        this.atributo = atributo;
        this.atributo2 = atributo2;
        this.instrucao = instrucao;
        this.linha = linha;
        this.atributo_linha = atributo_linha;
    }

    public String getAtributo_linha() {
        return atributo_linha;
    }

    public void setAtributo_linha(String atributo_linha) {
        this.atributo_linha = atributo_linha;
    }

    public String getAtributo() {
        return atributo;
    }

    public String getAtributo2() {
        return atributo2;
    }

    public String getInstrucao() {
        return instrucao;
    }

    public String getLinha() {
        return linha;
    }
}
