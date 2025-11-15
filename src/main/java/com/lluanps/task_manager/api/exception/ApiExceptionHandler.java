package com.lluanps.task_manager.api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
		LocalDateTime now = LocalDateTime.now();
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", now);
		body.put("status", 400);
		body.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(body);
	}
	
}