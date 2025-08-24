package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A classe Review do programa.
 * Essa classe é o modelo das reviews de todos as mídias.
 */
public class Review implements Serializable {
    private double nota;
    private LocalDate data;
    private String comentario;

    public Review(double nota, LocalDate data, String comentario) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        this.nota = nota;
        this.data = data;
        this.comentario = comentario;
    }

    public double getNota() { return nota; }
    public LocalDate getData() { return data; }
    public String getComentario() { return comentario; }

    @Override
    public String toString() {
        return String.format("Nota: %.2f | Data: %s | Comentário: %s",
                nota,
                data.toString(),
                comentario);
    }

}
