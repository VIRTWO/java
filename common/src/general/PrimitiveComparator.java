package general;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrimitiveComparator {

    public static class FieldComparisionResult {
        public int FIELD_COUNT_DIFFERENCE = 0;
        public List<Field> LEFT_EXTRA_FIELDS = new ArrayList<Field>();
        public List<Field> RIGHT_EXTRA_FIELDS = new ArrayList<Field>();
        public Map<Field, String> UNEQUAL_VALUES = new HashMap<Field, String>();
        public List<Field> SKIPPED = new ArrayList<Field>();

        public void print(PrintStream printStream) {
            printStream.println("Extra fields in left object:");
            for (Field f : LEFT_EXTRA_FIELDS) {
                printStream.println(f);
            }
            printStream.println();

            printStream.println("Extra fields in right object:");
            for (Field f : RIGHT_EXTRA_FIELDS) {
                printStream.println(f);
            }
            printStream.println();

            printStream.println("Fields with unequal values:");
            for (Map.Entry<Field, String> e : UNEQUAL_VALUES.entrySet()) {
                printStream.println(e.getKey() + " - " + e.getValue());
            }
            printStream.println();

            printStream.println("Fields skipped while matching values:");
            for (Field s : SKIPPED) {
                printStream.println(s);
            }
            printStream.println();
        }
    }

    private Set<Class<?>> supportedTypes = new HashSet<Class<?>>(Arrays.asList(
            byte.class, short.class, int.class, long.class, float.class,
            double.class, char.class, boolean.class, String.class, Byte.class,
            Short.class, Integer.class, Long.class, Float.class, Double.class,
            Character.class, Boolean.class

            ));

    private boolean isSupportedFieldType(Class<?> c) {
        return supportedTypes.contains(c);
    }

    public FieldComparisionResult compareFieldValues(Object objLeft,
            Object objRight) throws IllegalArgumentException,
            IllegalAccessException {
        FieldComparisionResult result = new FieldComparisionResult();

        ClassSpy cpLeft = new ClassSpy(objLeft.getClass());
        ClassSpy cpRight = new ClassSpy(objRight.getClass());

        List<Field> fListLeft = cpLeft.getFields();
        List<Field> fListRight = cpRight.getFields();

        // compare field size
        if (fListLeft.size() != fListRight.size()) {
            result.FIELD_COUNT_DIFFERENCE = fListLeft.size()
                    - fListRight.size();
        }

        Map<String, Field> fNameMapLeft = new HashMap<String, Field>();
        Map<String, Field> fNameMapRight = new HashMap<String, Field>();
        for (Field f : fListLeft) {
            f.setAccessible(true); // we don't care about modifier
            fNameMapLeft.put(f.getName(), f);
        }
        for (Field f : fListRight) {
            f.setAccessible(true);
            fNameMapRight.put(f.getName(), f);
        }

        // find Left extra fields
        for (Map.Entry<String, Field> e : fNameMapLeft.entrySet()) {
            if (fNameMapRight.containsKey(e.getKey())) {
                // similarly named field exists
                if (e.getValue().getType()
                        .equals(fNameMapRight.get(e.getKey()).getType())) {
                    // they are same type as well
                    continue;
                }
            }
            // they mismatched, so add it to left list
            result.LEFT_EXTRA_FIELDS.add(e.getValue());
        }

        // find Right extra fields
        for (Map.Entry<String, Field> e : fNameMapRight.entrySet()) {
            if (fNameMapLeft.containsKey(e.getKey())) {
                if (e.getValue().getType()
                        .equals(fNameMapLeft.get(e.getKey()).getType())) {
                    // we will match the values of the equal elements here
                    // if they are supported, else we will add them to skip list
                    if (isSupportedFieldType(e.getValue().getType()) == false) {
                        // not supported type
                        result.SKIPPED.add(fNameMapLeft.get(e.getKey()));
                        result.SKIPPED.add(e.getValue());
                        continue;
                    }
                    // implement the toString in class to support other types or
                    // inject special
                    // value compare function for type
                    String rightValue = e.getValue().get(objRight).toString();
                    String leftValue = fNameMapLeft.get(e.getKey())
                            .get(objLeft).toString();
                    if (leftValue.equals(rightValue) == false) {
                        // values are not same
                        result.UNEQUAL_VALUES.put(fNameMapLeft.get(e.getKey()), rightValue);
                        result.UNEQUAL_VALUES.put(e.getValue(), rightValue);
                    }
                    continue;
                }
            }
            result.RIGHT_EXTRA_FIELDS.add(e.getValue());
        }

        return result;
    }

}
