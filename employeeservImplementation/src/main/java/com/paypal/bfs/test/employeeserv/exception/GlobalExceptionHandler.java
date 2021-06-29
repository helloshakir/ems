package com.paypal.bfs.test.employeeserv.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Unable to process request. Check 'errors' field for details.");

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
			WebRequest request) {
		log.error("Failed to find the requested resource", resourceNotFoundException);
		
		return buildErrorResponse(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidResourceIdentifierException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInvalidResourceIndentifierException(InvalidResourceIdentifierException exception,
			WebRequest request) {
		log.error("Requested resource identifier is not valid", exception);
		
		return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceCreationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInvalidResourceCreationException(ResourceCreationException exception,
			WebRequest request) {
		log.error("Unable to create resource", exception);
		
		return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InputParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInputParseException(InputParseException exception,
			WebRequest request) {
		log.error("Unable to parse input", exception);
		
		return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
		log.error("Unknown error occurred", exception);
		return buildErrorResponse( "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return buildErrorResponse(ex.getMessage(), status);
	}

	private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus httpStatus) {
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
		
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

}
