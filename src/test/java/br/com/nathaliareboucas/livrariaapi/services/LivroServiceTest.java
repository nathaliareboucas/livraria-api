package br.com.nathaliareboucas.livrariaapi.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nathaliareboucas.livrariaapi.exceptions.NegocioException;
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
		final Livro livro = criarLivro();
		final Livro livroSalvo = Livro.builder().id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
		
		when(livroRepository.existsByIsbn(livro.getIsbn())).thenReturn(false);
		when(livroRepository.save(livro)).thenReturn(livroSalvo);		
		final Livro livroEsperado = livroService.salvar(livro);
		
		assertThat(livroEsperado).isNotNull();
		assertThat(livroEsperado.getTitulo()).isEqualTo(livroSalvo.getTitulo());
		assertThat(livroEsperado.getAutor()).isEqualTo(livroSalvo.getAutor());
		assertThat(livroEsperado.getIsbn()).isEqualTo(livroSalvo.getIsbn());
	}
	
	@Test
	public void naoDeveSalvarLivroComIsbnExistente() {
		final Livro livro = criarLivro();
		when(livroRepository.existsByIsbn(livro.getIsbn())).thenReturn(true);
		
		Throwable exception = catchThrowable(() -> livroService.salvar(livro));
		
		assertThat(exception)
			.isInstanceOf(NegocioException.class)
			.hasMessage("ISBN já cadastrado");
		
		Mockito.verify(livroRepository, Mockito.never()).save(livro);
	}
	
	@Test
	public void deveRecuperarLivroPorId() {
		Livro livro = criarLivroComId();
		when(livroRepository.getById(1L)).thenReturn(livro);
		
		Livro livroRecuperado = livroService.getById(1L);
		
		assertThat(livroRecuperado).isNotNull();
		assertThat(livroRecuperado.getId()).isEqualTo(livro.getId());
		assertThat(livroRecuperado.getTitulo()).isEqualTo(livro.getTitulo());
		assertThat(livroRecuperado.getAutor()).isEqualTo(livro.getAutor());
		assertThat(livroRecuperado.getIsbn()).isEqualTo(livro.getIsbn());
	}
	
	@Test
	public void deveLancarExcecaoQuandoLivroNaoEncontrado() {
		when(livroRepository.getById(1L)).thenThrow(EntityNotFoundException.class);
		
		Throwable excecao = catchThrowable(() -> livroService.getById(1L));
		
		assertThat(excecao)
			.isInstanceOf(EntityNotFoundException.class);
	}

	private Livro criarLivro() {
		return Livro.builder().titulo("Meu Livro").autor("Autor").isbn("A123456").build();
	}
	
	private Livro criarLivroComId() {
		return Livro.builder().id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
	}
	
}
