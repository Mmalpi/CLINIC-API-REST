package med.mmalpi.api.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity error404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity error400(MethodArgumentNotValidException e) {
		var errors = e.getFieldErrors().stream().map(ErrorHandlerData::new).toList();
		
		return ResponseEntity.badRequest().body(errors);
	}

	
	private record ErrorHandlerData(String campo, String Error) {
		
		public ErrorHandlerData(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}
