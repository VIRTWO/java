package io;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueingFileReader {

	public static class DataBlock {

		public static final DataBlock END = new DataBlock(null);

		private final byte[] data;

		public DataBlock(byte[] data) {
			this.data = data;
		}

		public byte[] getBytes() {
			return data;
		}
	}

	private int queueSize = 1024;
	private int chunkSize = 1024;
	private BlockingQueue<DataBlock> dataBlockQueue = new ArrayBlockingQueue<DataBlock>(queueSize);
	private boolean started = false;
	private final Reader reader;

	public QueueingFileReader(File file) {
		this(file, null);
	}

	public QueueingFileReader(File file, Character blockDeliminator) {
		this(file, blockDeliminator, 1024, 1024);
	}
	
	public QueueingFileReader(File file, Character blockDeliminator, int queueSize, int chunkSize) {
		reader = new Reader(file, blockDeliminator);
		this.queueSize = queueSize;
		this.chunkSize = chunkSize;
	}

	public synchronized void start() throws IOException {
		if(started) {
			throw new IllegalStateException("QueueingFileReader cannot be started twice. Create a new QueueingFileReader.");
		}
		Thread readerThread = new Thread(reader);
		readerThread.start();
		started = true;
	}
	
	public DataBlock getDataBlock(long timeout, TimeUnit timeUnit) throws InterruptedException {
		DataBlock data = dataBlockQueue.poll(timeout, timeUnit);
		if (data == DataBlock.END) {
			signalDone();
		}
		return data;
	}

	public DataBlock getDataBlock() throws InterruptedException {
		DataBlock data = dataBlockQueue.take();
		if (data == DataBlock.END) {
			signalDone();
		}
		return data;
	}
	
	
	private void signalDone() {
		try {
			dataBlockQueue.put(DataBlock.END);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class Reader implements Runnable {

		private final File file;
		private MappedByteBuffer buffer = null;
		private final Character deliminator;
		private byte[] oldData = null;

		public Reader(File file, Character deliminator) {
			this.file = file;
			this.deliminator = deliminator;
		}

		@Override
		public synchronized void run() {
			try {
				FileChannel fc = FileChannel.open(file.toPath(),
						StandardOpenOption.READ);
				buffer = fc.map(MapMode.READ_ONLY, 0, fc.size());
				byte[] dataChunk = null;
				while (buffer.hasRemaining()) {
					dataChunk = new byte[Math.min(chunkSize,
							buffer.remaining())];
					buffer.get(dataChunk);
					try {
						addToQueue(dataChunk);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				fc.close();
				signalDone();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private int getLastIndexOfDeliminator(byte[] data, char value) {
			for (int i = data.length - 1; i >= 0; i--) {
				if (data[i] == value) {
					return i;
				}
			}
			return -1;
		}

		private void addToQueue(byte[] dataChunk) throws InterruptedException {
			if (deliminator == null) {
				dataBlockQueue.put(new DataBlock(dataChunk));
			} else {
				splitAndAddToQueue(dataChunk);
			}
		}

		private void splitAndAddToQueue(byte[] newData)
				throws InterruptedException {

			byte[] data = null;
			if (oldData != null && oldData.length > 0) {
				// form complete data
				data = new byte[oldData.length + newData.length];
				System.arraycopy(oldData, 0, data, 0, oldData.length);
				System.arraycopy(newData, 0, data, oldData.length,
						newData.length);
			} else {
				data = newData;
			}

			// get index of last deliminator
			int dIndex = getLastIndexOfDeliminator(data, deliminator);

			if (dIndex != -1) {
				byte[] dataToQueue = Arrays.copyOf(data, dIndex + 1);
				// update old data
				oldData = Arrays.copyOfRange(data, dIndex + 1, data.length);
				// queue data
				dataBlockQueue.put(new DataBlock(dataToQueue));
			} else {
				// deliminator not found so we make this as oldData
				oldData = data;
			}

		}

	}

}
