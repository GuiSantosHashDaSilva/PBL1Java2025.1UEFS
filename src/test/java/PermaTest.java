import model.*;
import controller.MediaManager;
import model.Review;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe para teste de preservação de dados.
 */
public class PermaTest {

    /**
     * Essa função garante a permanência dos dados gerados, instanciando objetos,
     * os serializando, removendo eles do escopo do código, e recuperando esses dados pela desserialização.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void testPermanencia()
            throws IOException, ClassNotFoundException {
        Review review1 = new Review(5, LocalDate.now(), "Ótimo!");
        Review review2 = new Review(3,LocalDate.now(),"Piorou");
        List<Review> reviews1 = List.of(review1, review2);
        Review review3 = new Review(4, LocalDate.now(), "Bom");
        Review review4 = new Review(2,LocalDate.now(),"Mudei de ideia");
        List<Review> reviews2 = List.of(review3, review4);
        MediaManager m = new MediaManager();
        Livro l1 = new Livro("Telegrama", "Romance", "1949", reviews1, "Zeca Baleiro", "PetShop","426",true);
        Livro l2 = new Livro("Odara", "Ficção", "2013", reviews2, "Caetano", "Multishow","518",true);
        m.adicionarMidia(l1);
        m.adicionarMidia(l2);
        m.salvarMidia();
        m.removerMidia(l1);
        m.removerMidia(l2);
        m.carregarMidia();
        assertEquals ("Telegrama", l1.getTitulo());
        assertEquals ("Odara", l2.getTitulo());
    }
}
