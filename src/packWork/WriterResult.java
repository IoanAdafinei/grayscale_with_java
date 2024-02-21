package packWork;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;

public class WriterResult extends Thread {
	private BufferedImage img;
    private String outputDirectory = "./src/Output/"; // Directory where output is stored
	private String[] outputNames = null;
	private String[] inputNames = null;
	private DataInputStream in;
	
	public WriterResult(DataInputStream in, String[] outputNames, String[] inputNames) {
		this.outputNames = outputNames;
		this.inputNames = inputNames;
		this.in = in;
		}
	
	public void run(){
		long start = System.currentTimeMillis();
    	
		File file = new File("./src/Input/" + inputNames[0]);//de modificat cu imaginea intitiala
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = 0;
    	try {
			width = in.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int height = 0;
        try {
			height = in.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int length = 0;
        try {
			length = in.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int[] pixels = new int[length];
        
        for(int i = 0; i < length; i++) {
        	try {
				pixels[i] = in.readInt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
        	if(i == length - 1) {
        		try {
        			in.close();
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        }
        
        long end = System.currentTimeMillis();
    	System.out.println("Recieving the info from the consumer took " + (end-start) + " miliseconds\n");
    	
    	long start1 = System.currentTimeMillis();
    	
        System.out.println("WriterResult recieved info\n");
		
		img.setRGB(0, 0, width, height, pixels, 0, width);
	
	    for (String elem : outputNames) {
	        // Save the processed image to the output directory
	        String outputFilePath = outputDirectory + elem;
	        try {
	            File outputfile = new File(outputFilePath);
	            ImageIO.write(img, "bmp", outputfile);
	            System.out.println("Processed image saved to: " + outputFilePath);
	            long end1 = System.currentTimeMillis();
            	System.out.println("Saving the image to memory took " + (end1-start1) + " miliseconds\n");
            	
	            System.exit(0);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
}
