package com.acme.kurs.rest;

import com.acme.kurs.service.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Handler für allgemeine Exceptions.
 */
@ControllerAdvice
@Slf4j
class CommonExceptionHandler {

    /**
     * Exception für nicht gefundene Kurse
     *
     * @param ex Exception
     */
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    void onNotFound(final NotFoundException ex) {
        log.debug("onNotFound: {}", ex.getMessage());
    }
}
