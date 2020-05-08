package org.example.basic.exception;

import static org.junit.Assert.assertEquals;

import org.example.basic.exception.exceptions.UserNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test for {@link GlobalExceptionHandler}.
 *
 * @author Igor_Orlov
 */
@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testExceptionHandle() {
        String exMessage = "TestMessage";

        ResponseEntity<Object> response = exceptionHandler.handleException(new UserNotFoundException(exMessage));

        assertEquals("There is no user with uid=" + exMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
