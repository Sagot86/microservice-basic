package org.example.basic.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * SimpleMessage.
 *
 * @author Igor_Orlov
 */
@RequiredArgsConstructor
public class SimpleMessage implements ExceptionMessage<Object> {

    private final String message;
    private final HttpStatus status;

    @Override
    public ResponseEntity<Object> toResponse() {
        return new ResponseEntity<>(message, status);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
