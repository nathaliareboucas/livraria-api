package br.com.nathaliareboucas.livrariaapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("{id}")
	public ResponseEntity<LivroDTO> buscar(@PathVariable Long id) {
		Livro livro = livroService.getById(id);		
		return ResponseEntity.ok(LivroConverter.toDTO(livro));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		livroService.excluir(id);
	}
	
	@PutMapping()
	public ResponseEntity<LivroDTO> atualizar(@RequestBody LivroDTO livroDTO) {
		Livro livroAtualizado = livroService.atualizar(LivroConverter.toEntity(livroDTO));
		return ResponseEntity.ok(LivroConverter.toDTO(livroAtualizado));
	}
	
	@GetMapping()
	public Page<LivroDTO> buscar(LivroDTO livroDTO, Pageable pageRequest) {
		Page<Livro> pageLivros = livroService.buscar(LivroConverter.toEntity(livroDTO), pageRequest);
		
		List<LivroDTO> livrosDTO = pageLivros.getContent().stream()
			.map(entidade -> LivroConverter.toDTO(entidade))
			.collect(Collectors.toList());
		
		return new PageImpl<LivroDTO>(livrosDTO, pageRequest, pageLivros.getTotalElements());
	}

}
