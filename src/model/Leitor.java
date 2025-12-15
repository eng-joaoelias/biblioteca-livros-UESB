package model;

import java.io.Serializable;

/**
 * Classe que representa um Leitor (cliente) da biblioteca, ou seja,
 * a pessoa que pode pegar um livro emprestado.
 * <p>
 * Esta classe herda atributos básicos (nome, id) e comportamento (equals, hashCode, compareTo)
 * da classe {@link Pessoa}, utilizando o conceito de Herança.
 * Implementa {@link Serializable} para suportar persistência em arquivos.
 * </p>
 */
public class Leitor extends Pessoa implements Serializable {
	
	// Adicionado para suportar serialização.
	private static final long serialVersionUID = 1L; 
	
	// Atributos específicos do Leitor
	private String telefone;
	private String email;
	
	/**
	 * Construtor completo para criar uma nova instância de Leitor.
	 * <p>Chama o construtor da classe base (Pessoa) para inicializar nome e ID.</p>
	 * @param nome O nome completo do leitor.
	 * @param telefone O número de telefone de contato do leitor.
	 * @param email O endereço de e-mail do leitor.
	 */
	public Leitor(String nome, String telefone, String email) {
		super(nome); // Chama o construtor de Pessoa para setar nome e ID
		this.telefone = telefone;
		this.email = email;
	}
	
	/**
	 * Construtor vazio (necessário para processos de desserialização).
	 */
	public Leitor() {
		super();
	}

	// --- Getters e Setters específicos de Leitor ---

	/**
	 * Retorna o número de telefone do leitor.
	 * @return O telefone do leitor.
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * Define o número de telefone do leitor.
	 * @param telefone O novo número de telefone.
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Retorna o endereço de e-mail do leitor.
	 * @return O e-mail do leitor.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Define o endereço de e-mail do leitor.
	 * @param email O novo endereço de e-mail.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	// --- Métodos de Representação ---

	/**
	 * Retorna a representação textual formatada do Leitor.
	 * O formato é: "Nome (Telefone: [telefone])".
	 * @return Uma string representando o leitor.
	 */
	@Override
	public String toString() {
		// O método getNome() é herdado da classe Pessoa
		return getNome() + " (Telefone: " + telefone + ")";
	}
	
	// Os métodos equals(), hashCode() e compareTo() são herdados da classe Pessoa.
}