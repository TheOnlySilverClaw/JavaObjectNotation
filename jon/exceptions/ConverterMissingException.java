package jon.exceptions;

public class ConverterMissingException extends JONException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConverterMissingException(String key) {
		super("No converter for key '" + key + "'");
	}
}