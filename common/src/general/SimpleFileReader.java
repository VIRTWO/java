package general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleFileReader {

	private BufferedReader br = null;

	public SimpleFileReader(String path) throws IOException {
		java.io.FileReader fr = new FileReader(path);
		br = new BufferedReader(fr);
	}

	public String readLine() throws IOException {
		return br.readLine();
	}

	public List<String> readAllLine() throws IOException {
		String dummy = null;
		List<String> lineList = new ArrayList<String>();

		while ((dummy = readLine()) != null) {
			lineList.add(dummy);
		}

		return lineList;
	}

	public void close() throws IOException {
		br.close();
	}

	public static String readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] fileByte = new byte[(int) file.length()];
		fis.read(fileByte);
		fis.close();
		return new String(fileByte);
	}

	public static String readFile(String filePath) throws IOException {
		File f = new File(filePath);
		return readFile(f);
	}

}
