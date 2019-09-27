package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to take shingles from a queue and adds them to a ConcurrentHashMap along with a list of Index objects
 * @author Michael Coleman
 */
@SuppressWarnings("rawtypes")
public class ShingleTaker implements Callable {
	private BlockingQueue<Shingle> queue;
	private int filecount;
	private ConcurrentHashMap<String, List<Index>> db = new ConcurrentHashMap<String,List<Index>>();

	public ShingleTaker(BlockingQueue<Shingle> q, int count) {
		this.queue=q;
		this.filecount=count;
	}
	
	/**
	 * call() used to return a ConcurrentHashMap with a String and a list of Index objects as key value pairs
	 */
	@Override
	public ConcurrentHashMap<String, List<Index>> call() {	
		while(filecount>0) {
			try {
				Shingle s = queue.take();
				if(s instanceof Poison) {
					filecount--;
				}
				else{
					String shingle = s.getShingle();
					List<Index> list = null;

					//shingle not in Map
					if(!db.containsKey(shingle)){
						list = new ArrayList<Index>();
						list.add(new Index(1,s.getBook()));
					}
					//Update Shingle
					else{
						
						//get list for shingle
						list = db.get(shingle);
						
						Iterator<Index> iter = list.iterator();
						boolean fileInMap = false;

						//check if index matches file
						while (iter.hasNext()) {
							Index in = iter.next();
							if(s.getBook()==in.getFileName()) {
								fileInMap=true;
							}
						}
						
						if(fileInMap==false) {
							//add new file to map
							list.add(new Index(1,s.getBook()));
						}
						else {	
							//re-initialise iterator
							iter = list.iterator();
							
							while (iter.hasNext()) {
								Index in = iter.next();
								if(in.getFileName()==s.getBook()){
									//increase frequency by on
									in.increaseFrequency();
								}
							}
						}
						
					}
					//Put entry into Map
					db.put(shingle, list);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        
        //return Map as a future
		return db;  
		
	}
}
