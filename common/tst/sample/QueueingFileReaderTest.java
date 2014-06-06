package sample;

import io.QueueingFileReader;
import io.QueueingFileReader.DataBlock;

import java.io.File;
import java.io.IOException;

public class QueueingFileReaderTest {

    public static class Consumer implements Runnable {

        private final QueueingFileReader queueingFileReader;

        public Consumer(QueueingFileReader queueingFileReader) {
            this.queueingFileReader = queueingFileReader;
        }

        @Override
        public void run() {
            try {
                DataBlock data = null;
                long i = 0;
                while ((data = queueingFileReader.getDataBlock()) != DataBlock.END) {
                    if (((++i) % 100000) == 0) {
                        System.out.println(i + ": " + Thread.currentThread().getName());
                        System.out.println(new String(data.getBytes()));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumer " + Thread.currentThread().getName() + " is done.");
        }

    }

    public static void main(String[] args) throws IOException {

        QueueingFileReader qfr = new QueueingFileReader(new File("/tmp/tmp.file"), '\n');
        qfr.start();

        for (int i = 0; i < 2; i++) {
            Thread a = new Thread(new Consumer(qfr), "a" + i);
            a.start();
        }

        System.out.println("Done!");
    }

}
