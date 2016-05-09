package com.okorana.documentext.phonebookdata;

/**
 * Created by eefret on 5/9/16.
 */
/**
 * Thrown when an invalid header value was set.
 */
public class InvalidHeaderValueException extends Exception {
    private static final long serialVersionUID = -2053384496042052262L;
    /**
     * Constructs an InvalidHeaderValueException with no detailed message.
     */
    public InvalidHeaderValueException() {
        super();
    }
    /**
     * Constructs an InvalidHeaderValueException with the specified detailed message.
     *
     * @param message the detailed message.
     */
    public InvalidHeaderValueException(String message) {
        super(message);
    }
}
