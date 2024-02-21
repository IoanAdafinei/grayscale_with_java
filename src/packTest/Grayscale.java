package packTest;

import packWork.*;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

public class Grayscale {
	public static void main(String args[]) throws InterruptedException, IOException {

		int queueCapacity = 1; // Adjust the capacity based on your requirements
        BlockingQueue<BufferedImage> imageQueue = new ArrayBlockingQueue<>(queueCapacity);
        PipedOutputStream pipeOutConsumer = new PipedOutputStream();
		PipedInputStream pipeInWrite = new PipedInputStream(pipeOutConsumer);
		DataOutputStream outConsumer = new DataOutputStream(pipeOutConsumer);
		DataInputStream inWrite = new DataInputStream(pipeInWrite);
		
		if (args.length != 2) {
			System.out.println("Missing the required params");
			System.exit(0);
		}

		int l = args.length;

		String aux1[] = new String[l / 2];
		String aux2[] = new String[l / 2];

		// only using one for loop to solve the problem.
		for (int i = 0; i < l; i++) {

			if (i < l / 2)
				aux1[i] = args[i];
			else
				aux2[i - l / 2] = args[i];
		}
		
		LinkedList<String> inputNames = new LinkedList<>();
		for(int i = 0; i < aux1.length; i++) {
			inputNames.add(aux1[i]);
			}
		
		Producer producer = new Producer(inputNames, imageQueue);
        Consumer consumer = new Consumer(imageQueue, outConsumer);
        WriterResult write = new WriterResult(inWrite, aux2, aux1);

        producer.start();
        consumer.start();
        write.start();

        producer.join();
        consumer.join();
        write.join();
		
//        producer.stop();
//        consumer.stop();
//        write.stop();
	}
}
