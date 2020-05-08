package org.example.basic.exception.exceptions;

/**
 * UserNotFoundException.
 *
 * @author Igor_Orlov
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("There is no user with uid=" + userId);
    }
}
