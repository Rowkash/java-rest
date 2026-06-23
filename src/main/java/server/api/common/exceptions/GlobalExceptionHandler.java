package server.api.common.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException exception) {
    Map<String, Object> body = new HashMap<>();
    body.put("status", exception.getStatusCode().value());
    body.put("message", exception.getReason());

    return new ResponseEntity<>(body, exception.getStatusCode());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleBadRequest(BadRequestException exception) {

    Map<String, Object> body = new HashMap<>();
    body.put("status", 400);
    body.put("message", exception.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleBadRequest(NotFoundException ex) {

    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpStatus.NOT_FOUND);
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({JwtAuthenticationException.class})
  public ResponseEntity<Object> handleAuthenticationException(Exception ex) {

    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpStatus.UNAUTHORIZED.value());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  //    @ExceptionHandler(MethodArgumentNotValidException.class)
  //    public ResponseEntity<Object> handleValidationExceptions(
  //            MethodArgumentNotValidException ex) {
  //
  //        Map<String, String> errors = new HashMap<>();
  //        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
  //            errors.put(error.getField(), error.getDefaultMessage());
  //        }
  //
  //        Map<String, Object> body = new HashMap<>();
  //        body.put("status", HttpStatus.BAD_REQUEST.value());
  //        body.put("message", "Validation failed");
  //        body.put("errors", errors);
  //
  //        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  //    }

  //    @ExceptionHandler(HttpMessageNotReadableException.class)
  //    public ResponseEntity<Object> handleHttpMessageNotReadable(
  //            HttpMessageNotReadableException ex, WebRequest request) {
  //
  //        Map<String, Object> body = new HashMap<>();
  //        body.put("status", HttpStatus.BAD_REQUEST.value());
  //        body.put("message", "Invalid or missing request body.");
  //
  //        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  //    }

}
