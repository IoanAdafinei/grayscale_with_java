package packWork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class ProducerConsumer extends Algorithm {
    private LinkedList<BufferedImage> imageList = new LinkedList<>();
    private int capacity = 2;
    private String outputDirectory = "./src/Output/"; // Directory where output is stored
    private String imageDirectory = "./src/Input/"; // Directory where images are stored
    private String[] outputNames = null;

    public ProducerConsumer() {
        this.outputNames = null;
    }

    public ProducerConsumer(String[] output) {
        this.outputNames = output;
    }

    public void produce(String[] imageNames) throws InterruptedException {
        for (String imageName : imageNames) {
            synchronized (this) {
                while (imageList.size() == capacity)
                    wait();

                BufferedImage image = readImage(imageName); // Read image based on name

                System.out.println("Producer produced an image: " + imageName);

                imageList.add(image);
                notify();
                Thread.sleep(1000);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (imageList.size() == 0)
                    wait();

                BufferedImage img = imageList.removeFirst();

                System.out.println("Consumer consumed an image");

                processImage(img);

                // int width = img.getWidth();
                // int height = img.getHeight();
                // int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
                // int segmentSize = pixels.length;

                // for (int i = 0; i < 4; i++) {
                // int startIndex = i * segmentSize;
                // int endIndex = (i + 1) * segmentSize;

                // int[] segment = Arrays.copyOfRange(pixels, startIndex, endIndex);
                // }

                notify();
                Thread.sleep(1000);
            }
        }
    }

    private BufferedImage readImage(String imageName) {
        try {
            String imagePath = imageDirectory + imageName;
            File file = new File(imagePath);
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // private BufferedImage processImage(BufferedImage img) {

    // // !!!!!!!!!!!!!!!!!!!!!!!!
    // // pot sa o procesez direct aici fara probleme
    // // !!!!!!!!!!!!!!!!!!!!!!!!!!

    // // get image's width and height
    // int width = img.getWidth();
    // int height = img.getHeight();
    // int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
    // // convert to grayscale

    // Algorithm newImage = new Algorithm(pixels.length);

    // for (int i = 0; i < pixels.length; i++) {

    // // Here i denotes the index of array of pixels
    // // for modifying the pixel value.
    // int p = pixels[i];

    // int a = (p >> 24) & 0xff;
    // int r = (p >> 16) & 0xff;
    // int g = (p >> 8) & 0xff;
    // int b = p & 0xff;

    // newImage.seta(a, i);
    // newImage.setr(r, i);
    // newImage.setg(g, i);
    // newImage.setb(b, i);
    // }
    // pixels = newImage.executeAlgorithm(pixels.length);

    // img.setRGB(0, 0, width, height, pixels, 0, width);

    // System.out.println("Processing image...");

    // return img;
    // }

    private void processImage(BufferedImage img) {

        // !!!!!!!!!!!!!!!!!!!!!!!!
        // pot sa o procesez direct aici fara probleme
        // !!!!!!!!!!!!!!!!!!!!!!!!!!

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
        // convert to grayscale

        Algorithm newImage = new Algorithm(pixels.length);

        for (int i = 0; i < pixels.length; i++) {

            // Here i denotes the index of array of pixels
            // for modifying the pixel value.
            int p = pixels[i];

            int a = (p >> 24) & 0xff;
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;

            newImage.seta(a, i);
            newImage.setr(r, i);
            newImage.setg(g, i);
            newImage.setb(b, i);
        }
        pixels = newImage.executeAlgorithm(pixels.length);

        img.setRGB(0, 0, width, height, pixels, 0, width);

        System.out.println("Processing image...");

        for (String elem : outputNames) {
            // Save the processed image to the output directory
            String outputFilePath = outputDirectory + elem; // Change file format as needed
            try {
                File outputfile = new File(outputFilePath);
                ImageIO.write(img, "bmp", outputfile);
                System.out.println("Processed image saved to: " + outputFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
