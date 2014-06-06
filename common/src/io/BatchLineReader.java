package io;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BatchLineReader {

    private BufferedReader br = null;
    private int batchSize = 1;
    private List<String> lineList = null;

    private BatchLineReader(int batchSize) {
        this.batchSize = batchSize;
        lineList = new ArrayList<String>(this.batchSize);
    }

    public BatchLineReader(String filePath, int batchSize)
            throws FileNotFoundException {
        this(batchSize);
        FileReader fr = new FileReader(filePath);
        br = new BufferedReader(fr);
    }

    public BatchLineReader(File file, int batchSize)
            throws FileNotFoundException {
        this(batchSize);
        FileReader fr = new FileReader(file);
        br = new BufferedReader(fr);
    }

    public List<String> read() throws IOException {
        lineList.clear();

        String line = br.readLine();
        int count = 0;
        while (line != null) {
            lineList.add(line);
            count = count + 1;
            if (count == this.batchSize) {
                break; // we have read the batch
            }
            line = br.readLine();
        }

        if (lineList.size() == 0) {
            return null;
        }

        return lineList;
    }

    public void close() throws IOException {
        br.close();
    }

}
