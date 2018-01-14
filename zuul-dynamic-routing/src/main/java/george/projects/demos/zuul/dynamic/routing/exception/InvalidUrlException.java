package george.projects.demos.zuul.dynamic.routing.exception;

public class InvalidUrlException extends RuntimeException {

	public InvalidUrlException(String message, Throwable cause) {
		super(message, cause);
	}
}
