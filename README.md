# Documentação Técnica – Sistema de Gerenciamento de Biblioteca

## 1. Apresentação do Projeto

Este projeto consiste no desenvolvimento de um **Sistema de Gerenciamento de Biblioteca**, implementado como uma aplicação de console na linguagem **Java**, com o objetivo principal de aplicar, na prática, os conceitos estudados na disciplina de **Programação Orientada a Objetos (POO)**.

O sistema foi projetado para gerenciar uma coleção de livros, permitindo operações básicas de cadastro, consulta, edição e remoção, além do controle de empréstimos realizados por leitores. Durante o desenvolvimento, buscou-se aplicar corretamente conceitos como **herança, encapsulamento, polimorfismo, uso de coleções genéricas, tratamento de exceções e persistência de dados**.

A escolha por uma aplicação de console foi feita por simplicidade e por estar de acordo com o conteúdo abordado em sala de aula, permitindo maior foco na lógica de negócio e na modelagem orientada a objetos.

---

## 2. Funcionalidades do Sistema

As principais funcionalidades implementadas no sistema são:

- **Persistência de dados:**  
  Os livros cadastrados são salvos em arquivo utilizando serialização de objetos, permitindo que os dados sejam mantidos mesmo após o encerramento do programa.

- **Gerenciamento de livros:**  
  É possível adicionar, remover, editar e consultar livros a partir de um identificador único (ID) ou pelo título.

- **Controle de empréstimos:**  
  O sistema permite registrar empréstimos e devoluções, associando cada livro emprestado a um leitor específico.

- **Listagem e organização:**  
  Os livros podem ser listados de forma ordenada por título ou autor, além de filtros por autor específico ou por leitor que possui livros emprestados.

- **Tratamento de erros:**  
  Foram utilizados mecanismos de tratamento de exceções para evitar falhas causadas por entradas inválidas do usuário ou problemas de leitura e escrita em arquivos.

---

## 3. Estrutura do Projeto

O sistema foi organizado seguindo uma **estrutura em camadas**, com o objetivo de separar responsabilidades e facilitar a manutenção do código.

| Camada | Pacote | Descrição |
|------|--------|-----------|
| Modelo | `model` | Contém as classes que representam as entidades do domínio do sistema. |
| Serviço | `service` | Responsável pela lógica de negócio e pela persistência dos dados. |
| Aplicação | `main` | Contém a classe principal responsável pela interação com o usuário via console. |

---

## 4. Descrição das Classes

### 4.1 Pacote `model`

| Classe | Descrição |
|------|-----------|
| `Pessoa` | Classe base abstrata que armazena dados comuns como nome, telefone e e-mail. |
| `Leitor` | Representa um usuário da biblioteca. Herda os atributos da classe `Pessoa`. |
| `Autor` | Armazena informações do autor do livro, como nome e nacionalidade. |
| `Livro` | Classe principal do sistema. Representa um livro e implementa `Comparable<Livro>` para permitir ordenação por título. |

**Relacionamentos:**
- `Livro` possui uma **composição** com `Autor`, pois um livro sempre deve ter um autor.
- `Livro` mantém uma **associação opcional** com `Leitor`, utilizada quando o livro está emprestado.

---

### 4.2 Pacote `service`

| Classe | Descrição |
|------|-----------|
| `Persistencia` | Responsável por salvar e carregar os dados do sistema utilizando serialização. |
| `GerenciadorBiblioteca` | Classe central do sistema, onde estão implementadas as regras de negócio e o controle da lista de livros. |

A classe `GerenciadorBiblioteca` manipula uma `ArrayList<Livro>` e utiliza a classe `Persistencia` sempre que ocorre alguma alteração nos dados.

---

## 5. Aplicação dos Conceitos de POO

Os principais conceitos de Programação Orientada a Objetos foram aplicados da seguinte forma:

| Conceito | Aplicação |
|--------|----------|
| Herança | A classe `Leitor` herda atributos e métodos da classe `Pessoa`. |
| Encapsulamento | Todos os atributos das classes são privados, com acesso controlado por getters e setters. |
| Polimorfismo | Métodos como `toString()` e `equals()` foram sobrescritos para fornecer comportamentos específicos. |
| Coleções Genéricas | Uso de `ArrayList<Livro>` e `ArrayList<Leitor>` para garantir segurança de tipos. |
| Interfaces | Implementação de `Comparable<Livro>` e uso de `Comparator<Livro>` para ordenação flexível. |
| Tratamento de Exceções | Utilização de `try-catch` para tratar erros de entrada do usuário e exceções de I/O. |

---

## 6. Persistência de Dados

Para garantir que os dados não sejam perdidos após o encerramento do programa, foi utilizada a **serialização de objetos**.

### Funcionamento:

- **Ao iniciar o sistema:**  
  O `GerenciadorBiblioteca` tenta carregar a lista de livros a partir de um arquivo utilizando a classe `Persistencia`.  
  Caso o arquivo não exista ou esteja corrompido, uma nova lista vazia é criada.

- **Durante a execução:**  
  Sempre que ocorre uma alteração na lista de livros (cadastro, remoção, edição ou empréstimo), os dados são imediatamente salvos no arquivo.

Essa abordagem foi escolhida por ser simples, eficiente para o escopo do projeto e adequada ao conteúdo estudado na disciplina.

---

## 7. Considerações Finais

O desenvolvimento deste projeto possibilitou a aplicação prática dos conceitos de Programação Orientada a Objetos, reforçando a importância da organização do código, do uso adequado de classes e do tratamento de exceções.

Apesar de simples, o sistema atende aos requisitos propostos e pode ser facilmente expandido no futuro, como por exemplo com a adição de uma interface gráfica ou o uso de um banco de dados relacional.
