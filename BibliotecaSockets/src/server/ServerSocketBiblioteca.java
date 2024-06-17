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
            ajustarDataBaseCampos(FILE_PATH);

            try (ServerSocket server = new ServerSocket(portChoice)) {
                System.out.println("\n\nServidor pronto na porta -> " + portChoice);

                while (true) {
                    try (Socket serverSocket = server.accept();
                         InputStreamReader inputServer = new InputStreamReader(serverSocket.getInputStream());
                         PrintStream saida = new PrintStream(serverSocket.getOutputStream());
                         BufferedReader readerServer = new BufferedReader(inputServer)) {

                        System.out.println("Cliente conectado: " + serverSocket.getInetAddress().getHostAddress());

                        String messageInputClient;
                        while ((messageInputClient = readerServer.readLine()) != null) {
                            handleClientMessage(messageInputClient, readerServer, saida);
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

    private static void handleClientMessage(String messageInputClient, BufferedReader readerServer, PrintStream saida) throws Exception {
        switch (messageInputClient.toUpperCase()) {
            case "EXIT":
                saida.println("KILL");
                readerServer.close();
                saida.close();
                break;
            case "AJUDA":
                saida.println(Ajuda.menuAjuda());
                break;
            case "LISTAR":
                saida.println(LivroOperations.listarLivrosDatabase());
                break;
            case "ALUGAR":
                saida.println("IDALUGAR");
                break;
            case "DEVOLVER":
                saida.println("IDDEVOLVER");
                break;
            case "CADASTRAR":
                int idNovoCadastro = new OperacoesLivros().consultaNovoIdCadastroLivro();
                saida.println("CADASTRARLIVRO\n" + idNovoCadastro);
                break;
            case "DADOSLIVRO":
                JSONObject jsonLivroNovo = new JSONObject(readerServer.readLine());
                Boolean realizaCadastro = new OperacoesLivros().cadastrarLivro(jsonLivroNovo);
                saida.println(realizaCadastro ? "CADASTROSUCESSO\n__EOF" : "ERRO_CADASTRO");
                break;
            case "DADOSIDALUGAR":
                int idAlugarLivro = Integer.parseInt(readerServer.readLine());
                Boolean realizaAluguel = LivroOperations.realizarLocacaoLivro(idAlugarLivro);
                saida.println(realizaAluguel ? "ALUGADOSUCESSO\n__EOF" : "ALUGUELSEMSUCESSO\n__EOF");
                break;
            case "DADOSIDDEVOLVER":
                int idDevolverLivro = Integer.parseInt(readerServer.readLine());
                Boolean realizaDevolucao = LivroOperations.realizarDevolucaoLivro(idDevolverLivro);
                saida.println(realizaDevolucao ? "DEVOLVIDOSUCESSO\n__EOF" : "DEVOLUCAOSEMSUCESSO\n__EOF");
                break;
            default:
                saida.println("DESCONHECIDO\n__EOF");
                break;
        }
    }
}
