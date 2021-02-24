package org.smb.kata.java.api.advice;

import org.smb.kata.java.api.ConsumptionApi;
import org.smb.kata.java.exception.ApiError;
import org.smb.kata.java.exception.PowerConsumptionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ConsumptionApi.class)
public class ConsumptionApiAdvice {

    @ExceptionHandler(PowerConsumptionException.class)
    public final ResponseEntity<ApiError> handlePharmacyException(PowerConsumptionException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ex.getApiError());
    }
}