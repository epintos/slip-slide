package exceptions;

public class RepeatedPasswordsException extends Exception {
	
	public RepeatedPasswordsException() {
	}
	
	public RepeatedPasswordsException(String message) {
		super(message);
	}

}
