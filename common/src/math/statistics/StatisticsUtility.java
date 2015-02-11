package math.statistics;

import java.util.HashMap;
import java.util.Map;

/**
 * User: ANUJ Date: 11/14/11 Time: 10:35 PM
 */
public class StatisticsUtility {

    public static Map<Double, Double> getFrequency(double[] data) {
        return getFrequency(data, false);
    }

    public static Map<Double, Double> getFrequency(double[] data, boolean isDataSorted) {
        Map<Double, Double> frequency = new HashMap<Double, Double>();

        if ((data == null) || (data.length == 0)) {
            return frequency;
        }
        if (data.length == 1) {
            frequency.put(data[0], 1.0);
            return frequency;
        }

        if (isDataSorted == false) {
            for (int i = 0; i < data.length; i++) {
                if (frequency.containsKey(data[i])) {
                    frequency.put(data[i], frequency.get(data[i]) + 1);
                } else {
                    frequency.put(data[i], 1.0);
                }

            }
        } else {
            double prev = data[0];
            double count = 1.0;
            for (int i = 1; i < data.length; i++) {
                if (prev == data[i]) {
                    count++;
                } else {
                    frequency.put(prev, count);
                    prev = data[i];
                    count = 1.0;
                }
            }
        }
        return frequency;
    }

    public static Map<Double, Double> getProbability(HashMap<Double, Double> frequencyTable) {
        Map<Double, Double> probability = new HashMap<Double, Double>();
        if ((frequencyTable == null) || (frequencyTable.isEmpty() == true)) {
            return probability;
        }

        // Get total of frequency
        double sum = 0.0;
        for (double value : frequencyTable.values()) {
            sum = sum + value;
        }

        return getProbability(frequencyTable, sum);
    }

    public static HashMap<Double, Double> getProbability(HashMap<Double, Double> frequencyTable, double totalDataCount) {
        HashMap<Double, Double> probability = new HashMap<Double, Double>();
        if ((frequencyTable == null) || (frequencyTable.isEmpty() == true)) {
            return probability;
        }

        for (double key : frequencyTable.keySet()) {
            probability.put(key, frequencyTable.get(key) / totalDataCount);
        }
        return probability;
    }

    public static double getMin(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        double min = Double.MAX_VALUE;
        for (double value : data) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static double getMax(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        double max = Double.MIN_VALUE;
        for (double value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
