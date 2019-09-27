package ie.gmit.sw;
/**
 * Class used for indicating the end of a file extended from Class Shingle
 * @author Michael Coleman
 *
 */
public class Poison extends Shingle{
	/**
	 * Creates a Poison object with Strings for file and shingle called from super
	 * @param file parameter for the file path
	 * @param s parameter for the word/shingle
	 */
	public Poison(String file, String s) {
		super(file,s);
	}
}
