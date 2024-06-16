package server;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class OperacoesLivros {
    
    // Consulta todos os livros da biblioteca
    public List<LivroBiblioteca> consultaLivrosBibioteca() {
        List<LivroBiblioteca> livrosBiblioteca = new ArrayList<>();
        try {
            // Carrega o arquivo JSON com os livros
            String FILE_PATH = ".//src//server//database//databaseLivros.json";
            JSONArray livrosArray = retornaJSON(FILE_PATH);
            
            // Carrega cada livro do array em uma instância da classe LivroBiblioteca
            for (int i = 0; i < livrosArray.length(); i++) {
                JSONObject jsonLivro = livrosArray.getJSONObject(i);
                LivroBiblioteca livroB = new LivroBiblioteca();
                livroB.setId("" + jsonLivro.getInt("id"));                
                livroB.setAutorLivro(jsonLivro.getString("autor"));
                livroB.setGeneroLivro(jsonLivro.getString("genero"));
                livroB.setNomeLivro(jsonLivro.getString("titulo"));
                livroB.setNumeroExemplaresLivros("" + jsonLivro.getInt("exemplares"));
                livroB.setQntdAlugados(jsonLivro.getString("alugados"));
                livrosBiblioteca.add(livroB);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livrosBiblioteca;
    }
