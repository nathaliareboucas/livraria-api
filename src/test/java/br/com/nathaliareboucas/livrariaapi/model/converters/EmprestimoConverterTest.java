package br.com.nathaliareboucas.livrariaapi.model.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nathaliareboucas.livrariaapi.dto.EmprestimoDTO;
import br.com.nathaliareboucas.livrariaapi.dto.LivroDTO;
import br.com.nathaliareboucas.livrariaapi.model.entities.Emprestimo;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmprestimoConverterTest {
	
	@Test
	public void deveConverterDTOEmEntidade() {		
		EmprestimoDTO emprestimoDTO = criarEmprestimoDTOComId();	
		Emprestimo emprestimoEsperado = criarEmprestimoComId();
		
		Emprestimo emprestimoConvertido = EmprestimoConverter.toEntity(emprestimoDTO);
		
		assertThat(emprestimoConvertido).isNotNull();
		assertThat(emprestimoConvertido.getId()).isNotNull();
		assertThat(emprestimoConvertido.getLivro()).isNotNull();
		assertThat(emprestimoConvertido.getNomeCliente()).isEqualTo(emprestimoEsperado.getNomeCliente());
		assertThat(emprestimoConvertido.getDataEmprestimo()).isNotNull();
		assertThat(emprestimoConvertido.getDevolvido()).isFalse();
	}
	
	@Test
	public void deveConverterEntidadeEmDTO() {
		Emprestimo emprestimo = criarEmprestimoComId();
		EmprestimoDTO emprestimoDTOEsperado = criarEmprestimoDTOComId();
		
		EmprestimoDTO emprestimoDTOConvertido = EmprestimoConverter.toDTO(emprestimo);
		
		assertThat(emprestimoDTOConvertido).isNotNull();
		assertThat(emprestimoDTOConvertido.getId()).isNotNull();
		assertThat(emprestimoDTOConvertido.getLivroDTO()).isNotNull();
		assertThat(emprestimoDTOConvertido.getNomeCliente()).isEqualTo(emprestimoDTOEsperado.getNomeCliente());
		assertThat(emprestimoDTOConvertido.getDataEmprestimo()).isNotNull();
		assertThat(emprestimoDTOConvertido.getDevolvido()).isFalse();
	}
	
	@Test
	public void deveLancarExcecaoEmprestimoNuloAoTentarConverterEmEmprestimoDTO() {
		Emprestimo emprestimo = null;
		
		Throwable excecao = catchThrowable(() -> EmprestimoConverter.toDTO(emprestimo));
		
		assertThat(excecao).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("emprestimo nulo");
	}
	
	@Test
	public void deveLancarExcecaoLivroNuloAoTentarConverterEmEmprestimoDTO() {
		Emprestimo emprestimoSemLivro = Emprestimo.builder().id(1L).nomeCliente("Cliente Teste")
				.dataEmprestimo(LocalDate.now()).devolvido(false).build();
		
		Throwable excecao = catchThrowable(() -> EmprestimoConverter.toDTO(emprestimoSemLivro));
		
		assertThat(excecao).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("livro do emprestimo nulo");
		
	}
	
	@Test
	public void deveLancarExcecaoEmprestimoDTONuloAoTentarConverterEmEntidade() {
		EmprestimoDTO emprestimoDTO = null;
		
		Throwable excecao = catchThrowable(() -> EmprestimoConverter.toEntity(emprestimoDTO));
		
		assertThat(excecao).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("emprestimoDTO nulo");
	}
	
	@Test
	public void deveLancarExcecaoLivroDTONuloAoTentarConverterEmEntidade() {
		EmprestimoDTO emprestimoDTOSemLivroDTO = EmprestimoDTO.builder().id(1L).nomeCliente("Cliente Teste")
				.dataEmprestimo(LocalDate.now()).devolvido(false).build();
		
		Throwable excecao = catchThrowable(() -> EmprestimoConverter.toEntity(emprestimoDTOSemLivroDTO));
		
		assertThat(excecao).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("livroDTO do emprestimoDTO nulo");
	}
	
	private EmprestimoDTO criarEmprestimoDTOComId() {
		LivroDTO livroDTO = LivroDTO.builder().id(1L).titulo("Titulo Teste").autor("Autor Teste").isbn("123").build();
		return EmprestimoDTO.builder().id(1L).livroDTO(livroDTO).nomeCliente("Cliente Teste")
				.dataEmprestimo(LocalDate.now()).devolvido(false).build();
	}

	private Emprestimo criarEmprestimoComId() {
		Livro livro = Livro.builder().id(1L).titulo("Titulo Teste").autor("Autor Teste").isbn("123").build();
		return Emprestimo.builder().id(1L).livro(livro).nomeCliente("Cliente Teste")
				.dataEmprestimo(LocalDate.now()).devolvido(false).build();
	}
	
}
