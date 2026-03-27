package com.examenSPDO.reservation_service.exception;

import com.examenSPDO.reservation_service.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<com.examenSPDO.reservation_service.exception.APIException> handleRequestException(RequestException e) {
        com.examenSPDO.reservation_service.exception.APIException exception = new com.examenSPDO.reservation_service.exception.APIException(e.getMessage(), e.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(exception, e.getStatus());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<APIException> handleEntityNotFoundException(EntityNotFoundException e) {
        APIException exception = new APIException(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<APIException> handleNumberFormatException(NumberFormatException e) {
        APIException exception = new APIException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        APIException exception = new APIException("the input provided is invalid", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    //  Ajoute ce handler
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIException> handleNumberFormatException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
                new APIException(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
}
