package general;

import java.io.File;
import java.io.PrintStream;
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

    public static <K, V> void printMap(Map<K, V> map, boolean skipNull, PrintStream printStream) {
        for (Entry<K, V> e : map.entrySet()) {
            K k = e.getKey();
            V v = e.getValue();
            if (skipNull == false && v == null) continue;
            printStream.println(k.toString() + " - " + v.toString());
        }
    }
    
    public static <T> void printList(List<T> list, PrintStream printStream) {
        for (T t : list) {
            printStream.println(t.toString());
        }
    }

}
