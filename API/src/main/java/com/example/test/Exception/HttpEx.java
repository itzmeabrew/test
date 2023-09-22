package com.example.test.Exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class HttpEx extends Exception
{
    @Getter
    @Setter
    private HttpStatus status;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String message;

    public HttpEx(HttpStatus status, String code, String message)
    {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpEx(String message, HttpStatus status, String code, String message1)
    {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message1;
    }
}
