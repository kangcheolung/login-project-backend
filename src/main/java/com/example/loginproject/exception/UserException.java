package com.example.loginproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 사용자 관련 예외 클래스
 */
@Getter
public class UserException extends RuntimeException {

    private final HttpStatus status;

    public UserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * 사용자를 찾을 수 없을 때 발생하는 예외
     */
    public static class UserNotFoundException extends UserException {
        public UserNotFoundException() {
            super("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 권한이 없는 사용자일 때 발생하는 예외
     */
    public static class UserHasNoRoleException extends UserException {
        public UserHasNoRoleException() {
            super("권한이 없는 사용자입니다.", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * 이미 존재하는 사용자 ID로 가입하려 할 때 발생하는 예외
     */
    public static class UserAlreadyExistsException extends UserException {
        public UserAlreadyExistsException() {
            super("이미 존재하는 사용자 ID입니다.", HttpStatus.CONFLICT);
        }
    }
}