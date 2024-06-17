package server;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Formatter;
import java.util.List;

public class LivroOperations {

    // Método para listar todos os livros da biblioteca
    public static String listarLivrosDatabase() {
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        StringBuilder menuLivros = new StringBuilder();
        Formatter fmtLivros = new Formatter();
        
        // Adiciona uma linha separadora no início do menu
        menuLivros.append(fmtLivros.format("%150s", "======================================================================================================================================================\n").toString());

        // Adiciona o título do menu
        fmtLivros = new Formatter();
        menuLivros.append(fmtLivros.format("%85s", "LISTA DE LIVROS\n\n").toString());

        // Adiciona os cabeçalhos das colunas
        fmtLivros = new Formatter();
        fmtLivros.format("%15s %40s %25s %25s %14s %14s\n\n", "ID", "TÍTULO", "GÊNERO", "AUTOR", "QUANTIDADE", "ALUGADOS");

        // Adiciona as informações de cada livro
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

        menuLivros.append(fmtLivros.toString());

        // Adiciona uma linha separadora no final do menu
        fmtLivros = new Formatter();
        menuLivros.append(fmtLivros.format("%150s", "======================================================================================================================================================\n").toString());
        menuLivros.append("__EOF");

        fmtLivros.close();

        return menuLivros.toString();
    }

    // Método para realizar a locação de um livro
    public static Boolean realizarLocacaoLivro(int idAlugarLivro) {
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        for (LivroBiblioteca livro : listaLivros) {
            if (livro.getId().equals("" + idAlugarLivro)) {
                try {
                    int numExemplares = Integer.parseInt(livro.getNumeroExemplaresLivros());
                    int numExemplaresAlugados = Integer.parseInt(livro.getQntdAlugados());
                    
                    // Verifica se há exemplares disponíveis para alugar
                    if ((numExemplares - numExemplaresAlugados) > 0) {
                        livro.setQntdAlugados("" + (numExemplaresAlugados + 1));
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao alugar o livro.");
                    return false;
                }
            }
        }

        // Atualiza a base de dados com as novas informações dos livros
        JSONArray baseLivrosAlugados = new JSONArray();
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

        return new OperacoesLivros().atualizarBaseDadosLivro(baseLivrosAlugados);
    }

    // Método para realizar a devolução de um livro
    public static Boolean realizarDevolucaoLivro(int idDevolverLivro) {
        List<LivroBiblioteca> listaLivros = new OperacoesLivros().consultaLivrosBibioteca();
        for (LivroBiblioteca livro : listaLivros) {
            if (livro.getId().equals("" + idDevolverLivro)) {
                try {
                    int numExemplaresAlugados = Integer.parseInt(livro.getQntdAlugados());
                    
                    // Verifica se há exemplares alugados para devolver
                    if (numExemplaresAlugados > 0) {
                        livro.setQntdAlugados("" + (numExemplaresAlugados - 1));
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao devolver o livro.");
                    return false;
                }
            }
        }

        // Atualiza a base de dados com as novas informações dos livros
        JSONArray baseLivrosDevolucao = new JSONArray();
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

        return new OperacoesLivros().atualizarBaseDadosLivro(baseLivrosDevolucao);
    }
}
