package client;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteThread extends Thread{

    private Socket socketClient;

    ClienteThread(Socket clienteSocket) {
        this.socketClient = clienteSocket;
    }
    
    @Override
    public void run(){
        try{
            // Cria um InputStreamReader e um BufferedReader para ler as mensagens do servidor
            InputStreamReader inputServer = new InputStreamReader(socketClient.getInputStream());
            BufferedReader readerServer = new BufferedReader(inputServer);
            
            // Cria um Scanner para ler as entradas do usuário
            String messageInputClient = null;
            Scanner scanner = new Scanner(System.in);
            
            // Cria uma lista de comandos
            List<String> comandos = new ArrayList<String>();
            comandos.add("KILL");
            comandos.add("__EOF");
           
            // Loop principal para processar as mensagens do servidor
            while((messageInputClient = readerServer.readLine())!=null){
                
                // Se a mensagem for "KILL", exibe uma mensagem de conexão encerrada
                if(messageInputClient.equals("KILL")){
                    messageInputClient = "Conexão encerrada!\n <<< Pressione qualquer tecla para continuar >>>";
                    
                // Se a mensagem não contiver "KILL" ou "__EOF" e não estiver na lista de comandos, exibe a mensagem
                }else if(!messageInputClient.contains("__EOF")) {
                    if(!comandos.contains(messageInputClient)) System.out.println(messageInputClient);
                }
            }
        }catch(Exception e){
            System.out.println("Erro no client thread + " + e);
        }
    }
    
}
