package sample;

import image.SimpleImage;
import image.operator.SobelOperator;

import java.io.IOException;

public class ImageTest {

    public static void main(String[] args) {

        try {
            SimpleImage simpleImage = new SimpleImage("Test.png");

            // simpleImage.applySimpleMask(new InvertFilter());
            //
            // Canny algorithm - noise reduction
            // double[][] kernel = {
            // {2,4,5,4,2},
            // {4,9,12,9,4},
            // {5,12,15,12,5},
            // {4,9,12,9,4},
            // {2,4,5,4,2}
            // };

            // Gaussian2DFilter gaussian2DFilter = new Gaussian2DFilter(5, 5, kernel, 159, SimpleMask.Mode.WRAP);
            // gaussian 2D Filter
            // simpleImage.applySimpleMask(gaussian2DFilter, 1);

            SobelOperator sobelOperator = new SobelOperator();
            // apply sobel operator
            simpleImage.applySimpleMask(sobelOperator);
            sobelOperator.getTheta().save("Theta.png", "png");

            simpleImage.save("C:\\Users\\anujpr\\Desktop\\ImageTest\\Out.png", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done!");

    }

}