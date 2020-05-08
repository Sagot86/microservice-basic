package org.example.basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ExceptionMessage.
 *
 * @param <T> T
 * @author Igor_Orlov
 */
public interface ExceptionMessage<T> {

    /**
     * ResponseEntity.
     * @return ResponseEntity
     */
    ResponseEntity<T> toResponse();

    /**
     * HttpStatus.
     * @return HttpStatus
     */
    HttpStatus getHttpStatus();

    /**
     * Message.
     * @return message
     */
    String getMessage();

}
