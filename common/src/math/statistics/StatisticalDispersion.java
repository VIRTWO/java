package math.statistics;

import java.util.Arrays;

/**
 * User: ANUJ Date: 11/19/11 Time: 12:33 PM
 */
public class StatisticalDispersion {

    public static double getRange(double[] data, boolean isSorted) {

        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }

        double[] sortedData = null;
        if (isSorted == true) {
            sortedData = data;
        } else {
            sortedData = data.clone();
            Arrays.sort(sortedData);
        }

        return sortedData[data.length - 1] - data[0];
    }

    public static double getVariance(double[] data) {

        double mean = CentralTendency.getMean(data);
        if (mean == Double.NaN) {
            return Double.NaN;
        }

        double sumOfSqr = 0.0;
        double variance = 0.0;

        for (double aData : data) {
            sumOfSqr += Math.pow(aData - mean, 2.0);
            variance = (sumOfSqr - (Math.pow(sumOfSqr, 2) / data.length)) / (data.length - 1);
        }
        variance = sumOfSqr / (data.length - 1);
        return variance;
    }

    public static double getStandardDeviation(double[] data) {
        double var = getVariance(data);
        if (var == Double.NaN) {
            return var;
        }
        return Math.sqrt(var);
    }

    public static double getInterdecileRange(double[] data, boolean isSorted) {
        double[] sortedData = null;
        if (isSorted == true) {
            sortedData = data;
        } else {
            sortedData = data.clone();
            Arrays.sort(sortedData);
        }
        return Quantile.getDecile(sortedData, true, 1) - Quantile.getDecile(sortedData, true, 9);
    }

    public static double getMeanAbsoluteDeviation(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        double mean = CentralTendency.getMean(data);
        if (mean == Double.NaN) {
            return Double.NaN;
        }

        double[] meanDiff = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            meanDiff[i] = Math.abs(data[i] - mean);
        }

        return CentralTendency.getMedian(meanDiff, false);
    }

    public static double getMedianAbsoluteDeviation(double[] data, boolean isSorted) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        double median = CentralTendency.getMedian(data, isSorted);
        if (median == Double.NaN) {
            return Double.NaN;
        }

        double[] medianDiff = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            medianDiff[i] = Math.abs(data[i] - median);
        }

        return CentralTendency.getMedian(medianDiff, false);
    }

    public static double getInterquartileRange(double[] data, boolean isSorted) {
        double firstQuartile = Quantile.getQuartile(data, isSorted, 1);
        double thirdQuartile = Quantile.getQuartile(data, isSorted, 3);
        return thirdQuartile - firstQuartile;
    }

    public static double getCoefficientOfVariation(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        double sd = getStandardDeviation(data);
        double mn = CentralTendency.getMean(data);
        if ((mn == 0.0) || (mn == Double.NaN) || (sd == Double.NaN)) {
            return Double.NaN;
        }
        return sd / mn;
    }

    public static double getQuartileCoefficientOfDispersion(double[] data, boolean isSorted) {
        double firstQuartile = Quantile.getQuartile(data, isSorted, 1);
        double thirdQuartile = Quantile.getQuartile(data, isSorted, 3);
        double sum = thirdQuartile + firstQuartile;
        double diff = thirdQuartile - firstQuartile;
        if ((sum == Double.NaN) || (diff == Double.NaN) || (sum == 0.0)) {
            return Double.NaN;
        }
        return diff / sum;
    }

    public static double getIndexOfDispersion(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }
        double vr = getVariance(data);
        double mn = CentralTendency.getMean(data);
        if ((mn == 0.0) || (mn == Double.NaN) || (vr == Double.NaN)) {
            return Double.NaN;
        }
        return vr / mn;
    }

    public static double getMeanDifference(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }

        double diffSum = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                diffSum = diffSum + Math.abs(data[i] - data[j]);
            }
        }

        return diffSum / (data.length * (data.length - 1));
    }

    public static double getRelativeMeanDifference(double[] data) {
        if ((data == null) || (data.length < 1)) {
            return Double.NaN;
        }

        double diffSum = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                diffSum = diffSum + Math.abs(data[i] - data[j]);
            }
        }

        double mean = CentralTendency.getMean(data);
        if ((mean == Double.NaN) || (mean == 0.0)) {
            return Double.NaN;
        }

        return diffSum / mean;
    }

}
