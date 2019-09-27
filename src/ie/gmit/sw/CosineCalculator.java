package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * This class is used to calculate the cosine distance between two ConcurrentHashMap's for specified files (query Map and Subject Map)
 * @author Michael Coleman
 */
public class CosineCalculator {
	private ConcurrentHashMap<String, List<Index>> queryDB = new ConcurrentHashMap<String,List<Index>>();
	private ConcurrentHashMap<String, List<Index>> subjectDB = new ConcurrentHashMap<String,List<Index>>();
	private ArrayList<String> files;
	
	/**
	 * Creates a CosineCalculator Object using ConcurrentHashMap's and an ArrayList
	 * @param queryDB ConcurrentHashMap which is gotten from query file
	 * @param subjectDB ConcurrentHashMap which is gotten from query file/files
	 * @param files ArrayList of files paths to calculate cosine distance for each file
	 */
	public CosineCalculator(ConcurrentHashMap<String, List<Index>> queryDB,ConcurrentHashMap<String, List<Index>> subjectDB, ArrayList<String> files) {
		this.queryDB = queryDB;
		this.subjectDB = subjectDB;
		this.files = files;
	}
	
	/**
	 * Method to calculate cosine distance between query and subject files
	 * @return A list of type double which consists of the results of calculating the cosine distance
	 */
	public List<Double> calculateCosineDistance(){
		List<Double> resultList = new ArrayList<Double>();
		int dotProduct=0,queryMagnitude=0,subjectMagnitude=0;
		double result=0;
		
		//loop all subject files
		for(String file: files){
			//For every String in the query ConcurrentHashMap
			for (Entry<String, List<Index>> query : queryDB.entrySet()){		
				List<Index> queryLst = query.getValue();
				Index queryIndex = (Index) queryLst.get(0);
				
				//compute magnitude for query file
				queryMagnitude+=queryIndex.getFrequency();
				
				//query file key is stored in subject ConcurrentHashMap
				if(subjectDB.containsKey(query.getKey())){
					List<Index> subIndex = subjectDB.get(query.getKey());
					
					for(Index in : subIndex){
						if(file.equalsIgnoreCase(in.getFileName())) {
							dotProduct += queryIndex.getFrequency() * in.getFrequency();
						}
					}
				}
			}
			
			//loop subject entries to calculate subject magnitude
			for (Entry<String, List<Index>> subject : subjectDB.entrySet()){
				List<Index> subjectLst = subject.getValue();
				for(Index in : subjectLst){
					//if file matches file name from index
					if(file.equalsIgnoreCase(in.getFileName())) {	
						//add to subject magnitude
						subjectMagnitude+=in.getFrequency();
					}
				}
				
			}
		   
			//compute result
			result = dotProduct/(Math.sqrt(queryMagnitude) * Math.sqrt(subjectMagnitude));
			resultList.add(result);
			
			//reset calculations for other result calculations
			dotProduct=0;
			queryMagnitude=0;
			subjectMagnitude=0;
		}
		return resultList;	
	}
}
