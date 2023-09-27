package com.serdiuk.task.controller;

import com.serdiuk.task.service.exception.DateParameterException;
import com.serdiuk.task.service.exception.UserNotFoundException;
import com.serdiuk.task.service.exception.UserTooYoungException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {DateParameterException.class})
    public ResponseEntity<?> handleDateParameter(
            DateParameterException dateParameterException,
            WebRequest request) {
        return super.handleExceptionInternal(dateParameterException, dateParameterException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFound(
            UserNotFoundException userNotFoundException,
            WebRequest request) {
        return super.handleExceptionInternal(userNotFoundException, userNotFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {UserTooYoungException.class})
    public ResponseEntity<?> handleUserTooYoung(
            UserTooYoungException userTooYoungException,
            WebRequest request) {
        return super.handleExceptionInternal(userTooYoungException, userTooYoungException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
