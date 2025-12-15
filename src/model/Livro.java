package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe que representa um Livro na biblioteca.
 * <p>
 * Implementa {@link Comparable} para permitir a ordenação natural dos livros pelo título
 * e {@link Serializable} para suportar persistência em arquivos.
 * O empréstimo agora é representado pela referência a um objeto {@link Leitor}.
 * </p>
 */
public class Livro implements Comparable<Livro>, Serializable {
	
	// Adicionado para suportar serialização. É uma prática recomendada.
	private static final long serialVersionUID = 1L; 

	private String idLivro;
	private String titulo;
	private Autor autor; // Referência ao objeto Autor
	private int numPags;
	private int anoPub;
	private double preco;
	
	// Referencia o objeto Leitor para quem o livro foi emprestado.
	private Leitor leitorEmprestimo; // null se não estiver emprestado
	
	
	/**
	 * Construtor principal para adicionar um novo livro à coleção.
	 * <p>Um {@code idLivro} único é gerado automaticamente usando {@link UUID}.</p>
	 * * @param titulo O título do livro.
	 * @param autor O objeto {@link Autor} do livro.
	 * @param numPags O número total de páginas.
	 * @param anoPub O ano de publicação.
	 * @param preco O preço do livro.
	 */
	public Livro(String titulo, Autor autor, int numPags, int anoPub, double preco) {
		this.idLivro = UUID.randomUUID().toString(); // Gera um ID único e aleatório
		this.titulo = titulo;
		this.autor = autor;
		this.numPags = numPags;
		this.anoPub = anoPub;
		this.preco = preco;
		this.leitorEmprestimo = null; // Inicializa como não emprestado
	}
	
	/**
	 * Construtor vazio (necessário para processos de serialização/desserialização).
	 */
	public Livro() {
	}

	// --- Getters e Setters ---
    /**
     * Retorna o identificador único do livro.
     * @return O ID único do livro.
     */
    public String getIdLivro() {
        return idLivro;
    }
	/**
	 * Retorna o título do livro.
	 * @return O título.
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Define o título do livro.
	 * @param titulo O novo título.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Retorna o objeto Autor do livro.
	 * @return O {@link Autor} do livro.
	 */
	public Autor getAutor() {
		return autor;
	}
	/**
	 * Define o objeto Autor do livro.
	 * @param autor O novo {@link Autor} do livro.
	 */
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	/**
	 * Retorna o número de páginas.
	 * @return O número de páginas.
	 */
	public int getNumPags() {
		return numPags;
	}
	/**
	 * Define o número de páginas.
	 * @param numPags O novo número de páginas.
	 */
	public void setNumPags(int numPags) {
		this.numPags = numPags;
	}
	/**
	 * Retorna o ano de publicação.
	 * @return O ano de publicação.
	 */
	public int getAnoPub() {
		return anoPub;
	}
	/**
	 * Define o ano de publicação.
	 * @param anoPub O novo ano de publicação.
	 */
	public void setAnoPub(int anoPub) {
		this.anoPub = anoPub;
	}
	/**
	 * Retorna o preço do livro.
	 * @return O preço do livro.
	 */
	public double getPreco() {
		return preco;
	}
	/**
	 * Define o preço do livro.
	 * @param preco O novo preço.
	 */
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	/**
	 * Retorna o objeto Leitor para o qual o livro foi emprestado.
	 * @return O objeto {@link Leitor}, ou {@code null} se estiver disponível.
	 */
	public Leitor getLeitorEmprestimo() {
		return leitorEmprestimo;
	}
	
	/**
	 * Define o objeto Leitor para o qual o livro foi emprestado (para empréstimo)
	 * ou define como {@code null} (para devolução).
	 * @param leitorEmprestimo O objeto {@link Leitor}.
	 */
	public void setLeitorEmprestimo(Leitor leitorEmprestimo) {
		this.leitorEmprestimo = leitorEmprestimo;
	}
	
	/**
	 * Retorna uma string formatada com todas as informações do livro.
	 * O preço é formatado para duas casas decimais.
	 * @return Uma string detalhada do livro.
	 */
	public String exibeInformacoes() {
		String emprestado;
		if (leitorEmprestimo != null) {
		    // Usa o toString() da classe Leitor
			emprestado = "\nEmprestado para: " + leitorEmprestimo.toString(); 
		} else {
			emprestado = "\nStatus: Disponível";
		}
			
		return "--- Livro ---\n" 
			+ "Título: " + titulo 
			+ "\nAutor: " + autor.toString() 
			+ "\nNúmero de págs.: " + numPags 
			+ "\nAno de publicação: " + anoPub
			+ "\nPreço: R$" + String.format("%.2f", preco) 
			+ emprestado;
	}

	// --- Métodos Essenciais ---
	
	/**
	 * Compara este Livro com outro objeto para verificar igualdade.
	 * <p>Dois livros são considerados iguais se tiverem o mesmo {@code idLivro}.</p>
	 * * @param o O objeto a ser comparado.
	 * @return {@code true} se os objetos forem considerados iguais, {@code false} caso contrário.
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Livro livro = (Livro) o;
	    // O objeto é igual apenas se o ID for o mesmo.
	    return Objects.equals(idLivro, livro.idLivro); 
	}

	/**
	 * Gera um código hash para o objeto Livro.
	 * <p>É essencial que este método seja sobrescrito juntamente com {@code equals()}.</p>
	 * * @return O código hash, baseado no título e autor.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(titulo, autor);
	}
	
	/**
	 * Implementa a ordenação natural (por título) para a classe Livro.
	 * * @param outroLivro O livro a ser comparado.
	 * @return Um valor negativo, zero ou positivo se este livro for alfabeticamente
	 * menor, igual ou maior que o livro especificado.
	 */
	@Override
	public int compareTo(Livro outroLivro) {
		return this.titulo.compareToIgnoreCase(outroLivro.titulo);
	}
	
	
}