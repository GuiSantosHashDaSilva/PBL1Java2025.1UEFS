import model.Review;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe para testes das "reviews".
 */
public class ReviewTest {

    /**
     * Essa função verifica a atribuição correta de notas válidas.
     */
    @Test
    public void testNotaValida() {
        Review r = new Review(5, LocalDate.now(), "Ótimo!");
        assertEquals(5, r.getNota());
    }

    /**
     * Essa função garante o erro caso uma nota inválida seja submetida.
     */
    @Test
    public void testNotaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Review(6, LocalDate.now(), "Nota inválida"));
    }

    /**
     * Essa função garante a atribuição correta de comentários
     */
    @Test
    public void testComentario() {
        Review r = new Review(3, LocalDate.now(), "Bom");
        assertEquals("Bom", r.getComentario());
    }
}