package ie.gmit.sw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Class used for processing files and returning the ConcurrentHashMap of the processed files
 * @author Michael Coleman
 *
 */
public class Processor {
	private int filecount;
	/**
	 * Method which processes files by creating a FileParser thread for each file and a ShingleTaker thread to assign them to a ConcurrentHashMap
	 * @param dir directory path used to retrieve files
	 * @return A ConcurrentHashMap consisting of key value pairs of types String and a list of Index objects
	 */
	public Future<ConcurrentHashMap<String, List<Index>>> process(String dir) {
		File f = new File(dir);
		String[] files = f.list();
		filecount = files.length;

		BlockingQueue<Shingle> queue = new ArrayBlockingQueue<>(filecount);
		
		for(String fileName: files){
			//change from Windows path directory to Java/Linux path
			//get absolute path on windows e.g. (c\project\file) and change to e.g. (c/project/file)
			String path = f.getAbsolutePath();
			//adapted from https://stackoverflow.com/questions/8323760/java-get-uri-from-filepath
		    if ( File.separatorChar != '/' )
		    {
		        path = path.replace ( File.separatorChar, '/' );
		    }
		
		    //add name of file to path
			String fileDir = path+"/"+fileName;
			
			new Thread(new FileParser(queue, fileDir)).start();
		}
		//create an single thread executor
		ExecutorService executor = Executors.newSingleThreadExecutor();
		@SuppressWarnings("unchecked")
		//create callable with a new ShingleTaker
		Callable<ConcurrentHashMap<String, List<Index>>> callable = new ShingleTaker(queue,filecount);
		//future used to retrieve the ConcurrentHashMap from the ShingleTaker Callable
		Future<ConcurrentHashMap<String, List<Index>>> future =executor.submit(callable);
		return future;		
	}
	/**
	 * Method to return an ArrayList of files in directory specified
	 * @param dir directory path used to retrieve files
	 * @return An ArrayList of the paths of files
	 */
	public ArrayList<String> filePaths(String dir){
		File f = new File(dir);
		String[] files = f.list();
		ArrayList<String> filePaths = new ArrayList<String>();
		for(String fileName: files){
			String path = f.getAbsolutePath();
		    if ( File.separatorChar != '/' )
		    {
		        path = path.replace ( File.separatorChar, '/' );
		    }
		
		    //add name of file to path
			String fileDir = path+"/"+fileName;
			filePaths.add(fileDir);
			
		}
		return filePaths;
		
	}

}
