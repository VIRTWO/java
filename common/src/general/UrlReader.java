package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReader {

    public static String read(String url) throws IOException {

        InputStreamReader urlIStream = new InputStreamReader(
                (new URL(url)).openStream());
        BufferedReader urlBr = new BufferedReader(urlIStream);

        StringBuffer urlContent = new StringBuffer();
        String urlContentChunk = null;
        while ((urlContentChunk = urlBr.readLine()) != null) {
            urlContent.append(urlContentChunk);
        }

        urlBr.close();

        return urlContent.toString();
    }
}
