package image.filter;

import image.SimpleImage;

public class Gaussian2DFilter extends SimpleFilter {

    private int kernelRow = 0;
    private int kernelCol = 0;
    private double[][] kernel = null;
    private double divisor = 1;
    private Mode mode = null;

    public Gaussian2DFilter(int kernelRow, int kernelCol, double[][] kernel, double divisor, Mode mode) {
        this.kernelRow = kernelRow;
        this.kernelCol = kernelCol;
        this.kernel = kernel;
        this.divisor = divisor;
        this.mode = mode;
    }

    private int getCoordinate(int c, int kc, int l) {
        if ((c + kc) < l) {
            return (c + kc);
        }

        if (mode == Mode.WRAP) {
            return ((c + kc) % c);
        }
        else if (mode == Mode.ZERO) {
            return 0;
        }
        else if (mode == Mode.ZERO) {
            return c - 1;
        }
        else {
            return (c + kc);
        }
    }

    public void apply(SimpleImage simpleImage) {

        super.apply(simpleImage);

        int h = this.simpleImage.getHeight();
        int w = this.simpleImage.getWidth();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                double sr = 0, sg = 0, sb = 0;

                for (int ky = 0; ky < kernelRow; ky++) {
                    for (int kx = 0; kx < kernelCol; kx++) {
                        double k = kernel[ky][kx];

                        int xp = getCoordinate(x, kx, w);
                        int yp = getCoordinate(y, ky, h);

                        int color = this.simpleImage.getPixel(xp, yp);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;

                        sr = sr + (r * k);
                        sg = sg + (g * k);
                        sb = sb + (b * k);
                    }
                }

                sr = sr / divisor;
                sg = sg / divisor;
                sb = sb / divisor;

                this.simpleImage.setPixel(x, y, (int) sr, (int) sg, (int) sb);

            }
        }

    }

}