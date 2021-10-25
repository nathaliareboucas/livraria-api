package br.com.nathaliareboucas.livrariaapi.model.entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Emprestimo {

	private Long id;
	private Livro livro;
	private String nomeCliente;
	private LocalDate dataEmprestimo;
	private Boolean devolvido;
	
}
