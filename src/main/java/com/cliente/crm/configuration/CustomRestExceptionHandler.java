package com.cliente.crm.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cliente.crm.service.exceptions.DuplicidadeCpfCnpjException;
import com.cliente.crm.util.ApiError;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		log.error("error", ex);
		//
		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ DuplicidadeCpfCnpjException.class })
	public ResponseEntity<Object> handleAll(final DuplicidadeCpfCnpjException ex, final WebRequest request) {
		log.error("error", ex);
		//
		final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "error");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
