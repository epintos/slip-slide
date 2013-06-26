package exceptions;

public class NoSuchPasswordException extends Exception {
	public NoSuchPasswordException() {
	}
	
	public NoSuchPasswordException(String message) {
		super(message);
	}
}
