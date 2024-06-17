package server;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Formatter;
import java.util.List;

public class LivroOperations {

    // Método para listar todos os livros do banco de dados em um formato estruturado
    public static String listarLivrosDatabase() {
        // Consulta a lista de livros no banco de dados
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        // StringBuilder para construir a string formatada
        StringBuilder menuLivros = new StringBuilder();
        // Formatter para formatar a saída
        Formatter fmtLivros = new Formatter();
        // Adiciona linha de cabeçalho
        menuLivros.append(fmtLivros.format("%150s", "======================================================================================================================================================\n").toString());

        // Reinicia o Formatter para nova formatação
        fmtLivros = new Formatter();
        // Adiciona o título da lista de livros
        menuLivros.append(fmtLivros.format("%85s", "LISTA DE LIVROS\n\n").toString());

        // Reinicia o Formatter para nova formatação
        fmtLivros = new Formatter();
        // Adiciona cabeçalhos das colunas
        fmtLivros.format("%15s %40s %25s %25s %14s %14s\n\n", "ID", "TÍTULO", "GÊNERO", "AUTOR", "QUANTIDADE", "ALUGADOS");

        // Itera pela lista de livros e adiciona cada livro na formatação especificada
        for (LivroBiblioteca livro : listaLivros) {
            fmtLivros.format("%15s %40s %25s %25s %14s %14s\n",
                    livro.getId(),
                    livro.getNomeLivro(),
                    livro.getGeneroLivro(),
                    livro.getAutorLivro(),
                    livro.getNumeroExemplaresLivros(),
                    livro.getQntdAlugados()
            );
        }

        // Adiciona a string formatada ao StringBuilder
        menuLivros.append(fmtLivros.toString());

        // Reinicia o Formatter para nova formatação
        fmtLivros = new Formatter();
        // Adiciona linha de rodapé
        menuLivros.append(fmtLivros.format("%150s", "======================================================================================================================================================\n").toString());
        // Adiciona marcador de final de arquivo
        menuLivros.append("__EOF");

        // Fecha o Formatter
        fmtLivros.close();

        // Retorna a string completa formatada
        return menuLivros.toString();
    }

    // Método para realizar a locação de um livro pelo seu ID
    public static Boolean realizarLocacaoLivro(int idAlugarLivro) {
        // Consulta a lista de livros no banco de dados
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        // Itera pela lista de livros para encontrar o livro com o ID fornecido
        for (LivroBiblioteca livro : listaLivros) {
            if (livro.getId().equals("" + idAlugarLivro)) {
                try {
                    // Converte os valores de exemplares e alugados para inteiros
                    int numExemplares = Integer.parseInt(livro.getNumeroExemplaresLivros());
                    int numExemplaresAlugados = Integer.parseInt(livro.getQntdAlugados());
                    // Verifica se há exemplares disponíveis para aluguel
                    if ((numExemplares - numExemplaresAlugados) > 0) {
                        // Incrementa a quantidade de exemplares alugados
                        livro.setQntdAlugados("" + (numExemplaresAlugados + 1));
                    } else {
                        // Retorna false se não houver exemplares disponíveis
                        return false;
                    }
                } catch (Exception e) {
                    // Imprime mensagem de erro e retorna false em caso de exceção
                    System.out.println("Erro ao alugar o livro.");
                    return false;
                }
            }
        }

        // Cria um JSONArray para armazenar os dados atualizados dos livros
        JSONArray baseLivrosAlugados = new JSONArray();
        // Itera pela lista de livros e adiciona cada livro ao JSONArray
        for (LivroBiblioteca livro : listaLivros) {
            JSONObject jsonBaseLivroAlugados = new JSONObject();
            jsonBaseLivroAlugados.put("id", livro.getId());
            jsonBaseLivroAlugados.put("titulo", livro.getNomeLivro());
            jsonBaseLivroAlugados.put("autor", livro.getAutorLivro());
            jsonBaseLivroAlugados.put("genero", livro.getGeneroLivro());
            jsonBaseLivroAlugados.put("exemplares", livro.getNumeroExemplaresLivros());
            jsonBaseLivroAlugados.put("alugados", livro.getQntdAlugados());
            baseLivrosAlugados.put(jsonBaseLivroAlugados);
        }

        // Atualiza a base de dados com as informações dos livros alugados
        return new OperacoesLivros().atualizarBaseDadosLivro(baseLivrosAlugados);
    }

    // Método para realizar a devolução de um livro pelo seu ID
    public static Boolean realizarDevolucaoLivro(int idDevolverLivro) {
        // Consulta a lista de livros no banco de dados
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        // Itera pela lista de livros para encontrar o livro com o ID fornecido
        for (LivroBiblioteca livro : listaLivros) {
            if (livro.getId().equals("" + idDevolverLivro)) {
                try {
                    // Converte o valor de exemplares alugados para inteiro
                    int numExemplaresAlugados = Integer.parseInt(livro.getQntdAlugados());
                    // Verifica se há exemplares alugados para devolver
                    if (numExemplaresAlugados > 0) {
                        // Decrementa a quantidade de exemplares alugados
                        livro.setQntdAlugados("" + (numExemplaresAlugados - 1));
                    } else {
                        // Retorna false se não houver exemplares alugados
                        return false;
                    }
                } catch (Exception e) {
                    // Imprime mensagem de erro e retorna false em caso de exceção
                    System.out.println("Erro ao devolver o livro.");
                    return false;
                }
            }
        }

        // Cria um JSONArray para armazenar os dados atualizados dos livros
        JSONArray baseLivrosDevolucao = new JSONArray();
        // Itera pela lista de livros e adiciona cada livro ao JSONArray
        for (LivroBiblioteca livro : listaLivros) {
            JSONObject jsonBaseLivroAlugados = new JSONObject();
            jsonBaseLivroAlugados.put("id", livro.getId());
            jsonBaseLivroAlugados.put("titulo", livro.getNomeLivro());
            jsonBaseLivroAlugados.put("autor", livro.getAutorLivro());
            jsonBaseLivroAlugados.put("genero", livro.getGeneroLivro());
            jsonBaseLivroAlugados.put("exemplares", livro.getNumeroExemplaresLivros());
            jsonBaseLivroAlugados.put("alugados", livro.getQntdAlugados());
            baseLivrosDevolucao.put(jsonBaseLivroAlugados);
        }

        // Atualiza a base de dados com as informações dos livros devolvidos
        return new OperacoesLivros().atualizarBaseDadosLivro(baseLivrosDevolucao);
    }
}
