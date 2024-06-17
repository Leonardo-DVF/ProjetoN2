package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

import static server.OperacoesLivros.ajustarDataBaseCampos;

public class ServerSocketBiblioteca {
    public static void main(String[] args) {
        int portChoice = 8082; // Define o número da porta para o servidor

        try {
            String FILE_PATH = ".//src//server//database//databaseLivros.json";
            ajustarDataBaseCampos(FILE_PATH); // Ajusta o banco de dados conforme necessário

            // Cria um ServerSocket na porta especificada
            try (ServerSocket server = new ServerSocket(portChoice)) {
                System.out.println("\n\nServidor pronto na porta -> " + portChoice);

                // Loop infinito para aceitar conexões de clientes
                while (true) {
                    try (
                        // Aceita a conexão de um cliente
                        Socket serverSocket = server.accept();
                        InputStreamReader inputServer = new InputStreamReader(serverSocket.getInputStream());
                        PrintStream saida = new PrintStream(serverSocket.getOutputStream());
                        BufferedReader readerServer = new BufferedReader(inputServer)
                    ) {
                        System.out.println("Cliente conectado: " + serverSocket.getInetAddress().getHostAddress());

                        String messageInputClient;
                        // Lê mensagens do cliente
                        while ((messageInputClient = readerServer.readLine()) != null) {
                            handleClientMessage(messageInputClient, readerServer, saida); // Processa a mensagem do cliente
                        }
                    } catch (Exception e) {
                        System.out.println("Erro no processamento da mensagem do cliente: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    // Processa as mensagens enviadas pelo cliente
    private static void handleClientMessage(String messageInputClient, BufferedReader readerServer, PrintStream saida) throws Exception {
        switch (messageInputClient.toUpperCase()) {
            case "EXIT":
                saida.println("KILL"); // Envia sinal de encerramento ao cliente
                readerServer.close();
                saida.close();
                break;
            case "AJUDA":
                saida.println(Ajuda.menuAjuda()); // Envia o menu de ajuda ao cliente
                break;
            case "LISTAR":
                saida.println(LivroOperations.listarLivrosDatabase()); // Lista os livros no banco de dados
                break;
            case "ALUGAR":
                saida.println("IDALUGAR"); // Solicita o ID do livro a ser alugado
                break;
            case "DEVOLVER":
                saida.println("IDDEVOLVER"); // Solicita o ID do livro a ser devolvido
                break;
            case "CADASTRAR":
                int idNovoCadastro = new OperacoesLivros().consultaNovoIdCadastroLivro();
                saida.println("CADASTRARLIVRO\n" + idNovoCadastro); // Solicita os dados para cadastrar um novo livro
                break;
            case "DADOSLIVRO":
                JSONObject jsonLivroNovo = new JSONObject(readerServer.readLine());
                Boolean realizaCadastro = new OperacoesLivros().cadastrarLivro(jsonLivroNovo);
                saida.println(realizaCadastro ? "CADASTROSUCESSO\n__EOF" : "ERRO_CADASTRO"); // Confirma o sucesso ou falha do cadastro
                break;
            case "DADOSIDALUGAR":
                int idAlugarLivro = Integer.parseInt(readerServer.readLine());
                Boolean realizaAluguel = LivroOperations.realizarLocacaoLivro(idAlugarLivro);
                saida.println(realizaAluguel ? "ALUGADOSUCESSO\n_EOF" : "ALUGUELSEMSUCESSO\n_EOF"); // Confirma o sucesso ou falha do aluguel
                break;
            case "DADOSIDDEVOLVER":
                int idDevolverLivro = Integer.parseInt(readerServer.readLine());
                Boolean realizaDevolucao = LivroOperations.realizarDevolucaoLivro(idDevolverLivro);
                saida.println(realizaDevolucao ? "DEVOLVIDOSUCESSO\n_EOF" : "DEVOLUCAOSEMSUCESSO\n_EOF"); // Confirma o sucesso ou falha da devolução
                break;
            default:
                saida.println("DESCONHECIDO\n__EOF"); // Resposta para comando desconhecido
                break;
        }
    }
}
