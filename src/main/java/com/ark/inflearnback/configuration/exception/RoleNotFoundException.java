package com.ark.inflearnback.configuration.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(final String message) {
        super(message);
    }

}