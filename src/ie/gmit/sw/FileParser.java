package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
/**
 * Method used for Parsing Files an placing them onto a BlockingQueue of type Shingle
 * @author Michael Coleman
 *
 */
public class FileParser implements Runnable {
	
	private BlockingQueue<Shingle> queue;
	private String file;
	
	/**
	 * Creates a FileParser with a BlockingQueue and a String
	 * @param q Specifies the BlockingQueue to be used
	 * @param f String used to specify the file
	 */
	public FileParser(BlockingQueue<Shingle> q, String f) {
		this.queue=q;
		this.file=f;
	}

	/**
	 * run() used by threads to create shingles from files contents and place them onto the queue
	 */
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line= null;
			//read over file line by line
			while((line = br.readLine()) != null) {
				//When space encountered split line
				String[] shingles = line.split(" ");
				
				//for each shingle, place on queue
				for(String s : shingles) {
					queue.put(new Shingle(file,s.toLowerCase()));	
				}
			}
			queue.put(new Poison(file,"Poison"));
			br.close();
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
