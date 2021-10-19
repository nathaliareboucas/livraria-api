package br.com.nathaliareboucas.livrariaapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

public interface LivroService {

	Livro salvar(Livro livro);

	Livro getById(Long id);

	void excluir(Long id);

	Livro atualizar(Livro livro);

	Page<Livro> buscar(Livro livro, Pageable pageRequest);

}
