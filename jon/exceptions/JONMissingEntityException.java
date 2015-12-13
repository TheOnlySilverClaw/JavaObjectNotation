package jon.exceptions;

public class JONMissingEntityException extends JONException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JONMissingEntityException(String entityName) {
		super("Could not find entity for name '" + entityName + "'");
	}
	
	public JONMissingEntityException(String entityName, Throwable cause) {
		super("Could not find entity for name '" + entityName + "' after Exception.", cause);
	}
}
