package com.serdiuk.task.service.exception;

public class UserTooYoungException extends RuntimeException {
    public UserTooYoungException(String message) {
        super(message);
    }
}
