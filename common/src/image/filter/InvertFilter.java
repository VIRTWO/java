package image.filter;

import image.SimpleImage;

public class InvertFilter extends SimpleFilter {

    public void apply(SimpleImage simpleImage) {

        super.apply(simpleImage);

        int h = this.simpleImage.getHeight();
        int w = this.simpleImage.getWidth();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                int color = this.simpleImage.getPixel(x, y);

                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;

                int ir = 255 - r;
                int ig = 255 - g;
                int ib = 255 - b;

                this.simpleImage.setPixel(x, y, ir, ig, ib);
            }
        }
    }

}