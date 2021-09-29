package br.com.nathaliareboucas.livrariaapi.resources;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
	
	@Test
	public void deveCriarUmLivro() throws Exception {
	
		final LivroDTO livroDTO = criarNovoLivroDTO();
		final Livro livroSalvo = Livro.builder().id(1L).titulo("Meu Livro").autor("Autor").isbn("A123456").build();
		
		BDDMockito.given(livroService.salvar(Mockito.any(Livro.class))).willReturn(livroSalvo);
		String json = new ObjectMapper().writeValueAsString(livroDTO);
		
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVROS_API_V1)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("titulo").value(livroDTO.getTitulo()))
			.andExpect(MockMvcResultMatchers.jsonPath("autor").value(livroDTO.getAutor()))
			.andExpect(MockMvcResultMatchers.jsonPath("isbn").value(livroDTO.getIsbn()));
	}
	
	@Test
	public void deveLancarErroValidacaoDadosInsuficientes() throws Exception {
		String json = new ObjectMapper().writeValueAsString(new LivroDTO());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVROS_API_V1)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", Matchers.hasSize(3)));
	}
	
	@Test
	public void naoDeveCriarLivroComIsbnExistente() throws Exception {
		LivroDTO livroDTO = criarNovoLivroDTO();
		String json = new ObjectMapper().writeValueAsString(livroDTO);
		String mensagemErro = "ISBN já cadastrado";

		BDDMockito.given(livroService.salvar(Mockito.any(Livro.class))).willThrow(new NegocioException(mensagemErro));		

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVROS_API_V1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", Matchers.hasSize(1)))
			.andExpect(jsonPath("errors[0]").value(mensagemErro));
		
	}
	
	@Test
	public void deveRecuperarLivroPorId() throws Exception {
		Long id = 1L;
		Livro livroRetornado = Livro.builder().id(id).titulo("Livro teste").autor("Autor teste").isbn("123").build();
		
		BDDMockito.given(livroService.getById(id)).willReturn(Optional.of(livroRetornado));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVROS_API_V1.concat("/"+id))
			.accept(MediaType.APPLICATION_JSON);
				
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("titulo").value(livroRetornado.getTitulo()))
			.andExpect(MockMvcResultMatchers.jsonPath("autor").value(livroRetornado.getAutor()))
			.andExpect(MockMvcResultMatchers.jsonPath("isbn").value(livroRetornado.getIsbn()));
	}
	
	private LivroDTO criarNovoLivroDTO() {
		return LivroDTO.builder().titulo("Meu Livro").autor("Autor").isbn("A123456").build();
	}
	
}
