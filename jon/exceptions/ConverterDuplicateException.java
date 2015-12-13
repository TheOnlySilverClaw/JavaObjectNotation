package jon.exceptions;

public class ConverterDuplicateException extends JONException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConverterDuplicateException(String key) {
		super("Converter is already installed for key '" + key + "'");
	}
}