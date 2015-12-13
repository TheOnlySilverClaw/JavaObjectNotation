package jon.exceptions;

public class JONMissingValueException extends JONException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JONMissingValueException(String valueName) {
		super("Could not find value for name '" + valueName + "'");
	}
	
	public JONMissingValueException(String valueName, Throwable cause) {
		super("Could not find value for name '" + valueName + "' after Exception.", cause);
	}
}
