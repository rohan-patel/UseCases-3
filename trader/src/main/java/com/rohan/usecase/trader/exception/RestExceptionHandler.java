package com.rohan.usecase.trader.exception;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class) 
    private ResponseEntity<Error> handleEntityNotFound(EntityNotFoundException ex){
        Error error = new Error(HttpStatus.NOT_FOUND, "Entity not found", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
