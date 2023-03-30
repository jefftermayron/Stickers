import java.net.URI;
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
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();


        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);


        // exibir e manipular os dados     Math.round(valueOf
        for (Map<String,String> filme : listaDeFilmes) {
            String notastr = (filme.get("imDbRating"));
            double nota = Double.valueOf(notastr).doubleValue();
            
            System.out.println("\u001b[1m TITULO: " + filme.get("title") + "\u001b[m");
            System.out.println("IMAGEM: " + filme.get("image"));
            System.out.println("\u001b[97m \u001b[104m CLASSIFICAÇÃO: " + Math.round(nota) + "\u001b[m ");
            String star = "\u2B50";
            int i = (int) nota;
            System.out.println(star.repeat(i));
            System.out.println();

        }
    }
}
