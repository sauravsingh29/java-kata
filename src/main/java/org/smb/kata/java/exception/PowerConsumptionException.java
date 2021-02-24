package org.smb.kata.java.exception;

import org.springframework.http.HttpStatus;

public class PowerConsumptionException extends RuntimeException {

    private ApiError apiError;

    private Throwable cause;

    private String message;

    private HttpStatus httpStatus;

    public PowerConsumptionException(Throwable cause, String message, HttpStatus httpStatus) {
        super(message, cause);
        this.cause = cause;
        this.message = message;
        this.httpStatus = httpStatus;
        this.apiError = new ApiError(message, httpStatus);
    }

    public PowerConsumptionException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.apiError = new ApiError(message, httpStatus);
    }

    public ApiError getApiError() {
        return apiError;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
