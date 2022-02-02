package com.flex83.app.exception.handler;

import com.flex83.app.enums.ApiResponseCode;
import com.flex83.app.exception.ApiException;
import com.flex83.app.exception.ValidationException;
import com.flex83.app.response.generic.ResponseDTO;
import com.flex83.app.response.utils.ResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(ApiExceptionHandler.class);

    @Autowired
    private ResponseUtil responseUtil;

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(value = {ApiException.class})
    public ResponseDTO<?> handleGenericException(ApiException e) {
        LOG.error(String.format("API Exception: Got [[%s]] exception with message: %s", e.getClass().getName(), e.getMessage()));
        return responseUtil.exception(ApiResponseCode.ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ResponseDTO<?> handleGenericException(ValidationException e) {
        LOG.error(String.format("Validation Exception: Got [[%s]] exception with message: %s", e.getClass().getName(), e.getMessage()));
        return responseUtil.exception(e.getCode(), e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseDTO<?> exception(NoHandlerFoundException e) {
        LOG.error(String.format("API Exception: Got [[%s]] exception with message: %s", e.getClass().getName(), e.getMessage()));
        return responseUtil.exception(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDTO<?> exception(HttpRequestMethodNotSupportedException e) {
        LOG.error(String.format("API Exception: Got [[%s]] exception with message: %s", e.getClass().getName(), e.getMessage()));
        return responseUtil.exception(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseDTO<?> exception(Exception e) {
        LOG.error(String.format("API Exception: Got [[%s]] exception with message: %s", e.getClass().getName(), e.getMessage()));
        return responseUtil.exception(ApiResponseCode.ERROR.getCode());
    }
}
