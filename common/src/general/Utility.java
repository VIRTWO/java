package general;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utility {

    public static List<File> getFilesInDirectory(String dirPath) {

        File f = new File(dirPath + "/");
        File[] content = f.listFiles();
        List<File> fileList = new ArrayList<File>();
        for (File file : content) {
            if (file.isFile() == true) {
                fileList.add(file);
            }
        }

        return fileList;
    }

    public static void printMap(Map<String, String> map, boolean skipNull) {
        for (Entry<String, String> e : map.entrySet()) {
            String k = e.getKey(), v = e.getValue();
            if (skipNull == false && v == null) continue;
            System.out.println(k + " - " + v);
        }
    }

}
