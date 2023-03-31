import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {

    private BufferedImage read;

    public void cria (InputStream inputStream, String nomeArquivo, String texto, InputStream inputStreamSobreposicao) throws Exception{

        // leitura da imagem
        // InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        // InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies_3.jpg").openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);


        // cria nova imagem em memória om transparencia e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);



        // copiar a imagem original pra novo imagem (em mamória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        BufferedImage imagemSobreposicao = ImageIO.read(inputStreamSobreposicao);
        int posicaoImagemSobreposicaoY = novaAltura - imagemSobreposicao.getHeight();
        graphics.drawImage(imagemSobreposicao, 0, posicaoImagemSobreposicaoY, null);


        // variaveis
        //String notastr = (filme.get("imDbRating"));
        //double nota = Double.valueOf(notastr).doubleValue();

            
        // configurar fonte
        var fonte = new Font("impact", Font.BOLD, 100);
        graphics.setColor(Color.CYAN);
        graphics.setFont(fonte);


        // escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = novaAltura - 100;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY); 

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(texto, fonte, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);


        // escrever a nova imagem em um arquivo    (new File("Nome do Diretório")).mkdir()
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));




    } 


}
