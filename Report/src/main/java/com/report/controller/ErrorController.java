package com.report.controller;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.report.miscallaneous.ErrorModel;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledException(DisabledException e){
        final String message = e.getMessage() ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel(401, message));
    }
	
	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
        final String message = e.getMessage() ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel(401, message));
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        final String message = "Access Denied";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel(403, message));
    }
	
	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorModel(404, e.getMessage()));
    }
	
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorModel(404, e.getMessage()));
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateMySQLKeyException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorModel(404, e.getMessage()));
    }
}
