package com.arun.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.arun.dtos.ApiResponseMessage;



@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

	//handle resource not found exception
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException e)
	{
		logger.info("Exception Handler is Invoked");
		
		ApiResponseMessage message=ApiResponseMessage.builder()
			.message(e.getMessage())
			.httpStatus(HttpStatus.NOT_FOUND)
			.success(false)
			.build();
		return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
		
	}
	
	//Method Argument Not Valid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
	{
		List<ObjectError> allErros = e.getBindingResult().getAllErrors();
		
		Map<String,Object> response=new HashMap<>();
		
		allErros.stream().forEach(objectError->{
			String message=objectError.getDefaultMessage();
			String field=((FieldError) objectError).getField();
			response.put(field, message);
		});
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	//Handle BadApiRequest
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException e)
	{
		logger.info("Bad Request Api Handler is Invoked");
		
		ApiResponseMessage message=ApiResponseMessage.builder()
			.message(e.getMessage())
			.httpStatus(HttpStatus.BAD_REQUEST)
			.success(false)
			.build();
		return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	
}
