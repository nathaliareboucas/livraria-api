package br.com.nathaliareboucas.livrariaapi.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;
import br.com.nathaliareboucas.livrariaapi.repositories.LivroRepository;

@ExtendWith(SpringExtension.class)
@Profile("test")
public class LivroServiceTest {
	
	LivroService livroService;
	
	@MockBean
	LivroRepository livroRepository;
	
	@BeforeEach
	public void setup() {
		this.livroService = new LivroServiceImpl(livroRepository);
	}
	
	@Test
	public void deveSalvarUmLivro() {
		final Livro livro = Livro.builder().titulo("Meu Livro").autor("Autor").isbn("A123456").build();
		final Livro livroSalvo = Livro.builder().id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
				
		Mockito.when(livroRepository.save(livro)).thenReturn(livroSalvo);		
		final Livro livroEsperado = livroService.salvar(livro);
		
		assertThat(livroEsperado).isNotNull();
		assertThat(livroEsperado.getTitulo()).isEqualTo(livroSalvo.getTitulo());
		assertThat(livroEsperado.getAutor()).isEqualTo(livroSalvo.getAutor());
		assertThat(livroEsperado.getIsbn()).isEqualTo(livroSalvo.getIsbn());
	}

}
