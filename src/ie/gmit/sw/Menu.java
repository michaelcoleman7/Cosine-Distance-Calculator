package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
/**
 * Class used for display the user options available
 * @author Michael Coleman
 *
 */
public class Menu {
	private Scanner s = new Scanner(System.in);
	boolean keeprunning = true;

	/**
	 * Method used to display menu options
	 */
	public void show() {
		while (keeprunning) {
			System.out.println("Please select from below options");
			System.out.println("C ) Calculate Cosine Distance");
			System.out.println("Q ) Quit");
			String option = s.next();
			if(option.equalsIgnoreCase("c")){
					System.out.println("Enter a path of a file with which you want to be the query file");
					String queryFilePath = s.next();
					System.out.println("Enter a path of a file with which you want to be the subject files");
					String subjectFilePath = s.next();
					
					//Start the timer
					long startTime = System.nanoTime();
					
					//Subject files
					try{
						Processor p = new Processor();
						Future<ConcurrentHashMap<String, List<Index>>> qDB = p.process(queryFilePath);
						
						//Process files
						Processor p2 = new Processor();
						Future<ConcurrentHashMap<String, List<Index>>> subDB =p2.process(subjectFilePath);
						
						ConcurrentHashMap<String, List<Index>> queryDB = null;
						try {
							//get ConcurrentHashMap from future returned from p
							queryDB = qDB.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						ConcurrentHashMap<String, List<Index>> subjectDB = null;
						try {
							//get ConcurrentHashMap from future returned from p2
							subjectDB = subDB.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						
						//get file paths
						ArrayList<String> files = p.filePaths(subjectFilePath);
						
						//calculate cosine distances
						List<Double> results = new ArrayList<Double>();
						CosineCalculator cc = new CosineCalculator(queryDB, subjectDB, files);
						results = cc.calculateCosineDistance();
						
						System.out.println("\nResults: ");
						System.out.println("===================================");
						for(int i = 0; i< results.size();i++){
							System.out.println("Results are: "+results.get(i));	
						}	
						
						// End the Timer
						long endTime   = System.nanoTime();
						long totalTime = endTime - startTime;
				        // Print the total Time
				        double sec = (double)totalTime / 1000000000.0;
				        System.out.println("Total time to process files and calculate cosine distance is: "+sec+"\n");
					}catch(Exception e){
						System.out.println("Error trying to process files");
					}
			}
			else if(option.equalsIgnoreCase("q")){
				keeprunning = false;
			}
		}
	}
}
