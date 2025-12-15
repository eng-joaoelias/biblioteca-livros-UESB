package main;

import java.util.ArrayList;
import java.util.Scanner;

import model.Autor;
import model.Leitor;
import model.Livro;
import service.GerenciadorBiblioteca;
import service.GerenciadorBiblioteca.TipoOrdenacao;

/**
 * Classe principal para a aplicação de console da Biblioteca.
 * <p>
 * Responsável pela interação com o usuário (apresentação do menu) e por
 * coordenar as chamadas à camada de serviço (GerenciadorBiblioteca),
 * demonstrando o uso de todas as classes de Modelo e Serviço.
 * </p>
 */
public class App {

    private static GerenciadorBiblioteca gerenciador;
    private static Scanner scanner;
    
    // Lista auxiliar para arazenar Leitores (é um sistema de cadastro de livros, o foco não é de pessoas)
    private static ArrayList<Leitor> listaLeitores;

    public static void main(String[] args) {
        gerenciador = new GerenciadorBiblioteca();
        scanner = new Scanner(System.in);
        
        // Inicialização de Leitores
        listaLeitores = new ArrayList<>();
        inicializarLeitores();
        
        System.out.println("Sistema de Biblioteca Iniciado. Dados carregados da persistência.");
        exibirMenuPrincipal();
        
        System.out.println("Obrigado por utilizar o sistema da Biblioteca!");
        scanner.close();
    }
    
    /**
     * Cria alguns leitores de exemplo para facilitar os testes de empréstimo.
     */
    private static void inicializarLeitores() {
        listaLeitores.add(new Leitor("Ana Silva", "(11) 98765-4321", "ana@email.com"));
        listaLeitores.add(new Leitor("Bruno Costa", "(21) 91234-5678", "bruno@email.com"));
        listaLeitores.add(new Leitor("Carlos Rocha", "(31) 99999-0000", "carlos@email.com"));
    }

