package com.project.exception;

import com.project.model.ErrorReportModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorReportModel> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ErrorReportModel errorReportModel = new ErrorReportModel();
        errorReportModel.setErrorReportTime(System.currentTimeMillis());
        errorReportModel.setMessage(exception.getMessage());
        errorReportModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorReportModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorReportModel> handleInvalidProjectAccessException(InvalidProjectAccessException exception){
        ErrorReportModel errorReportModel = new ErrorReportModel();
        errorReportModel.setErrorReportTime(System.currentTimeMillis());
        errorReportModel.setMessage(exception.getMessage());
        errorReportModel.setStatusCode(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorReportModel, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorReportModel> handleJWTException(JWTException exception){
        ErrorReportModel errorReportModel = new ErrorReportModel();
        errorReportModel.setErrorReportTime(System.currentTimeMillis());
        errorReportModel.setMessage(exception.getMessage());
        errorReportModel.setStatusCode(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorReportModel, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorReportModel> handleUserNotFoundException(UserNotFoundException exception){
        ErrorReportModel errorReportModel = new ErrorReportModel();
        errorReportModel.setErrorReportTime(System.currentTimeMillis());
        errorReportModel.setMessage(exception.getMessage());
        errorReportModel.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorReportModel, HttpStatus.NOT_FOUND);
    }
}
