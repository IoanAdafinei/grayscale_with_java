package packWork;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.time.*;

import javax.imageio.ImageIO;

public class Producer extends Thread {
	private LinkedList<String> inputNames;
    private BlockingQueue<BufferedImage> imageQueue;
    private String outputDirectory = "./src/Output/"; // Directory where output is stored
    private String imageDirectory = "./src/Input/"; // Directory where images are stored
    private String[] outputNames = null;

    public Producer(LinkedList<String> inputNames, BlockingQueue<BufferedImage> imageQueue) {
        this.inputNames = inputNames;
        this.imageQueue = imageQueue;
    }

    public void run() {
    	synchronized (this) {
        try {
        	long start = System.currentTimeMillis();
            for (String imageName : this.inputNames) {
                BufferedImage image = readImage(imageName);
                imageQueue.put(image); // Put the image into the queue
            }
            notify();
            try {
            	long end = System.currentTimeMillis();
            	System.out.println("Sending the image to consumer took " + (end-start) + " miliseconds\n");
            	System.out.println("Producer finished work\n");
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (Exception e) {
            e.printStackTrace();
        }}
    }
    
    private BufferedImage readImage(String imageName) {
        try {
        	long start = System.currentTimeMillis();
            String imagePath = imageDirectory + imageName;
            File file = new File(imagePath);
            long end = System.currentTimeMillis();
            System.out.println("The read of the image took " + (end-start) + " miliseconds\n");
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}


