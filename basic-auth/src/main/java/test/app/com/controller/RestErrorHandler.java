package test.app.com.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {


    private static final String RESOURCE_ERROR_MESSAGE = "Authentication service is unavailable";

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity handleAuthenticationException(ResourceAccessException ex, WebRequest request) {
        log.debug("ResourceAccessException was thrown : {} ", ex);
        return handleExceptionInternal(ex, RESOURCE_ERROR_MESSAGE, new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

}