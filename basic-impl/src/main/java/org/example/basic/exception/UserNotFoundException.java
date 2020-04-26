package org.example.basic.exception;

/**
 * UserNotFoundException.
 *
 * @author Igor_Orlov
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
