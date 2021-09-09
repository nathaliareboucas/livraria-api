package br.com.nathaliareboucas.livrariaapi.model.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nathaliareboucas.livrariaapi.dto.LivroDTO;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

@ExtendWith(SpringExtension.class)
@Profile("test")
public class LivroConverterTest {
	
	@Test
	public void deveConverterDTOEmEntidade() {
		final LivroDTO livroDTO = LivroDTO.builder()
				.id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
		
		final Livro livroEntity = LivroConverter.toEntity(livroDTO);
		
		assertThat(livroEntity).isNotNull();
		assertThat(livroEntity.getId()).isEqualTo(livroDTO.getId());
		assertThat(livroEntity.getTitulo()).isEqualTo(livroDTO.getTitulo());
		assertThat(livroEntity.getAutor()).isEqualTo(livroDTO.getAutor());
		assertThat(livroEntity.getIsbn()).isEqualTo(livroDTO.getIsbn());
		
	}
	
	@Test
	public void deveConverterEntidadeEmDTO() {
		final Livro livroEntity = Livro.builder()
				.id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
		
		final LivroDTO livroDTO = LivroConverter.toDTO(livroEntity);
		
		assertThat(livroDTO).isNotNull();
		assertThat(livroDTO.getId()).isEqualTo(livroEntity.getId());
		assertThat(livroDTO.getTitulo()).isEqualTo(livroEntity.getTitulo());
		assertThat(livroDTO.getAutor()).isEqualTo(livroEntity.getAutor());
		assertThat(livroDTO.getIsbn()).isEqualTo(livroEntity.getIsbn());
		
	}
	
	@Test
	public void deveLancarExcecaoAoTentarConverterDTOEmEntidade() {
		final LivroDTO livroDTO = null;
		
		try {
			LivroConverter.toEntity(livroDTO);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("livroDTO nulo");
		}
		
	}
	
	@Test
	public void deveLancarExcecaoAoTentarConverterEntidadeEmDTO() {
		final Livro livroEntity = null;
		
		try {
			LivroConverter.toDTO(livroEntity);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("livro nulo");
		}
	}

}
