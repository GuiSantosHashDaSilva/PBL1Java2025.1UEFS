import model.Filme;
import model.Review;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe que realiza os testes relacionados a filmes.
 */
public class FilmeTest {

    @Test
    public void testCriarFilme() {
        Review review1 = new Review(5, LocalDate.now(), "Ótimo!");
        Review review2 = new Review(3,LocalDate.now(),"Piorou");
        List<Review> reviews = List.of(review1, review2);
        Filme f = new Filme("Filme", "Ação", "2020", reviews, "Lázaro Ramos, Wagner Moura", "120min", "DVD", "110Min","Lázaro Ruim","Super Xandão");
        assertEquals("Filme", f.getTitulo());
    }

    @Test
    public void testAdicionarReview() {
        List<Review> reviews = List.of();
        Filme f = new Filme("Filme", "Ação", "2020", reviews, "Jão Grilo, Chicó", "Sexta-Feira", "PlayTV", "5Min","Xambão","Selton Melo");
        f.setFoiVista(true);
        f.adicionarReview(new Review(4, LocalDate.now(), "Bom"));
        assertEquals(1, f.getReviews().size());
    }
}