package ie.gmit.sw;
/**
 * Class used to create an Index object
 * @author Michael Coleman
 */
public class Index {
	private int frequency;
	private String fileName;
	
	/**
	 * Creates a Index object with a String value for book an integer value for frequency
	 * @param freq frequency parameter for number of occurrences of shingle
	 * @param book parameter to specify which book/file the shingle is associated with
	 */
	public Index(int freq, String book) {
		this.frequency=freq;
		this.fileName=book;
	}
	
	/**
	 * Getter Method for returning the frequency integer
	 * @return frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * Setter Method for setting frequency integer equal to a specified integer
	 * @param fileName string which fileName is to set to
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * Method to increase the frequency of an occurrence of a shingle by one
	 */
	public void increaseFrequency(){
		this.frequency += 1;
	}
	/**
	 * Getter Method for returning the String fileName
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Setter Method for setting fileName String equal to a specified String
	 * @param fileName string which fileName is to set to
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
