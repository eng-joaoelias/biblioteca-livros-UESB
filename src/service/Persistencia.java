package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Livro;

/**
 * Classe responsável por lidar com a persistência de dados.
 * <p>
 * Utiliza o conceito de Serialização (ObjectOutputStream/ObjectInputStream)
 * para salvar e carregar a lista de livros em um arquivo binário.
 * </p>
 */
public class Persistencia {
    
    // Nome do arquivo onde a lista de livros será salva.
    private static final String NOME_ARQUIVO = "biblioteca_livros.dat";

    /**
     * Salva a lista de livros em um arquivo no sistema de arquivos.
     * <p>Utiliza um bloco try-with-resources para garantir que o fluxo (stream) seja fechado.</p>
     * * @param listaLivros A {@link ArrayList} de {@link Livro} a ser salva.
     * @return {@code true} se o salvamento foi bem-sucedido, {@code false} caso contrário.
     */
    public boolean salvarLivros(ArrayList<Livro> listaLivros) {
        // Uso de try-with-resources para garantir o fechamento do ObjectOutputStream
        try (FileOutputStream fos = new FileOutputStream(NOME_ARQUIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(listaLivros);
            return true;
            
        } catch (IOException e) {
            // Tratamento de Exceção específico para erros de I/O
            System.err.println("Erro ao salvar lista de livros: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carrega a lista de livros de um arquivo.
     * <p>Utiliza um bloco try-with-resources para garantir que o fluxo (stream) seja fechado.</p>
     * * @return A {@link ArrayList} de {@link Livro} carregada ou uma lista vazia se o arquivo não existir ou houver erro.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Livro> carregarLivros() {
        File arquivo = new File(NOME_ARQUIVO);

        // Se o arquivo não existe, retorna uma lista vazia e não tenta carregar
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileInputStream fis = new FileInputStream(arquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            // Lança um aviso para a conversão não verificada (unchecked cast)
            Object objetoLido = ois.readObject();
            if (objetoLido instanceof ArrayList) {
                // Conversão para ArrayList<Livro>
                return (ArrayList<Livro>) objetoLido;
            } else {
                System.err.println("O objeto lido não é uma lista de livros válida.");
                return new ArrayList<>();
            }
            
        } catch (IOException e) {
            // Tratamento de Exceção específico para erros de I/O
            System.err.println("Erro de I/O ao carregar lista de livros: " + e.getMessage());
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            // Tratamento de Exceção para classe não encontrada (ocorre na desserialização)
            System.err.println("Erro: Classe do objeto lido não encontrada. " + e.getMessage());
            return new ArrayList<>();
        }
    }
}