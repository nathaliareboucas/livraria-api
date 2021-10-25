package br.com.nathaliareboucas.livrariaapi.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.nathaliareboucas.livrariaapi.dto.EmprestimoDTO;
import br.com.nathaliareboucas.livrariaapi.model.converters.EmprestimoConverter;
import br.com.nathaliareboucas.livrariaapi.model.entities.Emprestimo;
import br.com.nathaliareboucas.livrariaapi.services.EmprestimoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/emprestimos")
@RequiredArgsConstructor
public class EmprestimoResouce {

	private final EmprestimoService emprestimoService;
	
	@PostMapping
	public ResponseEntity<EmprestimoDTO> criar(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {
		Emprestimo emprestimoSalvo = emprestimoService.salvar(EmprestimoConverter.toEntity(emprestimoDTO));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/id").buildAndExpand(emprestimoSalvo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(EmprestimoConverter.toDTO(emprestimoSalvo));
	}
}
