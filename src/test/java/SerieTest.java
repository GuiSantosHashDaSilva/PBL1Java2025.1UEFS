import controller.MediaManager;
import model.Serie;
import model.Temporada;
import model.Review;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe para teste das séries.
 */
public class SerieTest {

    /**
     * Esse teste garante a instanciação correta de séries.
     */
    @Test
    public void testCriacaoSerie() {
        Review review1 = new Review(5, LocalDate.now(), "Ótimo!");
        Review review2 = new Review(3,LocalDate.now(),"Piorou");
        List<Review> reviews = List.of(review1, review2);
        Serie s = new Serie("Série Teste", "Drama", "2022", reviews, "Lázaro Ramos, Wagner Moura", "Tareco e Mariola","Vassourau","2004");
        assertEquals("Série Teste", s.getTitulo());
        assertEquals("Drama", s.getGenero());
    }

    /**
     * Esse teste verifica a atribuição correta de temporadas para as séries.
     */
    @Test
    public void testAdicionarTemporada() {
        Review review1 = new Review(5, LocalDate.now(), "Ótimo!");
        Review review2 = new Review(3,LocalDate.now(),"Piorou");
        List<Review> reviews = List.of(review1, review2);
        Serie s = new Serie("Série Teste", "Ação", "2021", reviews, "Lázaro Ramos, Wagner Moura", "A paz","Japão","1945");
        Temporada t1 = new Temporada("1", "2021", "10");
        s.adicionarTemporada(t1);
        assertEquals(1, s.getTemporadas().size());
        assertEquals(t1, s.getTemporadas().get(0));
    }

    /**
     * Esse teste garante que o cálculo e atribuição corretos de avaliações e médias de notas.
     */
    @Test
    public void testAvaliacaoMedia() {
        List<Review> reviews = List.of();
        MediaManager manager = new MediaManager();
        Serie s = new Serie("Série Nota", "Ficção", "2020", reviews, "Lázaro Ramos, Wagner Moura", "Unplugged","Mar da Revolução","2012");
        Temporada t1 = new Temporada("1", "2020", "8");
        Temporada t2 = new Temporada("2", "2021", "10");

        s.adicionarTemporada(t1);
        s.adicionarTemporada(t2);
        t1.setFoiVista(true);
        t2.setFoiVista(true);
        manager.adicionarMidia(s);
        manager.adicionarTemporada(s, t1);
        manager.adicionarTemporada(s, t2);

        Review review1 = new Review(4, LocalDate.now(), "Boa");
        Review review2 = new Review(2, LocalDate.now(), "Fraca");

        manager.adicionarReviewTemporada(s, t1, review1);
        manager.adicionarReviewTemporada(s, t2, review2);
        assertEquals(3.0, s.getAvaliacaoMedia(), 0.01);
    }
}
