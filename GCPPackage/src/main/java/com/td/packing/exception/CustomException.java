package com.td.packing.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice  
public class CustomException extends ResponseEntityExceptionHandler  {
	 

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<ErrorResponse> customNotFoundError(NotFoundException ex){
		 
		ErrorResponse error = new ErrorResponse(); 
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		  
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler({InvalidDataException.class})
	public ResponseEntity<ErrorResponse> customInvalidError(InvalidDataException ex){
		 
		ErrorResponse error = new ErrorResponse(); 
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		  
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); 
	}
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
			Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", new Date());
	        body.put("status", status.value());

	        //Get all fields errors
	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList()); 
	        body.put("errors", errors); 
	        return new ResponseEntity<>(body, headers, status);
	}

  
}
