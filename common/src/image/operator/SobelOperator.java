package image.operator;

import image.ImageUtility;
import image.SimpleImage;

public class SobelOperator extends SimpleOperator {

    private SimpleImage theta = null;

    public SimpleImage getTheta() {
        return theta;
    }

    public void apply(SimpleImage simpleImage) {

        super.apply(simpleImage);

        theta = simpleImage.copy();

        int h = this.simpleImage.getHeight();
        int w = this.simpleImage.getWidth();

        double[][] g = new double[w][h];

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double[] result = computeGAndTheta(x, y);
                g[x][y] = ImageUtility.getColor((int) result[0], (int) result[1], (int) result[2]);
                this.theta.setPixel(x, y,
                        (int) ImageUtility.getColor((int) result[3], (int) result[4], (int) result[5]));
            }
        }

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                this.simpleImage.setPixel(x, y, (int) g[x][y]);
            }
        }

    }

    private double[] computeGAndTheta(int x, int y) {

        int h = this.simpleImage.getHeight();
        int w = this.simpleImage.getWidth();

        int xm1 = x - 1;
        if (xm1 < 0) xm1 = 0;

        int ym1 = y - 1;
        if (ym1 < 0) ym1 = 0;

        int xp1 = x + 1;
        if (xp1 >= w) xp1 = w - 1;

        int yp1 = y + 1;
        if (yp1 >= h) yp1 = h - 1;

        // x = col, y = row
        int p1x = xm1, p1y = ym1;
        int p2x = x, p2y = ym1;
        int p3x = xp1, p3y = ym1;
        int p4x = xm1, p4y = y;
        int p5x = x, p5y = y;
        int p6x = xp1, p6y = y;
        int p7x = xm1, p7y = yp1;
        int p8x = x, p8y = yp1;
        int p9x = xp1, p9y = yp1;

        int p1 = this.simpleImage.getPixel(p1x, p1y);
        int p2 = this.simpleImage.getPixel(p2x, p2y);
        int p3 = this.simpleImage.getPixel(p3x, p3y);
        int p4 = this.simpleImage.getPixel(p4x, p4y);
        int p5 = this.simpleImage.getPixel(p5x, p5y);
        int p6 = this.simpleImage.getPixel(p6x, p6y);
        int p7 = this.simpleImage.getPixel(p7x, p7y);
        int p8 = this.simpleImage.getPixel(p8x, p8y);
        int p9 = this.simpleImage.getPixel(p9x, p9y);

        double grg =
                computeG(ImageUtility.getRed(p1), ImageUtility.getRed(p2), ImageUtility.getRed(p3),
                        ImageUtility.getRed(p4), ImageUtility.getRed(p5),
                        ImageUtility.getRed(p6), ImageUtility.getRed(p7), ImageUtility.getRed(p8),
                        ImageUtility.getRed(p9));

        double ggg =
                computeG(ImageUtility.getGreen(p1), ImageUtility.getGreen(p2), ImageUtility.getGreen(p3),
                        ImageUtility.getGreen(p4), ImageUtility.getGreen(p5),
                        ImageUtility.getGreen(p6), ImageUtility.getGreen(p7), ImageUtility.getGreen(p8),
                        ImageUtility.getGreen(p9));

        double gbg =
                computeG(ImageUtility.getBlue(p1), ImageUtility.getBlue(p2), ImageUtility.getBlue(p3),
                        ImageUtility.getBlue(p4), ImageUtility.getBlue(p5),
                        ImageUtility.getBlue(p6), ImageUtility.getBlue(p7), ImageUtility.getBlue(p8),
                        ImageUtility.getBlue(p9));

        double grt =
                computeGTheta(ImageUtility.getRed(p1), ImageUtility.getRed(p2), ImageUtility.getRed(p3),
                        ImageUtility.getRed(p4), ImageUtility.getRed(p5),
                        ImageUtility.getRed(p6), ImageUtility.getRed(p7), ImageUtility.getRed(p8),
                        ImageUtility.getRed(p9));

        double ggt =
                computeGTheta(ImageUtility.getGreen(p1), ImageUtility.getGreen(p2), ImageUtility.getGreen(p3),
                        ImageUtility.getGreen(p4), ImageUtility.getGreen(p5),
                        ImageUtility.getGreen(p6), ImageUtility.getGreen(p7), ImageUtility.getGreen(p8),
                        ImageUtility.getGreen(p9));

        double gbt =
                computeGTheta(ImageUtility.getBlue(p1), ImageUtility.getBlue(p2), ImageUtility.getBlue(p3),
                        ImageUtility.getBlue(p4), ImageUtility.getBlue(p5),
                        ImageUtility.getBlue(p6), ImageUtility.getBlue(p7), ImageUtility.getBlue(p8),
                        ImageUtility.getBlue(p9));

        if (grg > 255) grg = 255;
        if (ggg > 255) ggg = 255;
        if (gbg > 255) gbg = 255;

        double[] result = new double[6];
        result[0] = grg;
        result[1] = ggg;
        result[2] = gbg;
        result[3] = normalizeTheta(grt);
        result[4] = normalizeTheta(ggt);
        result[5] = normalizeTheta(gbt);

        return result;
    }

    private double computeG(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9) {
        double gx = p1 + (2 * p2) + p3 - p7 - (2 * p8) - p9;
        double gy = p3 + (2 * p6) + p9 - p1 - (2 * p4) - p7;
        return Math.sqrt((gx * gx) + (gy * gy));
    }

    private double computeGTheta(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9) {
        double gx = p1 + (2 * p2) + p3 - p7 - (2 * p8) - p9;
        double gy = p3 + (2 * p6) + p9 - p1 - (2 * p4) - p7;
        return Math.atan(gy / gx);
    }

    private double normalizeTheta(double theta) {
        theta = Math.abs(theta);

        if (theta >= 0 && theta < 45) {
            return normalizeTheta(theta, 0, 45);
        }
        else if (theta >= 45 && theta < 90) {
            return normalizeTheta(theta, 45, 90);
        }
        else if (theta >= 90 && theta < 135) {
            return normalizeTheta(theta, 90, 135);
        }
        else {
            return 135;
        }
    }

    private double normalizeTheta(double theta, double min, double max) {
        double l = Math.abs(theta - min);
        double r = Math.abs(max - theta);
        if (l < r) return l;
        return r;
    }

}