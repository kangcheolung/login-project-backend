package com.example.loginproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {
    private final HttpStatus status;

    public UserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static class UserNotFoundException extends UserException {
        public UserNotFoundException() {
            super("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }
    }

    public static class UserHasNoRoleException extends UserException {
        public UserHasNoRoleException() {
            super("권한이 없는 사용자입니다.", HttpStatus.FORBIDDEN);
        }
    }
}