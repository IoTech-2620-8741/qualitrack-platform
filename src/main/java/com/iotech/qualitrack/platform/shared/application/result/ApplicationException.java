package com.iotech.qualitrack.platform.shared.application.result;

/** Carries a business failure across a transaction boundary so writes roll back. */
public class ApplicationException extends RuntimeException {
    private final ApplicationError error;

    public ApplicationException(ApplicationError error) {
        super(error.message());
        this.error = error;
    }

    public ApplicationError error() {
        return error;
    }
}
