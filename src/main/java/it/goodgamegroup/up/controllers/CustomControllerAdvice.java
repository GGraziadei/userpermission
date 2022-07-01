package it.goodgamegroup.up.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private DefaultErrorAttributes defaultErrorAttributes;

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest webRequest) {
        Map<String, Object> errorMap = this.defaultErrorAttributes.getErrorAttributes(webRequest , ErrorAttributeOptions.defaults());
        errorMap.put("locale", webRequest.getLocale());
        errorMap.put("error message", ex.getMessage());
        errorMap.put("cause" , ex.getCause());
        return handleExceptionInternal(ex, errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

}
