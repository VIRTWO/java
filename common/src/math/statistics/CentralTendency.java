package math.statistics;

import java.util.Arrays;
import java.util.Map;

/**
 * User: ANUJ Date: 11/19/11 Time: 12:14 PM
 */
public class CentralTendency {

    public static double getMean(double[] data) {

        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }

        double average = 0.0;
        for (int i = 0; i < data.length; i++) {
            average += data[i];
        }
        average = average / data.length;
        return average;
    }

    public static double getMedian(double[] data, boolean isSorted) {
        return Quantile.get(data, isSorted, 4d, 2d);
    }

    public static double getMode(double[] data) {
        Map<Double, Double> frequency = StatisticsUtility.getFrequency(data);
        return getMode(frequency);
    }

    public static double getMode(Map<Double, Double> frequencyTable) {
        double value = Double.NaN;
        double currentMax = Double.MIN_VALUE;

        if ((frequencyTable == null) || (frequencyTable.size() < 1)) {
            return value;
        }

        for (double key : frequencyTable.keySet()) {
            double currentFrequency = frequencyTable.get(key);
            if (currentFrequency > currentMax) {
                currentMax = currentFrequency;
                value = key;
            }
        }

        return value;
    }

    public static double getGeometricMean(double[] data) {
        double product = 1;
        for (double value : data) {
            product = product * value;
        }

        return Math.pow(product, (1.0 / data.length));
    }

    public static double getHarmonicMean(double[] data) {
        double zeroCount = 0;
        double sumOfReciprocal = 0;
        for (double value : data) {
            if ((value != 0.0) && (value != Double.NaN)) {
                sumOfReciprocal = sumOfReciprocal + (1.0 / value);
            } else {
                zeroCount++;
            }
        }

        double harmonicMean = Double.NaN;
        if ((sumOfReciprocal != 0.0) && (sumOfReciprocal != Double.NaN)) {
            harmonicMean = ((data.length - zeroCount) / (sumOfReciprocal));
        }
        return harmonicMean;
    }

    public static double getMidRange(double[] data, boolean isSorted) {
        double midrange = Double.NaN;
        if (isSorted == true) {
            midrange = (data[0] + data[data.length - 1]) / 2.0;
        } else {
            midrange = (StatisticsUtility.getMax(data) + StatisticsUtility.getMin(data)) / 2.0;
        }
        return midrange;
    }

    public static double getMidHinge(double[] data, boolean isSorted) {
        double firstQuartile = Quantile.getQuartile(data, isSorted, 1);
        double thirdQuartile = Quantile.getQuartile(data, isSorted, 3);
        return (firstQuartile + thirdQuartile) / 2.0;
    }

    public static double getTriMean(double[] data, boolean isSorted) {
        double median = getMedian(data, isSorted);
        double midHinge = getMidHinge(data, isSorted);
        return (median + midHinge) / 2.0;
    }

    public static double getWinsorizedMean(double[] data, boolean isSorted, double percentage) {
        if (percentage > 50) {
            percentage = percentage % 50;
        }
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        if (data.length == 1) {
            return data[0];
        }
        if (data.length == 2) {
            return (data[0] + data[1]) / 2.0;
        }

        int numToReplace = (int) ((data.length * percentage) / 100);

        double[] sortedData = data.clone();
        if (isSorted == false) {
            Arrays.sort(sortedData);
        }

        double lowerValue = data[data.length - 1];
        for (int i = 0; i < numToReplace; i++) {
            sortedData[i] = lowerValue;
        }

        double higherValue = data[data.length - numToReplace - 1];
        for (int i = (data.length - numToReplace); i < data.length; i++) {
            sortedData[i] = higherValue;
        }

        return getMean(sortedData);
    }

    public static double getTruncatedMean(double[] data, boolean isSorted, double percentage) {
        if (percentage > 50) {
            percentage = percentage % 50;
        }
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        if (data.length == 1) {
            return data[0];
        }
        if (data.length == 2) {
            return (data[0] + data[1]) / 2.0;
        }

        int numToTruncate = (int) ((data.length * percentage) / 100);

        double[] sortedData = data.clone();
        if (isSorted == false) {
            Arrays.sort(sortedData);
        }

        double[] truncatedData = Arrays.copyOfRange(sortedData, numToTruncate, data.length - numToTruncate);

        return getMean(truncatedData);
    }

    public static double getWeightedMean(double[] data, double[] weight) {
        if ((data == null) || (weight == null) || (data.length < 0) || (weight.length < 0)) {
            return Double.NaN;
        }
        if (data.length != weight.length) {
            throw new IllegalArgumentException("Weight not provided from all data.");
        }

        double numerator = 0, denominator = 0;
        for (int i = 0; i < data.length; i++) {
            numerator = numerator + (data[i] * weight[i]);
            denominator = denominator + weight[i];
        }
        if (denominator == 0.0) {
            return Double.NaN;
        }
        return numerator / denominator;
    }

}
