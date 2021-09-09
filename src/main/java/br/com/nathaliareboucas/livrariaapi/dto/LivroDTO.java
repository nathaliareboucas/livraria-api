package br.com.nathaliareboucas.livrariaapi.dto;

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
	private String titulo;
	private String autor;
	private String isbn;
		
}
