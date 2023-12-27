package ru.skypro.homework.exception;

public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException() {
    }

    public UserUnauthorizedException(String message) {
        super(message);
    }

    public UserUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UserUnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
