package image;

public class ImageUtility {

    public static int getColor(int r, int g, int b) {
        return (r << 16) + (g << 8) + (b);
    }

    public static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int getBlue(int color) {
        return (color >> 0) & 0xFF;
    }

}