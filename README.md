# Library System

Sistema de biblioteca em Java executado no console, com foco em cadastro de livros e usuarios, emprestimos, devolucoes e validacoes de regras de negocio.

## Visao geral

Este projeto foi construido para praticar Java com organizacao em camadas, simulando um pequeno sistema de gestao de biblioteca.

## Funcionalidades

- cadastrar livros
- cadastrar usuarios
- buscar livros por titulo
- buscar usuarios por e-mail
- registrar emprestimos
- registrar devolucoes
- listar livros, usuarios e emprestimos ativos

## Regras de negocio

- livro nao pode ser cadastrado duas vezes com mesmo titulo e autor
- usuario nao pode ser cadastrado com e-mail repetido
- emprestimo so acontece se o livro estiver disponivel
- devolucao nao pode ter data anterior ao emprestimo
- nao e possivel devolver livro sem emprestimo ativo

## Estrutura do projeto

```text
src/
|-- Main.java
|-- ui/
|   `-- LibraryConsoleUI.java
|-- services/
|   |-- BookService.java
|   |-- UserService.java
|   `-- LoanService.java
|-- entities/
|   |-- Book.java
|   |-- User.java
|   |-- Loan.java
|   |-- Admin.java
|   `-- Person.java
`-- enums/
    `-- StatusBook.java
```

## Perfis no sistema

- `Administrador`: consulta informacoes gerais e gerencia o cadastro da biblioteca
- `Usuario`: realiza cadastro, emprestimo e devolucao de livros

## Como executar

Abra o projeto na IDE e rode a classe principal:

```text
src/Main.java
```

O repositorio tambem possui o script:

```text
run-tests.ps1
```

## Objetivo do repositorio

Treinar modelagem orientada a objetos, validacoes de negocio e separacao entre interface, servicos e entidades em um projeto de console.

## Melhorias futuras

- persistencia em arquivo ou banco de dados
- busca por ISBN
- historico de emprestimos
- autenticacao simples por perfil
- testes automatizados mais amplos

## Autor

Projeto desenvolvido por Bruno Bergamin como pratica de Java e POO.
