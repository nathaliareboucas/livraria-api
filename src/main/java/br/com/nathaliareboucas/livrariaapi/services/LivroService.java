package br.com.nathaliareboucas.livrariaapi.services;

import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

public interface LivroService {

	Livro salvar(Livro livro);

	Livro getById(Long id);

	void excluir(Long id);

	Livro atualizar(Livro livro);

}
