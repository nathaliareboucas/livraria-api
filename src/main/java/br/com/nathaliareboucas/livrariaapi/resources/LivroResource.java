package br.com.nathaliareboucas.livrariaapi.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.nathaliareboucas.livrariaapi.dto.LivroDTO;
import br.com.nathaliareboucas.livrariaapi.model.converters.LivroConverter;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;
import br.com.nathaliareboucas.livrariaapi.services.LivroService;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroResource {
	
	private LivroService livroService;
	
	public LivroResource(LivroService livroService) {
		this.livroService = livroService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<LivroDTO> criar(@RequestBody @Valid LivroDTO livroDTO) {	
		final Livro livroSalvo = livroService.salvar(LivroConverter.toEntity(livroDTO));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/id").buildAndExpand(livroDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(LivroConverter.toDTO(livroSalvo));
	}

}
