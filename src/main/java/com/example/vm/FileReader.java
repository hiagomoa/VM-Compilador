package com.example.vm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Classe FileReader responsavel pela leitura bruta do arquivo e retorno de um array de bytes
 */
public class FileReader {
    public byte[] reader(String path) throws IOException {
        Path p = Paths.get(path);
        byte[] data = Files.readAllBytes(p);
        return data;
    }
}
