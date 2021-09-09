package br.com.nathaliareboucas.livrariaapi.model.converters;

import java.util.Objects;

import br.com.nathaliareboucas.livrariaapi.dto.LivroDTO;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

public class LivroConverter {
	
	public static Livro toEntity(final LivroDTO livroDTO) {
		if (Objects.isNull(livroDTO)) {
			throw new IllegalArgumentException("livroDTO nulo");
		}
		
		return Livro.builder()
				.id(livroDTO.getId())
				.titulo(livroDTO.getTitulo())
				.autor(livroDTO.getAutor())
				.isbn(livroDTO.getIsbn())
				.build();
	}
	
	public static LivroDTO toDTO(final Livro livro) {
		if (Objects.isNull(livro)) {
			throw new IllegalArgumentException("livro nulo");
		}
		
		return LivroDTO.builder()
				.id(livro.getId())
				.titulo(livro.getTitulo())
				.autor(livro.getAutor())
				.isbn(livro.getIsbn())
				.build();
	}

}
