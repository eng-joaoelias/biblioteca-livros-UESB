package service;

import java.util.ArrayList;
import java.util.Comparator;

import model.Autor;
import model.Leitor;
import model.Livro;

/**
 * Classe responsável por gerenciar a coleção de livros.
 * <p>
 * Contém a lógica de negócio para adicionar, remover, editar, listar e ordenar
 * os livros da biblioteca. Integra a persistência de dados através da classe {@link Persistencia}.
 * </p>
 */
public class GerenciadorBiblioteca {

	/**
	 * Lista principal de livros da biblioteca, armazenada como {@link ArrayList}.
	 */
	private ArrayList<Livro> listaLivros;
	
	/**
	 * Objeto responsável por carregar e salvar a lista de livros no arquivo.
	 */
	private final Persistencia persistencia;
	
	/**
     * Enum para representar as opções de ordenação disponíveis para a coleção de livros.
     */
    public enum TipoOrdenacao {
        /** Ordenar por Título */
        TITULO,
        /** Ordenar por Autor */
        AUTOR
    }

	/**
	 * Construtor que inicializa a lista de livros e o objeto de persistência.
	 * A lista é carregada do arquivo na inicialização.
	 */
	public GerenciadorBiblioteca() {
		this.persistencia = new Persistencia();
		// Tenta carregar os dados persistidos ao iniciar
		this.listaLivros = persistencia.carregarLivros();
		
		// Se o carregamento falhar (ou se o arquivo estiver vazio/não existir), inicia uma lista vazia
		if (this.listaLivros == null) {
			this.listaLivros = new ArrayList<>();
		}
	}

	/**
	 * Adiciona um novo livro à coleção e salva a lista.
	 * * @param titulo O título do livro.
	 * @param autor O objeto {@link Autor} do livro.
	 * @param numPags O número de páginas.
	 * @param anoPub O ano de publicação.
	 * @param preco O preço do livro.
	 * @return {@code true} se o livro for adicionado com sucesso, {@code false} caso já exista.
	 */
	public boolean addLivro(String titulo, Autor autor, int numPags, int anoPub, double preco) {

		Livro livroAdd = new Livro(titulo, autor, numPags, anoPub, preco);

		if (listaLivros.contains(livroAdd)) {
			return false;
		}

		listaLivros.add(livroAdd);
		persistencia.salvarLivros(listaLivros); // Salva após alteração
		return true;
	}

	/**
	 * Busca um livro na coleção usando o seu ID único.
	 * * @param idLivro O ID único do livro a ser procurado.
	 * @return O objeto {@link Livro} encontrado ou {@code null} caso não exista.
	 */
	public Livro buscarLivroPorID(String idLivro) {
		if (idLivro == null || idLivro.isEmpty()) return null;

		for (Livro livro : listaLivros) {
			// Usa o método getIdLivro()
			if (livro.getIdLivro().equals(idLivro)) {
				return livro;
			}
		}
		return null;
	}

