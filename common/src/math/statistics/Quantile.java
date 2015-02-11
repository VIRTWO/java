package math.statistics;

import java.util.Arrays;

/**
 * User: ANUJ Date: 11/15/11 Time: 12:25 AM
 */
public class Quantile {

    public static double getQuartile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 4d, rank);
    }

    public static double getTercile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 3d, rank);
    }

    public static double getQuintile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 5d, rank);
    }

    public static double getSextile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 6d, rank);
    }

    public static double getDecile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 10d, rank);
    }

    public static double getDuoDecile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 12d, rank);
    }

    public static double getVigintile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 20d, rank);
    }

    public static double getPercentile(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 100d, rank);
    }

    public static double getPermille(double[] data, boolean isSorted, double rank) {
        return get(data, isSorted, 1000d, rank);
    }

    /**
     * @param data : array of data points 
     * @param isSorted : indicates if data is sorted
     * @param whichQuantile : quantile to be calculated e.g. percentile, quartile
     * @param rank : e.g. 50th - percentile
     * @return
     */
    public static double get(double[] data, boolean isSorted, double whichQuantile, double rank) {
        double quantile = Double.NaN;
        if (data == null || rank > whichQuantile || whichQuantile < 0 || rank < 0) {
            return quantile;
        }

        if(rank == 0) {
            // this is not a universally accepted calculation
            return data[0];
        }
        
        if(rank == whichQuantile) {
         // this is not a universally accepted calculation
            return data[data.length - 1];
        }
        
        double[] sortedData = null;

        if (isSorted == true) {
            sortedData = data;
        } else {
            sortedData = data.clone();
            Arrays.sort(sortedData);
        }

        quantile = (data.length * rank) / whichQuantile;

        if (quantile != Math.round(quantile)) {
            return sortedData[(int) (Math.ceil(quantile) - 1)];
        } else {
            int quntileIndex = (new Double(quantile)).intValue();
            return ((sortedData[quntileIndex - 1] + sortedData[quntileIndex]) / 2);
        }

    }

}
