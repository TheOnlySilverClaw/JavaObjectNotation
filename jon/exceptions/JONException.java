package jon.exceptions;

public class JONException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JONException(String message) {
		super(message);
	}

	public JONException(String message, Throwable cause) {
		super(message, cause);
	}
}
