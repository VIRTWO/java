package io;

import java.io.BufferedWriter;
import java.io.IOException;

public class SimpleFileWriter {

    private BufferedWriter bw = null;

    public SimpleFileWriter(String path, boolean append) throws IOException {
        java.io.FileWriter fw = new java.io.FileWriter(path, append);
        bw = new BufferedWriter(fw);
    }

    public void write(String string) throws IOException {
        bw.write(string);
    }

    public void close() throws IOException {
        bw.flush();
        bw.close();
    }

}
