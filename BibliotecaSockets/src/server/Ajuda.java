package server; // Define que esta classe faz parte do pacote 'server'

import java.util.Formatter; // Importa a classe 'Formatter' para formatação de texto

public class Ajuda { // Declaração da classe 'Ajuda'
    public static String menuAjuda() { // Método estático 'menuAjuda' que retorna uma String
        StringBuilder menuAjuda = new StringBuilder(); // Cria um StringBuilder para construir a string do menu de ajuda
        int i = 0; // Inicializa um contador para numerar os comandos

        // Cria um Formatter para formatar a linha de separação superior
        Formatter fmtMenuAjuda = new Formatter();
        menuAjuda.append(fmtMenuAjuda.format("%150s", "======================================================================================================================================================\n").toString());

        // Cria um Formatter para formatar o título do menu
        fmtMenuAjuda = new Formatter();
        menuAjuda.append(fmtMenuAjuda.format("%80s", "MENU AJUDA - LISTA DE COMANDOS\n\n").toString());

        // Cria um Formatter para formatar o cabeçalho da tabela de comandos
        fmtMenuAjuda = new Formatter();
        fmtMenuAjuda.format("%5s %15s %110s\n\n", "NR", "COMANDO", "DESCRIÇÃO"); // Cabeçalho da tabela
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "AJUDA", "Chama o menu de AJUDA com os comandos possíveis."); // Primeira linha do comando
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "ALUGAR", "Aluga um livro desejado. Vai pedir o ID do livro escolhido."); // Segunda linha do comando
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "CADASTRAR", "Cadastra um novo livro da base de dados. Pedirá dados do livro para cadastro."); // Terceira linha do comando
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "DEVOLVER", "Devolve um livro específico. Vai pedir o ID do livro alugado."); // Quarta linha do comando
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "LISTAR", "Para obter os livros do sistema"); // Quinta linha do comando
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "EXIT", "Sai do sistema. Irá finalizar o cliente e o servidor! Cuidado."); // Sexta linha do comando
        menuAjuda.append(fmtMenuAjuda.toString()); // Adiciona a tabela formatada ao StringBuilder

        // Cria um Formatter para formatar a linha de separação inferior
        fmtMenuAjuda = new Formatter();
        menuAjuda.append(fmtMenuAjuda.format("%150s", "======================================================================================================================================================\n").toString());
        menuAjuda.append("__EOF"); // Adiciona o marcador de final de arquivo '__EOF'

        fmtMenuAjuda.close(); // Fecha o Formatter para liberar recursos

        return menuAjuda.toString(); // Converte o StringBuilder em uma string e retorna
    }
}
