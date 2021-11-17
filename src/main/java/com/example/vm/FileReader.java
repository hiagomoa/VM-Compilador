package com.example.vm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public byte[] reader() throws IOException {
        Path path = Paths.get("src/main/java/com/example/vm/Files/gerador.txt");
        byte[] data = Files.readAllBytes(path);
        return data;
    }
}
