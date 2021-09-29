package br.com.nathaliareboucas.livrariaapi.services;

import java.util.Optional;

import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

public interface LivroService {

	Livro salvar(Livro livro);

	Optional<Livro> getById(Long id);

}
