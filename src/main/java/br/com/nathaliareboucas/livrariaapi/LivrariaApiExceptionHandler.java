package br.com.nathaliareboucas.livrariaapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.nathaliareboucas.livrariaapi.exceptions.Erro;
import br.com.nathaliareboucas.livrariaapi.exceptions.NegocioException;

@RestController
@ControllerAdvice
public class LivrariaApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new Erro(ex.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		return handleExceptionInternal(ex, new Erro(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