    /**
     * Exibe o menu principal de interação e gerencia as escolhas do usuário.
     */
    private static void exibirMenuPrincipal() {
        int opcao;
        
        do {
            System.out.println("\n===== Menu da Biblioteca =====");
            System.out.println("1. Adicionar Novo Livro");
            System.out.println("2. Listar Livros (Ordenação)");
            System.out.println("3. Buscar Livro por ID/Título");
            System.out.println("4. Remover Livro");
            System.out.println("5. Realizar Empréstimo / Devolução");
            System.out.println("6. Listar Livros Emprestados a um Leitor");
            System.out.println("0. Sair e Salvar Dados");
            System.out.print("Escolha uma opção: ");
            
            // Tratamento de exceção para entrada de menu
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1; // Valor inválido para repetição do loop
                System.err.println("Entrada inválida. Digite um número de 0 a 6.");
            }

            switch (opcao) {
                case 1:
                    adicionarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    buscarLivro();
                    break;
                case 4:
                    removerLivro();
                    break;
                case 5:
                    menuEmprestimoDevolucao();
                    break;
                case 6:
                    listarLivrosEmprestados();
                    break;
                case 0:
                    // O gerenciador salva no construtor e nas alterações, então sair é suficiente.
                    break;
                default:
                    if (opcao != -1) {
                         System.out.println("Opção não reconhecida.");
                    }
                    break;
            }
            
        } while (opcao != 0);
    }
    
    // =========================================================================
    // Métodos de Ação do Menu
    // =========================================================================

    /**
     * Processa a entrada de dados do usuário e adiciona um novo livro.
     */
    private static void adicionarLivro() {
        System.out.println("\n--- Adicionar Livro ---");
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Nome do Autor: ");
        String nomeAutor = scanner.nextLine();
        
        System.out.print("Nacionalidade do Autor: ");
        String nacionalidadeAutor = scanner.nextLine();
        
        // Criação de objeto Autor
        Autor autor = new Autor(nomeAutor, nacionalidadeAutor);
        
        int numPags = lerInteiro("Número de Páginas: ");
        int anoPub = lerInteiro("Ano de Publicação: ");
        double preco = lerDouble("Preço (R$): ");

        boolean sucesso = gerenciador.addLivro(titulo, autor, numPags, anoPub, preco);

        if (sucesso) {
            System.out.println("\nLivro adicionado com sucesso!");
        } else {
            System.out.println("\nErro: Livro já existe na coleção (verifique título e autor).");
        }
    }

    /**
     * Exibe as opções de listagem e ordenação dos livros.
     */
    private static void listarLivros() {
        if (gerenciador.getListaLivros().isEmpty()) {
            System.out.println("\nA biblioteca está vazia. Adicione livros primeiro.");
            return;
        }

        System.out.println("\n--- Opções de Listagem e Ordenação ---");
        System.out.println("1. Listar todos (Título - Ordenação Natural)");
        System.out.println("2. Listar todos (Autor)");
        System.out.println("3. Listar por um Autor Específico");
        System.out.print("Escolha: ");
        
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Entrada inválida.");
            return;
        }

        try {
            switch (escolha) {
                case 1:
                    gerenciador.ordenarLivros(TipoOrdenacao.TITULO);
                    exibirLista(gerenciador.getListaLivros(), "TODOS OS LIVROS (Ordenado por Título)");
                    break;
                case 2:
                    gerenciador.ordenarLivros(TipoOrdenacao.AUTOR);
                    exibirLista(gerenciador.getListaLivros(), "TODOS OS LIVROS (Ordenado por Autor)");
                    break;
                case 3:
                    listarPorAutorEspecifico();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (IllegalArgumentException e) {
            // Captura a exceção específica para tipo de ordenação não suportado
            System.err.println("Erro de Ordenação: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos os leitores disponíveis para seleção.
     */
    private static void listarLeitores() {
        System.out.println("\n--- Leitores Cadastrados ---");
        if (listaLeitores.isEmpty()) {
            System.out.println("Nenhum leitor cadastrado.");
            return;
        }
        for (int i = 0; i < listaLeitores.size(); i++) {
            // Exibe o ID interno (índice) para seleção rápida
            System.out.println((i + 1) + ". " + listaLeitores.get(i).toString());
        }
    }
    
    /**
     * Lista os livros emprestados a um leitor selecionado.
     */
    private static void listarLivrosEmprestados() {
        System.out.println("\n--- Livros Emprestados por Leitor ---");
        listarLeitores();
        
        int indiceLeitor = lerInteiro("Selecione o número do Leitor (1 a " + listaLeitores.size() + "): ") - 1;
        
        if (indiceLeitor < 0 || indiceLeitor >= listaLeitores.size()) {
            System.out.println("Seleção de leitor inválida.");
            return;
        }
        
        Leitor leitorSelecionado = listaLeitores.get(indiceLeitor);
        
        ArrayList<Livro> livrosEmprestados = gerenciador.listLivrosEmprestadosPara(leitorSelecionado);
        exibirLista(livrosEmprestados, "LIVROS EMPRESTADOS A: " + leitorSelecionado.getNome());
    }
    
    /**
     * Pede ao usuário o nome de um autor e lista os livros dele.
     */
    private static void listarPorAutorEspecifico() {
        System.out.print("Nome do Autor para busca (Precisa ser exato): ");
        String nomeBusca = scanner.nextLine();
        
        // Procura o primeiro Livro com esse Autor
        Autor autorBusca = null;
        for (Livro livro : gerenciador.getListaLivros()) {
            if (livro.getAutor().getNome().equalsIgnoreCase(nomeBusca)) {
                autorBusca = livro.getAutor();
                break;
            }
        }
        
        if (autorBusca == null) {
            System.out.println("\nAutor não encontrado na coleção. Tente um nome exato.");
            return;
        }
        
        // Uso do método de filtragem do Gerenciador
        ArrayList<Livro> livrosDoAutor = gerenciador.listLivrosPorAutor(autorBusca);
        exibirLista(livrosDoAutor, "Livros do Autor: " + autorBusca.getNome());
    }

    /**
     * Busca um livro pelo ID ou Título e exibe suas informações.
     */
    private static void buscarLivro() {
        System.out.println("\n--- Buscar Livro ---");
        System.out.print("Buscar por ID ou Título? (ID/T): ");
        String tipoBusca = scanner.nextLine().toUpperCase();
        
        Livro livro = null;
        
        if (tipoBusca.equals("ID")) {
            System.out.print("Digite o ID único: ");
            String idBusca = scanner.nextLine();
            livro = gerenciador.buscarLivroPorID(idBusca);
            
        } else if (tipoBusca.equals("T")) {
            System.out.print("Digite o Título exato: ");
            String tituloBusca = scanner.nextLine();
            livro = gerenciador.exibeLivro(tituloBusca);
            
        } else {
            System.out.println("Tipo de busca inválido.");
            return;
        }

        if (livro != null) {
            System.out.println("\nLivro Encontrado:");
            System.out.println(livro.exibeInformacoes());
        } else {
            System.out.println("\nLivro não encontrado.");
        }
    }

    /**
     * Remove um livro da coleção.
     */
    private static void removerLivro() {
        System.out.println("\n--- Remover Livro ---");
        System.out.print("Digite o ID do livro a ser removido: ");
        String idRemocao = scanner.nextLine();

        boolean sucesso = gerenciador.remLivro(idRemocao);

        if (sucesso) {
            System.out.println("\nLivro removido com sucesso! (Dados salvos)");
        } else {
            System.out.println("\nErro: Livro não encontrado.");
        }
    }
    
    /**
     * Exibe o submenu para Empréstimo ou Devolução.
     */
    private static void menuEmprestimoDevolucao() {
        System.out.println("\n--- Empréstimo / Devolução ---");
        System.out.println("1. Emprestar Livro");
        System.out.println("2. Devolver Livro");
        System.out.print("Escolha a operação: ");
        
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Entrada inválida.");
            return;
        }
        
        switch(escolha) {
            case 1:
                emprestarLivro();
                break;
            case 2:
                devolverLivro();
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
    
    /**
     * Realiza a lógica de empréstimo de um livro para um leitor.
     */
    private static void emprestarLivro() {
        System.out.println("\n--- Realizar Empréstimo ---");
        System.out.print("Digite o ID do livro para empréstimo: ");
        String idLivro = scanner.nextLine();
        
        Livro livro = gerenciador.buscarLivroPorID(idLivro);
        if (livro == null) {
            System.out.println("Livro não encontrado com o ID: " + idLivro);
            return;
        }
        
        if (livro.getLeitorEmprestimo() != null) {
            System.out.println("O livro já está emprestado para: " + livro.getLeitorEmprestimo().getNome());
            return;
        }
        
        //Seleção do Leitor
        listarLeitores();
        int indiceLeitor = lerInteiro("Selecione o número do Leitor (1 a " + listaLeitores.size() + "): ") - 1;
        
        if (indiceLeitor < 0 || indiceLeitor >= listaLeitores.size()) {
            System.out.println("Seleção de leitor inválida.");
            return;
        }
        
        Leitor leitorSelecionado = listaLeitores.get(indiceLeitor);
        
        //Uso do método editLivro para atribuir o objeto Leitor
        boolean sucesso = gerenciador.editLivro(
            livro.getIdLivro(), 
            livro.getTitulo(), 
            livro.getAutor(), 
            livro.getNumPags(), 
            livro.getAnoPub(), 
            livro.getPreco(), 
            leitorSelecionado // Atribui o objeto Leitor
        );
        
        if (sucesso) {
            System.out.println("\nLivro '" + livro.getTitulo() + "' emprestado com sucesso para " + leitorSelecionado.getNome() + ". (Dados salvos)");
        } else {
            System.out.println("\nErro ao registrar o empréstimo.");
        }
    }
    
    /**
     * Realiza a lógica de devolução de um livro.
     */
    private static void devolverLivro() {
        System.out.println("\n--- Realizar Devolução ---");
        System.out.print("Digite o ID do livro para devolução: ");
        String idLivro = scanner.nextLine();
        
        Livro livro = gerenciador.buscarLivroPorID(idLivro);
        if (livro == null) {
            System.out.println("Livro não encontrado com o ID: " + idLivro);
            return;
        }
        
        if (livro.getLeitorEmprestimo() == null) {
            System.out.println("O livro já está disponível. Não foi possível realizar a devolução.");
            return;
        }
        
        String nomeLeitor = livro.getLeitorEmprestimo().getNome();
        
        // Uso do método editLivro para setar o Leitor como null (Devolução)
        boolean sucesso = gerenciador.editLivro(
            livro.getIdLivro(), 
            livro.getTitulo(), 
            livro.getAutor(), 
            livro.getNumPags(), 
            livro.getAnoPub(), 
            livro.getPreco(), 
            null // Define como null para devolução
        );
        
        if (sucesso) {
            System.out.println("\nLivro '" + livro.getTitulo() + "' devolvido com sucesso por " + nomeLeitor + ". (Dados salvos)");
        } else {
            System.out.println("\nErro ao registrar a devolução.");
        }
    }


    // =========================================================================
    // Métodos Auxiliares
    // =========================================================================

    /**
     * Exibe a lista de livros formatada.
     * @param lista A lista de livros a ser exibida.
     * @param titulo O título da seção de listagem.
     */
    private static void exibirLista(ArrayList<Livro> lista, String titulo) {
        System.out.println("\n-----------------------------------------");
        System.out.println("     " + titulo.toUpperCase());
        System.out.println("-----------------------------------------");
        
        if (lista.isEmpty()) {
            System.out.println("Nenhum livro encontrado para este critério.");
            return;
        }
        
        for (Livro livro : lista) {
            // Imprime o ID para facilitar operações de busca/remoção
            System.out.println("[ID: " + livro.getIdLivro() + "]");
            System.out.println(livro.exibeInformacoes());
            System.out.println("---");
        }
    }
    
    /**
     * Método auxiliar para garantir que o usuário digite um número inteiro válido.
     * @param prompt A mensagem a ser exibida para o usuário.
     * @return O valor inteiro digitado.
     */
    private static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Tratamento de exceção específico
                System.err.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    /**
     * Método auxiliar para garantir que o usuário digite um número decimal válido (double).
     * @param prompt A mensagem a ser exibida para o usuário.
     * @return O valor decimal (double) digitado.
     */
    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                // Tenta ler o valor, substituindo vírgulas por pontos para consistência
                String entrada = scanner.nextLine().replace(',', '.');
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                // Tratamento de exceção específico
                System.err.println("Entrada inválida. Por favor, digite um número decimal (ex: 19.99 ou 19,99).");
            }
        }
    }
}