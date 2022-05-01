package com.project.exception;

import com.project.model.ErrorReportModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class RegistrationExceptionHandler {
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorReportModel> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ErrorReportModel errorReportModel = new ErrorReportModel();
        errorReportModel.setErrorReportTime(System.currentTimeMillis());
        errorReportModel.setMessage(exception.getMessage());
        errorReportModel.setStatusCode(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorReportModel, HttpStatus.FORBIDDEN);
    }
}
