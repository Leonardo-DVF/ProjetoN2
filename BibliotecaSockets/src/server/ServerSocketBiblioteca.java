package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
import static server.OperacoesLivros.ajustarDataBaseCampos;

public class ServerSocketBiblioteca {
    public static void main(String[] args) {
        int portChoise = 8082; // Define o número da porta para o servidor

        try {
            //Precisa testar se o arquivo está adequado para realizar as operações
            String FILE_PATH = ".//src//server//database//databaseLivros.json";
            ajustarDataBaseCampos(FILE_PATH);
                        
            //Prepara o socket (listener) para a porta especificada:
            ServerSocket server = new ServerSocket(portChoise);
            System.out.println("\n\nServidor pronto na porta -> " + portChoise);
            
            // Aceita uma conexão de cliente
            Socket serverSocket = server.accept();
            System.out.println("Cliente conectado: " + serverSocket.getInetAddress().getHostAddress());

            // Configura streams de entrada e saída para comunicação com o cliente
            InputStreamReader inputServer = new InputStreamReader(serverSocket.getInputStream());
            PrintStream saida = new PrintStream(serverSocket.getOutputStream());            
            BufferedReader readerServer = new BufferedReader(inputServer);
            
            String messageInputClient;
            // Lê mensagens do cliente
            while((messageInputClient = readerServer.readLine()) != null){
                // Trata os diferentes comandos do cliente
                if(messageInputClient.toUpperCase().equals("EXIT")){
                    // Encerra o servidor
                    messageInputClient = "KILL";
                    saida.println(messageInputClient);
                    serverSocket.shutdownOutput();
                }else if(messageInputClient.toUpperCase().equals("AJUDA")){
                    // Envia o menu de ajuda para o cliente
                    messageInputClient = menuAjuda();
                    saida.println(messageInputClient);
                }else if(messageInputClient.toUpperCase().equals("LISTAR")){
                    // Lista todos os livros do JSON
                    messageInputClient = listarLivrosDatabase();
                    saida.println(messageInputClient);
                }else if(messageInputClient.toUpperCase().equals("ALUGAR")){
                    // Solicita o ID do livro para alugar
                    messageInputClient = "IDALUGAR";
                    saida.println(messageInputClient);
                }else if(messageInputClient.toUpperCase().equals("DEVOLVER")){
                    // Solicita o ID do livro para devolver
                    messageInputClient = "IDDEVOLVER";
                    saida.println(messageInputClient);
                }else if(messageInputClient.toUpperCase().equals("CADASTRAR")){
                    // Obtém um novo ID para cadastro de livro e solicita os dados do livro ao cliente
                    int idNovoCadastro = new OperacoesLivros().consultaNovoIdCadastroLivro();
                    messageInputClient = "CADASTRARLIVRO\n" + idNovoCadastro;
                    saida.println(messageInputClient);
                }else if(messageInputClient.toUpperCase().equals("DADOSLIVRO")){
                    // Recebe os dados do livro do cliente e cadastra o livro
                    messageInputClient = readerServer.readLine();    
                    JSONObject jsonLivroNovo = new JSONObject(messageInputClient);
                    Boolean realizaCadastro = new OperacoesLivros().cadastrarLivro(jsonLivroNovo);
                    
                    if(realizaCadastro) messageInputClient = "CADASTROSUCESSO\n__EOF";
                    saida.println(messageInputClient);
                }else if(messageInputClient.toUpperCase().equals("DADOSIDALUGAR")){
                    // Recebe o ID do livro do cliente e processa o aluguel
                    messageInputClient = readerServer.readLine();
                    int idAlugarLivro = 0;
                    try{
                       idAlugarLivro = Integer.parseInt(messageInputClient);
                       Boolean realizaAluguel = realizarLocacaoLivro(idAlugarLivro);
                       
                       if(realizaAluguel) messageInputClient = "ALUGADOSUCESSO\n__EOF";
                       else messageInputClient = "ALUGUELSEMSUCESSO\n__EOF";
                       
                       saida.println(messageInputClient);
                    }catch(Exception e){
                        System.out.println("Erro ao converter ID aluguel");
                    }
                }else if(messageInputClient.toUpperCase().equals("DADOSIDDEVOLVER")){
                    // Recebe o ID do livro do cliente e processa a devolução
                    messageInputClient = readerServer.readLine();
                    int idDevolverLivro = 0;
                    
                    try{
                       idDevolverLivro = Integer.parseInt(messageInputClient);
                       Boolean realizaDevolucao = realizarDevolucaoLivro(idDevolverLivro);
                       
                       if(realizaDevolucao) messageInputClient = "DEVOLVIDOSUCESSO\n__EOF";
                       else messageInputClient = "DEVOLUCAOSEMSUCESSO\n__EOF";
                       saida.println(messageInputClient);
                    }catch(Exception e){
                        System.out.println("Erro ao converter ID aluguel");
                    }
                }else{
                    // Trata comandos desconhecidos
                    messageInputClient = messageInputClient = "DESCONHECIDO\n__EOF";
                    saida.println(messageInputClient);
                }
            }
        }catch(Exception e) {
           System.out.println("Erro: " + e.getMessage());
        }
    }

    public static String listarLivrosDatabase(){
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        StringBuilder menuLivros = new StringBuilder();
        Formatter fmtLivros = new Formatter();
        menuLivros.append(fmtLivros.format("%150s","======================================================================================================================================================\n").toString());

        fmtLivros = new Formatter();
        menuLivros.append(fmtLivros.format("%85s","LISTA DE LIVROS\n\n").toString());

        fmtLivros = new Formatter();
        fmtLivros.format("%15s %40s %25s %25s %14s %14s\n\n", "ID", "TÍTULO", "GÊNERO", "AUTOR", "QUANTIDADE", "ALUGADOS");

        for (int i = 0; i < listaLivros.size(); i++) {
            fmtLivros.format("%15s %40s %25s %25s %14s %14s\n",
                    listaLivros.get(i).getId(),
                    listaLivros.get(i).getNomeLivro(),
                    listaLivros.get(i).getGeneroLivro(),
                    listaLivros.get(i).getAutorLivro(),
                    listaLivros.get(i).getNumeroExemplaresLivros(),
                    listaLivros.get(i).getQntdAlugados()
            );
        }
        menuLivros.append(fmtLivros.toString());

        fmtLivros = new Formatter();
        menuLivros.append(fmtLivros.format("%150s","======================================================================================================================================================\n").toString());
        menuLivros.append("__EOF");

        fmtLivros.close();
        
        //System.out.println(menuLivros.toString());
        
        return menuLivros.toString();
    }
    
    public static Boolean realizarLocacaoLivro(int idAlugarLivro){
            List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
            for (int i = 0; i < listaLivros.size(); i++) {
                if(listaLivros.get(i).getId().equals("" + idAlugarLivro)){
                    try{
                        int numExemplares = Integer.parseInt(listaLivros.get(i).getNumeroExemplaresLivros());
                        int numExemplaresAlugados = Integer.parseInt(listaLivros.get(i).getQntdAlugados());
                        if((numExemplares - numExemplaresAlugados) > 0){
                            listaLivros.get(i).setQntdAlugados("" + (numExemplaresAlugados + 1));
                        }else{
                            return false;
                        }
                    }catch(Exception e){
                        System.out.println("Erro ao alugar o livro.");
                    }
                }
            }//Final do for
