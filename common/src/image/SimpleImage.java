package image;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;

public class SimpleImage {

    private BufferedImage image = null;
    private int height = 0, width = 0;

    public SimpleImage copy() {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);

        BufferedImage biCopy = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

        SimpleImage copy = new SimpleImage(biCopy);
        return copy;
    }

    public SimpleImage(BufferedImage bufferedImage) {
        this.image = bufferedImage;
        height = image.getHeight();
        width = image.getWidth();
    }

    public SimpleImage(String imagePath) throws IOException {
        this(ImageIO.read(new File(imagePath)));
    }

    public void save(String outputPath, String format) throws IOException {
        ImageIO.write(image, format, new File(outputPath));
    }

    public void setPixel(int x, int y, int rgb) {
        image.setRGB(x, y, rgb);
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        image.setRGB(x, y, ImageUtility.getColor(r, g, b));
    }

    public int getPixel(int x, int y) {
        return image.getRGB(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void applySimpleMask(SimpleMask mask) {
        mask.apply(this);
    }

    public void applySimpleMask(SimpleMask mask, int numTimeToRepeat) {
        while (numTimeToRepeat > 0) {
            mask.apply(this);
            numTimeToRepeat--;
        }
    }

}