package br.com.nathaliareboucas.livrariaapi.model.converters;

import static java.util.Objects.isNull;

import br.com.nathaliareboucas.livrariaapi.dto.EmprestimoDTO;
import br.com.nathaliareboucas.livrariaapi.model.entities.Emprestimo;

public class EmprestimoConverter {

	public static Emprestimo toEntity(EmprestimoDTO emprestimoDTO) {
		if (isNull(emprestimoDTO)) {
			throw new IllegalArgumentException("emprestimoDTO nulo");
		}
		
		if (isNull(emprestimoDTO.getLivroDTO())) {
			throw new IllegalArgumentException("livroDTO do emprestimoDTO nulo");
		}
		
		return Emprestimo.builder()
				.id(emprestimoDTO.getId())
				.livro(LivroConverter.toEntity(emprestimoDTO.getLivroDTO()))
				.nomeCliente(emprestimoDTO.getNomeCliente())
				.dataEmprestimo(emprestimoDTO.getDataEmprestimo())
				.devolvido(emprestimoDTO.getDevolvido())
				.build();
	}
	
	public static EmprestimoDTO toDTO(Emprestimo emprestimo) {
		if (isNull(emprestimo)) {
			throw new IllegalArgumentException("emprestimo nulo");
		}
		
		if (isNull(emprestimo.getLivro())) {
			throw new IllegalArgumentException("livro do emprestimo nulo");
		}
		
		return EmprestimoDTO.builder()
				.id(emprestimo.getId())
				.livroDTO(LivroConverter.toDTO(emprestimo.getLivro()))
				.nomeCliente(emprestimo.getNomeCliente())
				.dataEmprestimo(emprestimo.getDataEmprestimo())
				.devolvido(emprestimo.getDevolvido())
				.build();
	}

}
