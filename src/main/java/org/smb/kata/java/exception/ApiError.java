package org.smb.kata.java.exception;

import org.springframework.http.HttpStatus;

import java.util.StringJoiner;

public class ApiError {

    private String message;

    private HttpStatus status;

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiError.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .add("httpStatus=" + status)
                .toString();
    }
}
