package br.com.nathaliareboucas.livrariaapi.resources;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nathaliareboucas.livrariaapi.dto.LivroDTO;
import br.com.nathaliareboucas.livrariaapi.exceptions.NegocioException;
import br.com.nathaliareboucas.livrariaapi.model.entities.Livro;
import br.com.nathaliareboucas.livrariaapi.services.LivroService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class LivroResourceTest {
	
	static String LIVROS_API_V1 = "/api/v1/livros";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	LivroService livroService;
	
	private LivroDTO criarNovoLivroDTO() {
		return LivroDTO.builder().titulo("Meu Livro").autor("Autor").isbn("A123456").build();
	}
	
	private Livro criarNovoLivroComId() {
		return Livro.builder().id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
	}
	
	@Test
	public void deveCriarUmLivro() throws Exception {	
		final LivroDTO livroDTO = criarNovoLivroDTO();
		final Livro livroSalvo = criarNovoLivroComId();
		
		given(livroService.salvar(Mockito.any(Livro.class))).willReturn(livroSalvo);
		String json = new ObjectMapper().writeValueAsString(livroDTO);
				
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVROS_API_V1)
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").value(1L))
			.andExpect(jsonPath("titulo").value(livroDTO.getTitulo()))
			.andExpect(jsonPath("autor").value(livroDTO.getAutor()))
			.andExpect(jsonPath("isbn").value(livroDTO.getIsbn()));
	}
	
	@Test
	public void deveLancarErroValidacaoDadosInsuficientes() throws Exception {
		String json = new ObjectMapper().writeValueAsString(new LivroDTO());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVROS_API_V1)
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(3)));
	}
	
	@Test
	public void naoDeveCriarLivroComIsbnExistente() throws Exception {
		LivroDTO livroDTO = criarNovoLivroDTO();
		String json = new ObjectMapper().writeValueAsString(livroDTO);
		String mensagemErro = "ISBN já cadastrado";

		given(livroService.salvar(Mockito.any(Livro.class))).willThrow(new NegocioException(mensagemErro));		

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVROS_API_V1)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(1)))
			.andExpect(jsonPath("errors[0]").value(mensagemErro));
		
	}
	
	@Test
	public void deveRecuperarLivroPorId() throws Exception {
		Livro livroRetornado = criarNovoLivroComId();		
		given(livroService.getById(1L)).willReturn(livroRetornado);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVROS_API_V1.concat("/"+1L))
			.accept(APPLICATION_JSON);
				
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(1L))
			.andExpect(jsonPath("titulo").value(livroRetornado.getTitulo()))
			.andExpect(jsonPath("autor").value(livroRetornado.getAutor()))
			.andExpect(jsonPath("isbn").value(livroRetornado.getIsbn()));
	}
	
	@Test
	public void deveRetornarErroLivroNaoEncontrado() throws Exception {
		String mensagemErro = "Recurso não encontrado";
		given(livroService.getById(anyLong())).willThrow(new EntityNotFoundException());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVROS_API_V1.concat("/"+1L))
			.accept(APPLICATION_JSON);
								
		mockMvc.perform(request)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("errors", hasSize(1)))
			.andExpect(jsonPath("errors[0]").value(mensagemErro));
	}
	
	@Test
	public void deveExcluirLivro() throws Exception {
		Livro livro = criarNovoLivroComId();
		given(livroService.getById(anyLong())).willReturn(livro);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(LIVROS_API_V1.concat("/"+1L))
			.accept(APPLICATION_JSON);
		
		mockMvc.perform(request)
			.andExpect(status().isNoContent());		
	}
	
	@Test
	public void deveRetornarErroLivroNaoEncontradoAoTentarExcluir() throws Exception {		
		willThrow(new EntityNotFoundException()).given(livroService).excluir(anyLong());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(LIVROS_API_V1.concat("/"+1L))
			.accept(APPLICATION_JSON);
		
		mockMvc.perform(request)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("errors", hasSize(1)))
			.andExpect(jsonPath("errors[0]").value("Recurso não encontrado"));
	}
	
	@Test
	public void deveAtualizarUmLivro() throws Exception {
		LivroDTO livroDTO = criarNovoLivroDTO();
		livroDTO.setId(1L);
		Livro livro = criarNovoLivroComId();

		given(livroService.atualizar(any(Livro.class))).willReturn(livro);
		String json = new ObjectMapper().writeValueAsString(livroDTO);
				
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(LIVROS_API_V1)
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(livroDTO.getId()))
			.andExpect(jsonPath("titulo").value(livroDTO.getTitulo()))
			.andExpect(jsonPath("autor").value(livroDTO.getAutor()))
			.andExpect(jsonPath("isbn").value(livroDTO.getIsbn()));		
	}
	
	@Test
	public void deveRetornarErroLivroNaoEncontradoAoTentarAtualizar() throws Exception {
		LivroDTO livroDTO = criarNovoLivroDTO();
		livroDTO.setId(1L);
		
		given(livroService.atualizar(any(Livro.class))).willThrow(new EntityNotFoundException());
		String json = new ObjectMapper().writeValueAsString(livroDTO);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(LIVROS_API_V1)
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("errors", hasSize(1)))
			.andExpect(jsonPath("errors[0]").value("Recurso não encontrado"));
	}
	
}
