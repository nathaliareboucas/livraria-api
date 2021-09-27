package br.com.nathaliareboucas.livrariaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
	
	boolean existsByIsbn(String isbn);

}
