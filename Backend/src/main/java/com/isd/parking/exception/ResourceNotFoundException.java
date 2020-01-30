package com.isd.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Custom exception class for output exception message
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    /**
     * Constructor
     *
     * @param message - exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
