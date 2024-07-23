package ru.nsu.gunko;

import java.util.*;

public class Statist {
    private final Vector<Float> intValues;
    private final Vector<Double> floValues;
    // min, max, sum, avg, count

    private final Vector<Integer> strValues;
    // min and max len

    public Statist() {
        intValues = new Vector<>(5);
        intValues.addAll(Collections.nCopies(5, 0.0f));
        intValues.set(0, Float.MAX_VALUE);

        floValues = new Vector<>(5);
        floValues.addAll(Collections.nCopies(5, 0.0d));
        floValues.set(0, Double.MAX_VALUE);

        strValues = new Vector<>(3);
        strValues.addAll(Collections.nCopies(3, 0));
        strValues.set(0, Integer.MAX_VALUE);
    }

    public void intAnalyse(float value) {
        intValues.set(0, Math.min(intValues.get(0), value));
        intValues.set(1, Math.max(intValues.get(1), value));

        intValues.set(2, intValues.get(2) + value);
        intValues.set(4, intValues.get(4) + 1);
        intValues.set(3, intValues.get(2) / intValues.get(4));
    }

    public void floAnalyse(double value) {
        floValues.set(0, Math.min(floValues.get(0), value));
        floValues.set(1, Math.max(floValues.get(1), value));

        floValues.set(2, floValues.get(2) + value);
        floValues.set(4, floValues.get(4) + 1);
        floValues.set(3, floValues.get(2) / floValues.get(4));
    }

    public void strAnalyse(String value) {
        strValues.set(0, Math.min(strValues.get(0), value.length()));
        strValues.set(1, Math.max(strValues.get(1), value.length()));
        strValues.set(2, strValues.get(2) + 1);
    }

    public void printStat() {
        System.out.println("Statistics");
        System.out.println("--------------------");

        if (intValues.get(4) == 0) {
            System.out.println("No statistics for integers");
        } else {
            System.out.println("Integers:");
            System.out.println("Min: " + intValues.get(0) + ", max: " + intValues.get(1));
            System.out.println("Sum: " + intValues.get(2) + ", avg: " + intValues.get(3));
        }
        System.out.println("--------------------");

        if (floValues.get(4) == 0) {
            System.out.println("No statistics for floats");
        } else {
            System.out.println("Floats:");
            System.out.println("Min: " + floValues.get(0) + ", max: " + floValues.get(1));
            System.out.println("Sum: " + floValues.get(2) + ", avg: " + floValues.get(3));
        }
        System.out.println("--------------------");

        if (strValues.get(2) == 0) {
            System.out.println("No statistics for strings");
        } else {
            System.out.println("Strings:");
            System.out.println("Min length: " + strValues.get(0));
            System.out.println("Max length: " + strValues.get(1));

        }
        System.out.println("--------------------");
    }
}
