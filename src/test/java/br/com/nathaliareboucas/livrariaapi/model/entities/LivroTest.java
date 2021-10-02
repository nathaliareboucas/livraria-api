package br.com.nathaliareboucas.livrariaapi.model.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Profile("test")
public class LivroTest {
	
	@Test
	public void deveAtualizarTituloAutorDoLivo() {
		Livro livro = Livro.builder().id(1L).titulo("Livro teste").autor("Autor teste").isbn("123").build();
		Livro livroModificado = Livro.builder().id(1L).titulo("Livro atualizado").autor("Autor atualizado").isbn("123").build();
		
		livro.atualizaPropriedades(livroModificado);
		
		assertThat(livro.getId()).isEqualTo(1L);
		assertThat(livro.getTitulo()).isEqualTo(livroModificado.getTitulo());
		assertThat(livro.getAutor()).isEqualTo(livroModificado.getAutor());
		assertThat(livro.getIsbn()).isEqualTo("123");
	}
	
	@Test
	public void deveLancarErroIsbnNaoPodeSerAtualizado() {
		Livro livro = Livro.builder().id(1L).titulo("Livro teste").autor("Autor teste").isbn("123").build();
		Livro livroModificado = Livro.builder().id(1L).titulo("Livro atualizado").autor("Autor atualizado").isbn("456").build();
		
		try {
			livro.atualizaPropriedades(livroModificado);			
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo("O ISBN do livro não pode ser alterado");
		}
	}
	
	@Test
	public void naoDeveAtualizarTituloAutorDoLivro() {
		Livro livro = Livro.builder().id(1L).titulo("Livro teste").autor("Autor teste").isbn("123").build();
		Livro livroModificado = Livro.builder().id(1L).titulo(null).autor(null).isbn(null).build();
		
		livro.atualizaPropriedades(livroModificado);
		
		assertThat(livro.getId()).isEqualTo(1L);
		assertThat(livro.getTitulo()).isNotNull();
		assertThat(livro.getAutor()).isNotNull();
		assertThat(livro.getIsbn()).isNotNull();
	}

}
