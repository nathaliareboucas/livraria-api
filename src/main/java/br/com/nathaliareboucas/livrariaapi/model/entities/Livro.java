package br.com.nathaliareboucas.livrariaapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

	private Long id;
	private String titulo;
	private String autor;
	private String isbn;
	
}
