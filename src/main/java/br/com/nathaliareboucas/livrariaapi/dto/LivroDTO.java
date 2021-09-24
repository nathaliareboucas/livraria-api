package br.com.nathaliareboucas.livrariaapi.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivroDTO {
	
	private Long id;
	
	@NotEmpty(message = "Título é obrigatório")
	private String titulo;
	
	@NotEmpty(message = "Autor é obrigatório")
	private String autor;
	
	@NotEmpty(message = "ISBN é obrigatório")
	private String isbn;
		
}
