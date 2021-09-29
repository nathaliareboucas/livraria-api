package br.com.nathaliareboucas.livrariaapi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.nathaliareboucas.livrariaapi.exceptions.NegocioException;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;
import br.com.nathaliareboucas.livrariaapi.repositories.LivroRepository;

@Service
public class LivroServiceImpl implements LivroService {

	private LivroRepository livroRepository;
	
	public LivroServiceImpl(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	@Override
	public Livro salvar(Livro livro) {
		boolean isbnExistente = livroRepository.existsByIsbn(livro.getIsbn());
		
		if (isbnExistente) {
			throw new NegocioException("ISBN já cadastrado");
		}
		
		return livroRepository.save(livro);
	}

	@Override
	public Optional<Livro> getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
