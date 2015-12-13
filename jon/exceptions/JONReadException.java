package jon.exceptions;

public class JONReadException extends JONException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JONReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public JONReadException(String message) {
		super(message);
	}
}
