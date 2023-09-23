package com.example.test.Exception;

import com.example.test.Form.HttpRex;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<HttpRex> handleHttpEx(HttpRuntimeException exception, WebRequest webRequest)
    {
        System.out.println("Exception");
        HttpRex errorDetails = new HttpRex(exception.getHttpStatus()+"",exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(errorDetails);
    }
}
