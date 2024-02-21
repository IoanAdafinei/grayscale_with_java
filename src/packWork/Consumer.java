package packWork;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.time.*;

import javax.imageio.ImageIO;

public class Consumer extends Thread {
	private DataOutputStream out;
	
	private BlockingQueue<BufferedImage> imageQueue;

    public Consumer(BlockingQueue<BufferedImage> imageQueue, DataOutputStream out) {
        this.imageQueue = imageQueue;
        this.out = out;
    }

    public void run() {
            while (true) {
            	
            	
            	synchronized (this) {
            	long start = System.currentTimeMillis();
                BufferedImage image = null;
				try {
					image = imageQueue.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Process the image and send it through a pipe
                BufferedImage newImage = processImage(image);
                int width = newImage.getWidth();
                int height = newImage.getHeight();
                int[] newPixels = newImage.getRGB(0, 0, width, height, null, 0, width);
                
                try {
					out.writeInt(width);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                try {
					out.writeInt(height);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                try {
					out.writeInt(newPixels.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//                for(int j = 0; j < 4; j++) {
//                	for(int i = newPixels.length/4*j; i < newPixels.length/4*(j + 1); i++) {
                	for(int i = 0; i < newPixels.length; i++) {
	                	try {
							out.writeInt(newPixels[i]);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	
	                }
//                	System.out.println("Finished sending part " + j + "through pipe");
//                	}
                System.out.println("Finished transmitting through pipe\n");
                this.notify();
                try {
                	long end = System.currentTimeMillis();
                	System.out.println("Sending the image to WriterResult took " + (end-start) + " miliseconds\n");
                	
                	System.out.println("Consumer finished work\n");
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
    }}}
//	public String[] getOutputNames() {
//		return this.outputNames;
//	}
	
    private BufferedImage processImage(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
        long start = System.currentTimeMillis();

        Algorithm newImage = new Algorithm(pixels.length);
        
        System.out.println("Processing image...");

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
        
        long end = System.currentTimeMillis();
    	System.out.println("Processing the image in consumer took " + (end-start) + " miliseconds\n");
    	
        return img;

    }
	
}
