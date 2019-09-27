package com.synthesis.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

	@ExceptionHandler(TweetNotFoundException.class)
	public final ResponseEntity<Object> handleTweetNotFound(TweetNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()),
				exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFound(UserNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()),
				exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
