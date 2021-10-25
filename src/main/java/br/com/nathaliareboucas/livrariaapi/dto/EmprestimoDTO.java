package br.com.nathaliareboucas.livrariaapi.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmprestimoDTO {
	
	private Long id;
	
	@NotNull(message = "Livro é obrigatório")
	private LivroDTO livroDTO;
	
	@NotBlank(message = "Cliente é obrigatório")
	private String nomeCliente;
	
	@JsonIgnoreProperties
	private LocalDate dataEmprestimo;
	private Boolean devolvido;

}
