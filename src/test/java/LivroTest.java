import model.Livro;
import model.Review;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes para livros
 */
public class LivroTest {

    /**
     * Esse teste verifica a instanciação de livros, gerando um objeto e verificando se ele está presente na lista de livros.
     */
    @Test
    public void testCriacaoLivro() {
        Review review1 = new Review(5, LocalDate.now(), "Ótimo!");
        Review review2 = new Review(3,LocalDate.now(),"Piorou");
        List<Review> reviews = List.of(review1, review2);
        Livro l = new Livro("Samurai", "Ficção", "1949", reviews, "Djavan", "Luz","448",true);
        assertEquals("Samurai", l.getTitulo());
    }

    /**
     * Esse teste garante o funcionamento da atribuição de "review".
     */
    @Test
    public void testAdicionarReviewComVista() {
        List<Review> reviews = List.of();
        Livro l = new Livro("Topázio", "Drama", "2000", reviews, "Djavan", "Meu Lado","448",true);
        l.setFoiVista(true);
        l.adicionarReview(new Review(4, LocalDate.now(), "Legal"));
        assertFalse(l.getReviews().isEmpty());
    }

    /**
     * Esse teste verifica se as mídias são marcadas como vista de forma correta.
     */
    @Test
    public void testFoiVista() {
        Review review1 = new Review(5, LocalDate.now(), "Ótimo!");
        Review review2 = new Review(3,LocalDate.now(),"Piorou");
        List<Review> reviews = List.of(review1, review2);
        Livro l = new Livro("Kremilin", "Drama", "2000", reviews, "Jasmim", "Vaso Quebrado", "Fugir de Mim",true);
        l.setFoiVista(true);
        assertTrue(l.isFoiVista());
    }
}