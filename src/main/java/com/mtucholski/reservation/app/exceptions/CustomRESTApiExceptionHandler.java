package com.mtucholski.reservation.app.exceptions;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author mtucholski
 * Controller advisor class for REST Exceptions
 */
@Component
@ControllerAdvice
@Slf4j
public class CustomRESTApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final List<String> errors = new ArrayList<>();
    private String error = "";

    /**
     * method for handling MethodArgumentNotValidException
     * @param ex exception thrown
     * @param headers http headers
     * @param status http status
     * @param request http request
     * @return ResponseEntity object with http status, message and error details
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getClass().getName());
        val apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        errors.clear();
        addErrorsFieldToList(ex);
        addObjectErrorToList(ex);

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * method for handling BindException
     * @param ex thrown exception
     * @param headers http header
     * @param status http status
     * @param request http request
     * @return ResponseEntity object with http status, message and error details
     */
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex.getClass().getName());
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        errors.clear();
        addErrorsFieldToList(ex);
        addObjectErrorToList(ex);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /**
     *  method for handling TypeMismatchException
     * @param ex thrown exception
     * @param headers http headers
     * @param status http status
     * @param request http request
     * @return ResponseEntity with description
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        logger.error(ex.getClass().getName());
        error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * method for handling MissingServletRequestPart
     * @param ex thrown exception
     * @param headers http headers
     * @param status http status
     * @param request http request
     * @return ResponseEntity with description
     */

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        logger.error(ex.getClass().getName());
        error = ex.getRequestPartName() + " part is missing";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handler for MissingServletRequestParameterException
     * @param ex thrown exception
     * @param headers http headers
     * @param status http status
     * @param request http request
     * @return ResponseEntity with description
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        logger.error(ex.getClass().getName());
        error = ex.getParameterName() + " parameter is missing";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * exception handler for MethodArgumentTypeMismatchException class
     * @param ex thrown exception
     * @param request http request
     * @return ResponseEntity with description
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {

        logger.error(ex.getClass().getName());
        logger.fatal(request);

        error = ex.getName() + "Object should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     *  exception hander for ConstraintViolationException.class
     * @param ex thrown exception
     * @param request http request
     * @return ResponseEntity with description
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {

        logger.error(ex.getClass().getName());
        logger.fatal(request);

        errors.clear();

        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     *  handler for NoHandlerFoundException
     * @param ex thrown exception
     * @param headers http headers
     * @param status http status 404
     * @param request http request
     * @return ResponseEntity with description 404
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        logger.error(ex.getClass().getName());
        logger.fatal(request);

         error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handler for HttpRequestMethodNotSupported
     * @param ex thrown exception
     * @param headers http headers
     * @param status http status 405
     * @param request http request
     * @return ResponseEntity with description
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        logger.error(ex.getClass().getName());
        logger.fatal(request);

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    /**
     * handler for HttpMediaTypeNotSupported
     * @param ex thrown exception
     * @param headers http headers
     * @param status http status 415
     * @param request http request
     * @return ResponseEntity with description
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        logger.error(ex.getClass().getName());
        logger.fatal(request);
        final StringBuilder builder = new StringBuilder();

        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");

        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handler for Exception.class
     * @param ex thrown exception
     * @param request http request
     * @return ResponseEntity with description
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {

        logger.error(ex.getClass().getName());
        logger.fatal(request);

        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     *  private method for adding errors to list
     * @param ex BindException
     */
    private void addErrorsFieldToList(BindException ex) {

        for (final FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.add(err.getField() + ": " + err.getDefaultMessage());
        }
    }
    /**
     *  private method for adding errors to list
     * @param ex MethodArgumentNotValidException
     */
    private void addErrorsFieldToList(MethodArgumentNotValidException ex) {

        for (final FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.add(err.getField() + ": " + err.getDefaultMessage());
        }
    }

    /**
     *  private method for adding errors to list
     * @param ex BindException
     */
    private void addObjectErrorToList(BindException ex) {


        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
    }

    /**
     *  private method for adding errors to list
     * @param ex MethodArgumentNotValidException
     */
    private void addObjectErrorToList(MethodArgumentNotValidException ex) {

        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
    }
}

