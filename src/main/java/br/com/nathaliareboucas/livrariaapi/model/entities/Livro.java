package br.com.nathaliareboucas.livrariaapi.model.entities;

import static java.util.Objects.nonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nathaliareboucas.livrariaapi.exceptions.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LIVRO")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITULO")
	private String titulo;
	
	@Column(name = "AUTOR")
	private String autor;
	
	@Column(name = "ISBN")
	private String isbn;

	public void atualizaPropriedades(Livro livro) {
		if (nonNull(livro.getIsbn()) && !livro.getIsbn().equals(this.isbn)) {
			throw new NegocioException("O ISBN do livro não pode ser alterado");
		}
		
		if (nonNull(livro.getTitulo())) {
			this.titulo = livro.getTitulo();
		}
		
		if (nonNull(livro.getAutor())) {
			this.autor = livro.getAutor();
		}		
	}
	
}
