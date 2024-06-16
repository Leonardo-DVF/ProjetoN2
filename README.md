# Projeto de Servidor de Biblioteca utilizando Sockets

## Descrição
Este projeto implementa um servidor de biblioteca que controla o registro e o cadastro de livros utilizando sockets. O servidor é capaz de executar as seguintes funcionalidades:
- Listagem dos livros
- Aluguel e devolução de livros
- Cadastro de livros

Os livros são armazenados em um arquivo JSON que representa a "base de dados" da biblioteca. As operações realizadas pelo cliente, como cadastro e aluguel, são refletidas neste arquivo.

## Estrutura do Projeto
O projeto é composto pelos seguintes componentes:
1. **Servidor**: Controla o registro e o cadastro de livros.
2. **Cliente**: Envia solicitações ao servidor e recebe respostas.
3. **Classe Livro**: Representa os livros com os atributos autor, nome, gênero e número de exemplares.
4. **Manipulação de Arquivo JSON**: Lida com a leitura e escrita do arquivo JSON que armazena os dados dos livros.

## Funcionalidades
### Listagem dos Livros
Permite listar todos os livros disponíveis na biblioteca.

### Aluguel e Devolução de Livros
Permite alugar um livro, decrementando o número de exemplares disponíveis, e devolver um livro, incrementando o número de exemplares.

### Cadastro de Livros
Permite cadastrar novos livros na biblioteca, adicionando-os ao arquivo JSON.

## Estrutura da Classe Livro
A classe `Livro` possui os seguintes atributos:
- **Autor**: Nome do autor do livro.
- **Nome**: Título do livro.
- **Gênero**: Gênero literário do livro.
- **Número de Exemplares**: Quantidade de exemplares disponíveis para aluguel.

## Comunicação Cliente-Servidor
A comunicação entre o cliente e o servidor é realizada utilizando sockets. O cliente envia solicitações para listar, alugar, devolver e cadastrar livros, e o servidor processa essas solicitações e envia as respostas apropriadas.

## Contribuidores
- Leonardo Duarte Veiga Ferreira
- Mateus Bittencourt Camandaroba
- Rafael Gomes Parente
- Ruan Arthur Rodrigues Gonçalves
- Yann Soares Guimarães da Silva
