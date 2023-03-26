package it.beata.ordermanager.items.inbound.exceptions;

import it.beata.ordermanager.items.inbound.ItemController;
import it.beata.ordermanager.items.inbound.dto.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackageClasses = ItemController.class)
@Slf4j
public class ItemExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        StandardResponse response = new StandardResponse(
                HttpStatus.BAD_REQUEST,
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex.getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(response);
    }


}
