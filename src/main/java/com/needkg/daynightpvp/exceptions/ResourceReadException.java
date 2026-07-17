package com.needkg.daynightpvp.exceptions;

import java.nio.file.Path;

public class ResourceReadException extends RuntimeException {

    private static final String MESSAGE = "Failed to read resource ";

    public ResourceReadException(Path resource, Throwable cause) {
        super(MESSAGE + resource, cause);
    }

}