package com.epam.training.shipping.controllers;

import com.epam.training.shipping.apimodel.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorModel> handleNoSuchElementException(NoSuchElementException e) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setMessage(e.getMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }
}