	/**
	 * Retorna o índice (posição na lista) do livro com o ID informado.
	 * * @param idLivro O ID único do livro.
	 * @return O índice do livro na lista ou {@code -1} caso não seja encontrado.
	 */
	public int buscarIndiceLivroPorID(String idLivro) {
		if (idLivro == null || idLivro.isEmpty()) return -1;

		for (int i = 0; i < listaLivros.size(); i++) {
			if (listaLivros.get(i).getIdLivro().equals(idLivro)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Remove um livro da coleção usando o seu ID e salva a lista.
	 * * @param idLivro O ID único do livro a ser removido.
	 * @return {@code true} se o livro for encontrado e removido, {@code false} caso não exista.
	 */
	public boolean remLivro(String idLivro) {
		int indice = this.buscarIndiceLivroPorID(idLivro);

		if (indice != -1) {
			listaLivros.remove(indice);
			persistencia.salvarLivros(listaLivros); // Salva após alteração
			return true;
		}

		return false;
	}

	/**
	 * Edita todas as informações de um livro existente, encontrado pelo seu ID, e salva a lista.
	 * * @param idLivro O ID único do livro a ser editado.
	 * @param titulo O novo título do livro.
	 * @param autor O novo objeto {@link Autor} do livro.
	 * @param numPags O novo número de páginas.
	 * @param anoPub O novo ano de publicação.
	 * @param preco O novo preço do livro.
	 * @param leitorEmprestimo O objeto {@link Leitor} (ou {@code null} se estiver disponível).
	 * @return {@code true} se o livro for encontrado e as informações forem atualizadas.
	 */
	public boolean editLivro(String idLivro, String titulo, Autor autor, int numPags, 
			int anoPub, double preco, Leitor leitorEmprestimo) {

		int indice = this.buscarIndiceLivroPorID(idLivro);

		if (indice != -1) {
			Livro livro = listaLivros.get(indice);

			// Atualiza os dados básicos
			livro.setTitulo(titulo);
			livro.setAutor(autor);
			livro.setNumPags(numPags);
			livro.setAnoPub(anoPub);
			livro.setPreco(preco);
			
			// ATUALIZADO: Define o objeto Leitor
			livro.setLeitorEmprestimo(leitorEmprestimo);

			persistencia.salvarLivros(listaLivros); // Salva após alteração
			return true;
		}

		return false;
	}

	/**
	 * Retorna uma sublista contendo todos os livros escritos por um autor específico.
	 * * @param autor O objeto {@link Autor} para filtrar a lista.
	 * @return Uma {@link ArrayList} de livros do autor especificado.
	 */
	public ArrayList<Livro> listLivrosPorAutor(Autor autor) {
		ArrayList<Livro> livrosDoAutor = new ArrayList<>();

		for (Livro livro : listaLivros) {
			if (livro.getAutor().equals(autor)) {
				livrosDoAutor.add(livro);
			}
		}

		return livrosDoAutor;
	}

	/**
	 * Retorna uma sublista contendo todos os livros que estão emprestados para um leitor específico.
	 * * @param leitor O objeto {@link Leitor} que emprestou o livro.
	 * @return Uma {@link ArrayList} de livros emprestados ao leitor.
	 */
	public ArrayList<Livro> listLivrosEmprestadosPara(Leitor leitor) {
		ArrayList<Livro> livrosDoEmprestimo = new ArrayList<>();

		for (Livro livro : listaLivros) {
			// ATUALIZADO: Compara objetos Leitor
			if (livro.getLeitorEmprestimo() != null &&
				livro.getLeitorEmprestimo().equals(leitor)) {

				livrosDoEmprestimo.add(livro);
			}
		}

		return livrosDoEmprestimo;
	}

	/**
	 * Busca um livro pelo título (ignora maiúsculas/minúsculas - case-insensitive).
	 * * @param titulo O título a ser procurado.
	 * @return O objeto {@link Livro} encontrado ou {@code null} se o livro não for encontrado.
	 */
	public Livro exibeLivro(String titulo) {
		if (titulo == null || titulo.trim().isEmpty()) return null;

		for (Livro livro : listaLivros) {
			if (livro.getTitulo().equalsIgnoreCase(titulo)) {
				return livro;
			}
		}

		return null;
	}
	
	/**
     * Ordena a lista de livros com base no tipo de ordenação escolhido.
     * <p>Substitui o uso de {@code throws Exception} genérico por uma exceção mais específica.</p>
     * * @param tipo O {@link TipoOrdenacao} desejado (TITULO ou AUTOR).
	 * @return A lista de livros ordenada.
     * @throws IllegalArgumentException Se o tipo de ordenação não for suportado.
     */
    public ArrayList<Livro> ordenarLivros(TipoOrdenacao tipo) throws IllegalArgumentException {
        
        // Definição do Comparator com base na escolha
        Comparator<Livro> comparator;

        if (tipo == TipoOrdenacao.TITULO) { // Ordenação por título
            // Usa a ordenação natural definida em Livro (compareTo)
            comparator = Comparator.naturalOrder(); 
            
        } else if (tipo == TipoOrdenacao.AUTOR) { // Ordenação por autor
            // Cria um Comparator que compara primeiro o objeto Autor, usando o nome (em minúsculas)
            comparator = Comparator.comparing(Livro::getAutor, 
                                             Comparator.comparing(a -> a.getNome().toLowerCase()));
            
        } else {
        	// Substituição do throws Exception genérico pelo IllegalArgumentException
        	throw new IllegalArgumentException("Tipo de ordenação não suportado: " + tipo);
        }

        // Aplica o Comparator à lista
        this.listaLivros.sort(comparator);
        
        // Salva a lista após a ordenação (se a ordenação for um estado persistente)
        // Se a ordenação for apenas para exibição, esta linha pode ser removida.
        persistencia.salvarLivros(listaLivros);
        
        return this.listaLivros;
    }

	/**
	 * Retorna a lista completa e atualizada de livros.
	 * @return A {@link ArrayList} de {@link Livro}s.
	 */
	public ArrayList<Livro> getListaLivros() {
		return listaLivros;
	}
}