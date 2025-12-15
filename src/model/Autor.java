package model;

import java.io.Serializable;

/**
 * Classe que representa o Autor de um livro.
 * <p>
 * Esta classe herda atributos básicos (nome, id) e comportamento (equals, hashCode, compareTo)
 * da classe {@link Pessoa}, aplicando o conceito de Herança.
 * Implementa {@link Serializable} para suportar persistência em arquivos.
 * </p>
 */
public class Autor extends Pessoa implements Serializable {
	
	// Adicionado para suportar serialização.
	private static final long serialVersionUID = 1L; 
	
	private String nacionalidade;
	
	/**
	 * Construtor completo para criar uma nova instância de Autor.
	 * <p>Chama o construtor da classe base (Pessoa) para inicializar nome e ID.</p>
	 * @param nome O nome completo do autor.
	 * @param nacionalidade A nacionalidade do autor.
	 */
	public Autor(String nome, String nacionalidade) {
		super(nome); // Chama o construtor de Pessoa para setar nome e ID
		this.nacionalidade = nacionalidade;
	}
	
	/**
	 * Construtor vazio (necessário para processos de desserialização).
	 */
	public Autor() {
		super();
	}

	// --- Getters e Setters específicos de Autor ---
	
	/**
	 * Retorna a nacionalidade do autor.
	 * @return A nacionalidade do autor.
	 */
	public String getNacionalidade() {
		return nacionalidade;
	}
	
	/**
	 * Define a nacionalidade do autor.
	 * @param nacionalidade A nova nacionalidade do autor.
	 */
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	// --- Métodos de Representação ---

	/**
	 * Retorna a representação textual formatada do Autor.
	 * O formato é: "Nome (Nacionalidade)".
	 * @return Uma string representando o autor.
	 */
	@Override
	public String toString() {
		// O método getNome() é herdado da classe Pessoa
		return getNome() + " (" + nacionalidade + ")";
	}
	
	// Os métodos equals(), hashCode() e compareTo() são herdados diretamente da classe Pessoa
	// e utilizam o ID e o Nome para comparação/ordenação.
}