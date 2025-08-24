package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo superclasse para todas as mídias no programa
 */
public abstract class Midia implements Serializable {
    private String titulo;
    private String genero;
    private String anosaiu;
    public List<Review> reviews;

    public Midia(String titulo, String genero, String anosaiu, List<Review> reviews) {
        this.titulo = titulo;
        this.genero = genero;
        this.anosaiu = anosaiu;
        this.reviews = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {return genero; }

    public void setGenero(String genero) { this.genero = genero; }

    public String getAnosaiu() { return anosaiu; }

    public void setAnosaiu(String anosaiu) { this.anosaiu = anosaiu; }

    public List<Review> getReviews() {
        return reviews;
    }
}
