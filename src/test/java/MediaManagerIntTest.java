import model.*;
import controller.MediaManager;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe para testes do gerenciador de mídias.
 */
public class MediaManagerIntTest {

    /**
     * Essa função verifica a instanciação de "reviews" como atributo de outros objetos.
     */
    @Test
    public void testAdicionarLivroComReview() {
        List<Review> reviews = List.of();
        MediaManager manager = new MediaManager();
        Livro livro = new Livro("Ana Maria", "Fantasia", "2001", reviews, "Santanna", "Cantador","Confusão",true);
        livro.setFoiVista(true);
        manager.adicionarMidia(livro);

        Review review = new Review(5, LocalDate.now(), "Excelente!");
        manager.adicionarReview(livro, review);

        assertTrue(livro.getReviews().contains(review));
        assertEquals(1, livro.getReviews().size());
    }

    /**
     * Essa função verifica se as "reviews" são atribuídas sem que a mídia tenha sido vista.
     */
    @Test
    public void testAdicionarFilmeSemAssistir() {
        List<Review> reviews = List.of();
        MediaManager manager = new MediaManager();
        Filme filme = new Filme("Meu Bem Querer", "Ação", "2010", reviews, "Lázaro Ramos, Wagner Moura", "Meu Encanto", "Segredo", "Sagrado","Djavan","Emoção");
        manager.adicionarMidia(filme);

        Review review = new Review(4, LocalDate.now(), "Bom filme.");
        manager.adicionarReview(filme, review);

        assertEquals(0, filme.getReviews().size());
    }

    /**
     * Essa função garante a atribuição correta das temporadas e os seus atributos.
     */
    @Test
    public void testAdicionarReviewTemporadaDeSerie() {
        List<Review> reviews = List.of();
        MediaManager manager = new MediaManager();
        Serie serie = new Serie("Último Romance", "Drama", "2022", reviews, "Lázaro Ramos, Wagner Moura", "Ventura","Fila do Pão","Quando não Quis");
        Temporada temporada = new Temporada("1", "2022", "10");
        temporada.setFoiVista(true);
        manager.adicionarMidia(serie);
        manager.adicionarTemporada(serie, temporada);

        Review review = new Review(4, LocalDate.now(), "Boa temporada");
        manager.adicionarReviewTemporada(serie, temporada, review);

        assertEquals(1, temporada.getReviews().size());
        assertEquals(4.0, serie.getAvaliacaoMedia());
    }
}