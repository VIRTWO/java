package general;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

public class Dumper {

    public static <K, V> void dump(Map<K, V> map, boolean skipNull, PrintStream printStream) {
        for (Entry<K, V> e : map.entrySet()) {
            K k = e.getKey();
            V v = e.getValue();
            if (skipNull == false && v == null) continue;
            printStream.println(k.toString() + " - " + v.toString());
        }
    }
    
    public static <K, V> void dump(Map<K, V> map, boolean skipNull) {
        dump(map, skipNull, System.out);
    }
    
    public static <T> void dump(Collection<T> collection, PrintStream printStream) {
        for (T t : collection) {
            printStream.println(t.toString());
        }
    }
    
    public static <T> void dump(Collection<T> collection) {
        dump(collection, System.out);
    }

}
