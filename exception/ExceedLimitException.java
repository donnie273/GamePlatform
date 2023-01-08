package my.game.app.exception;

public class ExceedLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceedLimitException(String message) {
		super(message);
	}
	
}
