# Library System

Sistema de biblioteca em Java, executado no console, com foco em regras basicas de cadastro e emprestimo de livros.

## O que o sistema faz

- Cadastra livros
- Cadastra usuarios
- Busca usuarios por e-mail
- Busca livros por titulo
- Registra emprestimo de livro
- Registra devolucao de livro
- Lista livros, usuarios e emprestimos ativos

## Perfis do sistema

- `Administrador`: pode cadastrar livros e consultar informacoes gerais (livros, usuarios e emprestimos ativos)
- `Usuario`: pode se cadastrar, emprestar livro e devolver livro

## Estrutura do projeto

- `Main.java`: ponto de entrada da aplicacao
- `ui/LibraryConsoleUI.java`: menus e interacao no terminal
- `services/`: regras de negocio (livros, usuarios e emprestimos)
- `entities/`: classes de dominio (`Book`, `User`, `Loan`, `Admin`, `Person`)
- `enums/StatusBook.java`: status do livro (`AVAILABLE` e `BORROWED`)

## Regras principais

- Livro nao pode ser cadastrado duas vezes com mesmo titulo e autor
- Usuario nao pode ser cadastrado com e-mail repetido
- Emprestimo so ocorre se o livro estiver `AVAILABLE`
- Ao emprestar, o status do livro vira `BORROWED`
- Ao devolver, o status volta para `AVAILABLE`
- Nao permite devolucao com data anterior a data de emprestimo
- Nao permite devolver livro sem emprestimo ativo

## Fluxo geral

1. O programa abre um menu principal com area de administrador e area de usuario.
2. Na area de administrador, o sistema concentra cadastro e consultas da biblioteca.
3. Na area de usuario, o sistema realiza operacoes de emprestimo e devolucao.
4. Toda validacao de regra acontece na camada `services`.
# library-system
