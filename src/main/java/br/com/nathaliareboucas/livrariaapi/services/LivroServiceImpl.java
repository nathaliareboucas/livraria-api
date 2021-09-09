package br.com.nathaliareboucas.livrariaapi.services;

import org.springframework.stereotype.Service;

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
		return livroRepository.save(livro);
	}

}
