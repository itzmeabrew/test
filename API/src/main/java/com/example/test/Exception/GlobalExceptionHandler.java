package com.example.test.Exception;

import com.example.test.DTO.HttpRex;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(HttpEx.class)
    public ResponseEntity<HttpRex> handleHttpEx(HttpEx exception, WebRequest webRequest)
    {
        System.out.println("Exception");
        HttpRex errorDetails = new HttpRex(exception.getCode(),exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(errorDetails);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpRex> handleUserNameNotFound(HttpEx exception, WebRequest webRequest)
    {
        HttpRex errorDetails = new HttpRex("404","No user found");
        return ResponseEntity.status(404).body(errorDetails);
    }
}
