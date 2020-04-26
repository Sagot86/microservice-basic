package org.example.basic.exception;

import org.example.basic.exception.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler.
 *
 * @author Igor_Orlov
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class
    })
    public ResponseEntity<Object> handleException(Exception ex) {
        log.debug("GlobalExceptionHandler handle {}", ex.getClass().getName());
        SimpleMessage message = new SimpleMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return message.toResponse();
    }

}
