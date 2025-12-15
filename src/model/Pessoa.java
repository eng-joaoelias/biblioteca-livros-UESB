package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe base abstrata para todas as entidades de 'Pessoa' no sistema (Autor, Leitor, etc.).
 * <p>
 * Contém atributos comuns como nome e um identificador único.
 * Implementa {@link Serializable} para suportar persistência em arquivos.
 * </p>
 */
public abstract class Pessoa implements Comparable<Pessoa>, Serializable {
	
	// Adicionado para suportar serialização. É uma prática recomendada.
	private static final long serialVersionUID = 1L; 

	private String id;
	private String nome;
	
	/**
	 * Construtor padrão que inicializa a Pessoa com um nome e gera um ID único.
	 * @param nome O nome completo da pessoa.
	 */
	public Pessoa(String nome) {
		// Gera um ID único na criação
		this.id = UUID.randomUUID().toString(); 
		this.nome = nome;
	}
	
	/**
	 * Construtor vazio (necessário para processos de desserialização).
	 */
	public Pessoa() {
	}

	// --- Getters e Setters ---
	/**
	 * Retorna o identificador único da pessoa.
	 * @return O ID único (String).
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Retorna o nome da pessoa.
	 * @return O nome da pessoa.
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Define o nome da pessoa.
	 * @param nome O novo nome da pessoa.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	// --- Polimorfismo e Métodos Essenciais ---
	
	/**
	 * Compara esta Pessoa com outro objeto para verificar igualdade.
	 * <p>A igualdade é definida pelo {@code id} único.</p>
	 * @param o O objeto a ser comparado.
	 * @return {@code true} se os objetos tiverem o mesmo ID, {@code false} caso contrário.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pessoa pessoa = (Pessoa) o;
		// A igualdade é definida unicamente pelo ID
		return Objects.equals(id, pessoa.id);
	}

	/**
	 * Gera um código hash para o objeto Pessoa.
	 * @return O código hash, baseado no ID.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	/**
	 * Implementa a ordenação natural (por nome) para a classe Pessoa e suas subclasses.
	 * @param outraPessoa A pessoa a ser comparada.
	 * @return Um valor negativo, zero ou positivo se esta pessoa for alfabeticamente
	 * menor, igual ou maior que a pessoa especificada.
	 */
	@Override
	public int compareTo(Pessoa outraPessoa) {
		return this.nome.compareToIgnoreCase(outraPessoa.nome);
	}
}