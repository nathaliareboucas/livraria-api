package br.com.nathaliareboucas.livrariaapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;

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
	
	@Test
	public void deveRetornarVerdadeiroQuandoExistirLivroComMesmoIsbn() {
		String isbn = "123";
		Livro livro = Livro.builder().titulo("Livro teste").autor("Autor teste").isbn("123").build();
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

}
