package org.example.exception;

/**
 * Generic catchall exception we're going to use for the time being
 * <p>
 * Runtime exception since we really shouldn't see these
 */
public class CheckoutException extends RuntimeException {
	/**
	 * Default constructor with an error message
	 *
	 * @param errorMessage
	 */
	public CheckoutException(String errorMessage) {
		super(errorMessage);
	}
}
