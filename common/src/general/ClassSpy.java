package general;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassSpy {

    private Class<?> c;

    public ClassSpy(Class<?> c) {
        this.c = c;
    }

    public List<Field> getFields() {
        List<Field> fList = new ArrayList<Field>();

        // does not get the private members
        Field[] dummy = c.getFields();
        for (Field f : dummy) {
            fList.add(f);
        }

        // get the private members
        dummy = c.getDeclaredFields();
        for (Field f : dummy) {
            if (fList.contains(f) == false) {
                fList.add(f);
            }
        }

        return fList;
    }

    public List<Method> getMethods() {
        List<Method> mList = new ArrayList<Method>();

        // does not get the private members
        Method[] dummy = c.getMethods();
        for (Method m : dummy) {
            mList.add(m);
        }

        // get the private members
        dummy = c.getDeclaredMethods();
        for (Method m : dummy) {
            if (mList.contains(m) == false) {
                mList.add(m);
            }
        }

        return mList;
    }

    public List<Constructor<?>> getConstructors() {
        List<Constructor<?>> cList = new ArrayList<Constructor<?>>();

        // gets all
        Constructor<?>[] dummy = c.getDeclaredConstructors();
        for (Constructor<?> c : dummy) {
            cList.add(c);
        }

        return cList;
    }

    public Package getPackage() {
        return c.getPackage();
    }

}
