package br.com.nathaliareboucas.livrariaapi.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nathaliareboucas.livrariaapi.dto.EmprestimoDTO;
import br.com.nathaliareboucas.livrariaapi.dto.LivroDTO;
import br.com.nathaliareboucas.livrariaapi.model.entities.Emprestimo;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;
import br.com.nathaliareboucas.livrariaapi.services.EmprestimoService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {EmprestimoResouce.class})
@AutoConfigureMockMvc
public class EmprestimoResourceTest {
	
	static final String EMPRESTIMOS_API_V1 = "/api/v1/emprestimos";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	EmprestimoService emprestimoService;
	
	@Test
	public void deveRealizarUmEmprestimo() throws Exception {
		EmprestimoDTO emprestimoDTO = criarEmprestimoDTO();
		String json = new ObjectMapper().writeValueAsString(emprestimoDTO);
		
		Emprestimo emprestimoCriado = criarEmprestimoComId();
		
		when(emprestimoService.salvar(any(Emprestimo.class))).thenReturn(emprestimoCriado);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(EMPRESTIMOS_API_V1)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").value(1L));
	}
	
	public EmprestimoDTO criarEmprestimoDTO()  {
		LivroDTO livroDTO = LivroDTO.builder().id(1L).titulo("Título Teste").autor("Autor Teste").isbn("123").build();
		return EmprestimoDTO.builder().livroDTO(livroDTO).nomeCliente("Cliente Teste").devolvido(false).build();
	}
	
	public Emprestimo criarEmprestimoComId() {
		Livro livro = Livro.builder().id(1L).titulo("Título Teste").autor("Autor Teste").isbn("123").build();
		return Emprestimo.builder().id(1L).livro(livro).nomeCliente("Cliente Teste").dataEmprestimo(LocalDate.now())
				.devolvido(false).build();
	}

}
