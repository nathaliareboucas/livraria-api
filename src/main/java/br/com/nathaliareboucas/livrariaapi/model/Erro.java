package br.com.nathaliareboucas.livrariaapi.model;

import java.util.ArrayList;
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

}
