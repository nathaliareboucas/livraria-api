package br.com.nathaliareboucas.livrariaapi.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class Erro {
	
	private List<String> errors;
	
	public Erro(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getFieldErrors().forEach(fieldError -> this.errors.add(fieldError.getDefaultMessage()));
	}

	public Erro(NegocioException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}

}
