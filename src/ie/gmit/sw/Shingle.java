package ie.gmit.sw;
/**
 * Class used to create a Shingle object
 * @author Michael Coleman
 *
 */
public class Shingle {
	private String book;
	private String shingle;
	
	/**
	 * Creates a Shingle object with Strings for book and shingle
	 * @param file parameter for the file path
	 * @param lowerCase parameter for the word/shingle
	 */
	public Shingle(String file, String lowerCase) {
		this.book=file;
		this.shingle=lowerCase;
	}
	/**
	 * Getter Method for returning a shingle String
	 * @return String - shingle
	 */
	public String getShingle(){
		return shingle;
	}
	/**
	 * Getter Method for returning a book String
	 * @return String - book
	 */
	public String getBook() {
		return book;
	}
	/**
	 * Setter Method for setting book String equal to a specified String
	 * @param book String value that book will be set to
	 */
	public void setBook(String book) {
		this.book = book;
	}
	/**
	 * Setter Method for setting shingle String equal to a specified String
	 * @param shingle String value that shingle will be set to
	 */
	public void setShingle(String shingle) {
		this.shingle = shingle;
	}
}
