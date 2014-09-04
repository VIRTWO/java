package sample;

import general.PrimitiveComparator;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveComparatorTest {

    @SuppressWarnings("unused")
    public static class A {
        private String a = "SomeString";
        private String b = "SomeString2";

        private int x = 0;

        private double y = 0;

        private List<String> z = new ArrayList<String>();
    }

    @SuppressWarnings("unused")
    public static class B {

        private String a = "SomeString";
        private String b = "SomeString3";

        private int x = 1;

        private int y = 0;

        private List<String> z = new ArrayList<String>();
    }

    public static void main(String[] args) throws Exception {
        PrimitiveComparator pc = new PrimitiveComparator();
        pc.compareFieldValues(new A(), new B()).print(System.out);
    }
}
