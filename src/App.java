import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.Key;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {


        // fazer uma conexão HTTP e buscar os top 250 filmes
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json"
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        

        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir(); 


        // exibir e manipular os dados   
        // "\u001b[1mIMAGEM: " + ulrImagem + "\u001b[m" 
        var geradora = new GeradoraDeFigurinhas();
         
        for (Map<String,String> filme : listaDeFilmes) {
            //String notastr = (filme.get("imDbRating"));
            //double classicacao = Double.valueOf(notastr).doubleValue();
            String ulrImagem = filme.get("image");
            String titulo = filme.get("title");
            String idImagem = filme.get("id");

            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            String textoFigurinha;
            InputStream imagemJeffter;
            if (classificacao >= 8.0) {
                textoFigurinha = "INSANO";
                imagemJeffter = new FileInputStream(new File("sobreposicao/aprovado.png"));
            } else {
                textoFigurinha = "VISHH...";
                imagemJeffter = new FileInputStream(new File("sobreposicao/reprovado.png"));
            }
            
            
            InputStream inputStream = new URL(ulrImagem).openStream();
            String nomeArquivo = "figurinhas/" + idImagem + ".png";

            geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemJeffter);
            
            System.out.println("\u001b[1mTITULO: " + titulo + "\u001b[m");
            //System.out.println("\u001b[97m \u001b[104mCLASSIFICAÇÃO: " + Math.round(classificacao) + "\u001b[m ");
            //String star = "\u2B50";
            //int i = (int) Math.round(classicacao);
            //System.out.println(star.repeat(i));  //colocar no cmd (chcp 65001)
            System.out.println();

        }
    }
}
