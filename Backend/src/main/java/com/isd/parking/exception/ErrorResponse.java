package com.isd.parking.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Error response model class
 * Uses in custom Exception handler.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse {

    private Date timestamp;

    private String status;

    private String message;

    private String details;
}

