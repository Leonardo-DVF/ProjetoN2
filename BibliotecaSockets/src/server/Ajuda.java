package server;

import java.util.Formatter;

public class Ajuda {
    public static String menuAjuda() {
        StringBuilder menuAjuda = new StringBuilder();
        int i = 0;

        Formatter fmtMenuAjuda = new Formatter();
        menuAjuda.append(fmtMenuAjuda.format("%150s", "======================================================================================================================================================\n").toString());

        fmtMenuAjuda = new Formatter();
        menuAjuda.append(fmtMenuAjuda.format("%80s", "MENU AJUDA - LISTA DE COMANDOS\n\n").toString());

        fmtMenuAjuda = new Formatter();
        fmtMenuAjuda.format("%5s %15s %110s\n\n", "NR", "COMANDO", "DESCRIÇÃO");
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "AJUDA", "Chama o menu de AJUDA com os comandos possíveis.");
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "ALUGAR", "Aluga um livro desejado. Vai pedir o ID do livro escolhido.");
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "CADASTRAR", "Cadastra um novo livro da base de dados. Pedirá dados do livro para cadastro.");
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "DEVOLVER", "Devolve um livro específico. Vai pedir o ID do livro alugado.");
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "LISTAR", "Para obter os livros do sistema");
        fmtMenuAjuda.format("%5s %15s %110s\n", ++i, "EXIT", "Sai do sistema. Irá finalizar o cliente e o servidor! Cuidado.");
        menuAjuda.append(fmtMenuAjuda.toString());

        fmtMenuAjuda = new Formatter();
        menuAjuda.append(fmtMenuAjuda.format("%150s", "======================================================================================================================================================\n").toString());
        menuAjuda.append("__EOF");

        fmtMenuAjuda.close();

        return menuAjuda.toString();
    }
}
