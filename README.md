# 📚 Documentação Técnica do Sistema de Gerenciamento de Biblioteca

## 1. Sumário Executivo do Projeto

O presente Sistema de Gerenciamento de Biblioteca constitui uma aplicação de console desenvolvida em linguagem Java, cujo escopo principal reside na demonstração e aplicação dos **princípios fundamentais da Programação Orientada a Objetos (POO)**. Este projeto emprega conceitos avançados, abrangendo herança, polimorfismo, tratamento de exceções estruturado, programação genérica e persistência de dados.

O objetivo central do sistema é administrar uma coleção de entidades **Livro**, viabilizando as operações básicas de persistência (CRUD) e funcionalidades específicas de gestão bibliotecária, tais como ordenação otimizada de coleções e o rigoroso registro de empréstimos associados a entidades **Leitor**.

### Funcionalidades Primárias

* **Persistência de Dados:** Implementação de mecanismos de carregamento e salvamento automático da coleção de livros em disco, utilizando **serialização de objetos**.
* **Gestão de Itens:** Capacidade de inclusão, remoção e consulta de livros mediante identificador único (ID) ou título.
* **Administração de Empréstimos:** Registro formal de empréstimos e devoluções, estabelecendo uma associação direta entre o objeto `Livro` e a entidade `Leitor` correspondente.
* **Análise e Organização:** Disponibilização de opções de listagem da coleção, com suporte para ordenação por Título e Autor, além de filtros por autor específico ou por leitor com itens em posse.
* **Robustez do Código:** Aplicação de um **Tratamento de Exceções tipificado** para administrar entradas do usuário incoerentes e prevenir falhas na lógica de negócio (e.g., validação de tipos de ordenação).

---

## 2. Estrutura Arquitetural

A arquitetura do sistema adota um **modelo em camadas**, promovendo a segregação de responsabilidades essenciais para otimizar a manutenibilidade e a escalabilidade do código.

| Camada | Pacote | Responsabilidade |
| :--- | :--- | :--- |
| **Modelo (Model)** | `model` | Contém as classes que representam as entidades de negócio e o domínio do sistema (`Livro`, `Autor`, `Leitor`, `Pessoa`). |
| **Serviço (Service)** | `service` | Implementa a lógica de negócio principal (`GerenciadorBiblioteca`) e o módulo de controle de dados (`Persistencia`). |
| **Apresentação (Main)** | `main` | Constitui a interface de usuário (`App`), encarregada da interação via console e da orquestração das operações do sistema. |

---

## 3. Detalhamento de Módulos e Classes

### 3.1. Pacote `model` (Entidades do Domínio)

| Classe | Descrição | Relacionamentos |
| :--- | :--- | :--- |
| `Pessoa` (Abstrata/Base) | Superclasse que consolida atributos fundamentais de identificação e contato (`nome`, `telefone`, `email`). | Superclasse da entidade `Leitor`. |
| `Leitor` | Representa o usuário autorizado a solicitar empréstimos. **Herda** os atributos e métodos da superclasse `Pessoa`. | **Associação:** `Livro` estabelece uma referência à instância de `Leitor` para indicar o responsável pelo empréstimo (`leitorEmprestimo`). |
| `Autor` | Entidade responsável por catalogar as informações do autor da obra, incluindo nome e nacionalidade. | **Associação:** `Livro` possui uma **composição** com a entidade `Autor`. |
| `Livro` | A entidade central da coleção. Implementa a interface `Comparable<Livro>` para definir a ordenação natural baseada no título. | **Associações:** Composição obrigatória com `Autor` e associação opcional com `Leitor`. |

### 3.2. Pacote `service` (Lógica e Persistência)

| Classe | Descrição | Responsabilidades |
| :--- | :--- | :--- |
| `Persistencia` | Módulo utilitário encarregado da manipulação de arquivos. Emprega serialização/desserialização de objetos para ler e escrever a coleção de livros. | Assegurar a integridade e a persistência da `ArrayList<Livro>`. |
| `GerenciadorBiblioteca` | Constitui o componente central do sistema, implementando a totalidade das regras de negócio e coordenando as interações. | Administrar a `ArrayList<Livro>`, executar operações de consulta, modificação, e ordenar a coleção utilizando interfaces `Comparator` e coordenar o fluxo de dados via `Persistencia`. |

---

## 4. Princípios da Programação Orientada a Objetos (POO)

O desenvolvimento do projeto emprega extensivamente os pilares da Programação Orientada a Objetos, complementados por conceitos avançados da plataforma Java:

| Conceito | Aplicação no Projeto |
| :--- | :--- |
| **Herança** | A classe `Leitor` é estabelecida como uma subclasse da entidade base `Pessoa` (implícita), promovendo a reutilização de atributos de identificação. |
| **Polimorfismo** | 1. **Sobrescrita (`@Override`):** A redefinição dos métodos `toString()` e `equals()` nas classes de Modelo estabelece representações textuais e critérios de equivalência específicos. 2. **Composição:** O método `exibeInformacoes()` da classe `Livro` demonstra o comportamento polimórfico ao invocar os respectivos métodos `toString()` dos objetos associados (`Autor` e `Leitor`). |
| **Encapsulamento** | Todos os atributos internos das classes de Modelo são declarados como `private`, com acesso estrito mediado por métodos acessores e modificadores (`public` Getters e Setters). |
| **Programação Genérica** | Utilização de coleções tipificadas (`ArrayList<Livro>`, `ArrayList<Leitor>`) para garantir a segurança e a coerência dos tipos. A função de ordenação emprega a interface `Comparator<Livro>` para flexibilidade. |
| **Tratamento de Exceções** | 1. **I/O:** A classe `Persistencia` lida com exceções de entrada/saída (`IOException`, `ClassNotFoundException`). 2. **Lógica de Negócio:** O método `ordenarLivros` lança uma exceção tipificada (`IllegalArgumentException`) em oposição a uma exceção genérica, elevando a robustez do código. 3. **Apresentação:** A interface `App` emprega estruturas *try-catch* para gerenciar entradas do usuário incoerentes (`NumberFormatException`). |
| **Associação/Composição** | A classe `Livro` mantém associações fortes (**Composição**) com `Autor` e uma associação opcional com `Leitor`, modelando as interações do mundo real. |

---

## 5. Protocolo de Persistência de Dados

O protocolo de persistência implementado assegura a manutenção do estado da coleção de livros após o encerramento da execução, utilizando a **serialização de objetos** nativa da linguagem Java (lógica encapsulada na classe `Persistencia`).

* **Carga Inicial de Dados (`App.main` -> `GerenciadorBiblioteca`):**
    * No momento da instanciação, a classe `GerenciadorBiblioteca` aciona o método `Persistencia.carregarLivros()`.
    * Caso o arquivo de persistência esteja acessível, a `ArrayList<Livro>` completa (incluindo as referências aninhadas a `Autor` e `Leitor`) é **desserializada** e carregada na memória principal.
    * Na ausência ou corrupção do arquivo, uma nova coleção vazia é instanciada.

* **Atualização e Armazenamento (Modificação de Estado):**
    * Qualquer método em `GerenciadorBiblioteca` que execute uma alteração no estado da coleção (`addLivro`, `remLivro`, `editLivro`, `ordenarLivros`) invoca, de forma sequencial, o método `Persistencia.salvarLivros(listaLivros)`.
    * O estado atual da `ArrayList<Livro>` é, então, imediatamente **serializado** e gravado no arquivo de dados, garantindo que o ponto de controle mais recente seja persistido de forma contínua.
