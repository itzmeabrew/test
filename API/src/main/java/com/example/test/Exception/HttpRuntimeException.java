package com.example.test.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpRuntimeException extends RuntimeException
{
    private final HttpStatus httpStatus;

    public HttpRuntimeException(HttpStatus httpStatus, String message)
    {
        super(message);
        this.httpStatus = httpStatus;
    }

}