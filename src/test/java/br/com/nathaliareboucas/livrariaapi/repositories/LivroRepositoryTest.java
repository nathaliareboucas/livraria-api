package br.com.nathaliareboucas.livrariaapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LivroRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	LivroRepository livroRepository;
	
	public Livro criarLivro() {
		return Livro.builder().titulo("Livro teste").autor("Autor teste").isbn("123").build();
	}
	
	public Livro criarLivroComId() {
		return Livro.builder().id(1L).titulo("Livro teste").autor("Autor teste").isbn("123").build();
	}
	
	@Test
	public void deveRetornarVerdadeiroQuandoExistirLivroComMesmoIsbn() {
		String isbn = "123";
		Livro livro = criarLivro();
		entityManager.persist(livro);
		
		boolean livroExistente = livroRepository.existsByIsbn(isbn);
		
		assertThat(livroExistente).isTrue();		
	} 
	
	@Test
	public void deveRetornarFalsoQuandoNaoExistirLivroComMesmoIsbn() {
		String isbn = "123";
		
		boolean livroExistente = livroRepository.existsByIsbn(isbn);
		
		assertThat(livroExistente).isFalse();		
	} 
	
	@Test
	public void deveRecuperarLivroPorId() {
		Livro livro = criarLivro();
		Livro livroSalvo = entityManager.persist(livro);
		
		Livro livroRecuperado = livroRepository.getById(livroSalvo.getId());
		
		assertThat(livroRecuperado).isNotNull();
		assertThat(livro.getId()).isNotNull();
		assertThat(livroRecuperado.getTitulo()).isEqualTo(livro.getTitulo());
		assertThat(livroRecuperado.getAutor()).isEqualTo(livro.getAutor());
		assertThat(livroRecuperado.getIsbn()).isEqualTo(livro.getIsbn());
	}
	
	@Test 
	public void deveLancarExcecaoAoRecuperarLivroPorId() {
		try {
			livroRepository.getById(1L);
		} catch (Exception excecao) {
			assertThat(excecao)
				.isInstanceOf(EntityNotFoundException.class);
		}
		
	}
	
	@Test
	public void deveSalvarUmLivro() {
		Livro livro = criarLivro();
		
		Livro livroSalvo = livroRepository.save(livro);
		
		assertThat(livroSalvo).isNotNull();
		assertThat(livroSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveExcluirUmLivro() {
		Livro livro = criarLivro();
		entityManager.persist(livro);
		
		Livro livroExistente = entityManager.find(Livro.class, livro.getId());
		
		livroRepository.delete(livroExistente);
		
		Livro livroExcluido = entityManager.find(Livro.class, livro.getId());
		
		assertThat(livroExcluido).isNull();
	}

}
