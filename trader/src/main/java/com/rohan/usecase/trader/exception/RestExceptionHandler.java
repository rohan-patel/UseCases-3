package com.rohan.usecase.trader.exception;

<<<<<<< HEAD
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rohan.usecase.trader.model.Error;
=======
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;

import com.rohan.usecase.trader.model.Error;
import com.rohan.usecase.trader.exception.UserAlreadyExistException;
>>>>>>> branch 'main' of https://github.com/rohan-patel/UseCases-3.git

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class) 
    private ResponseEntity<Error> handleEntityNotFound(EntityNotFoundException ex){
        Error error = new Error(HttpStatus.NOT_FOUND, "Entity not found", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UserAlreadyExistException.class) 
    private ResponseEntity<Error> handleUserAlreadyExist(UserAlreadyExistException ex){
        Error error = new Error(HttpStatus.BAD_REQUEST, "User Already Exists", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
