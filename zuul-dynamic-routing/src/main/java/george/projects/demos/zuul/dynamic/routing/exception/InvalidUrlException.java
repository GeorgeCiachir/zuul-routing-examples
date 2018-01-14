package george.projects.demos.zuul.dynamic.routing.exceptions;

public class InvalidUrlException extends RuntimeException {

	public InvalidUrlException(String message, Throwable cause) {
		super(message, cause);
	}
}
