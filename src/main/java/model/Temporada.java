package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo para instanciação de todas.
 */
public class Temporada implements Serializable {
    public String numero;
    private String anotempo;
    private String qtdeps;
    private boolean foiVista;
    private List<Review> reviews = new ArrayList<>();

    public Temporada(String numero, String anotempo, String qtdeps) {
        this.numero = numero;
        this.anotempo = anotempo;
        this.qtdeps = qtdeps;
    }

    public boolean isFoiVista() {
        return foiVista;
    }

    public void setFoiVista(boolean foiVista) {
        this.foiVista = foiVista;
    }

    public String getNumero() { return numero; }

    public void setNumero(String numero) { this.numero = numero; }

    public String getAnotempo() { return anotempo; }

    public void setAnotempo(String anotempo) { this.anotempo = anotempo; }

    public String getQtdeps() { return qtdeps; }

    public void setQtdeps(String qtdeps) { this.qtdeps = qtdeps; }

    public void adicionarReview(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return String.format(
                "Temporada %d\nAno: %s | Episódios: %s | Foi assistida: %s | Reviews: %s",
                numero,
                anotempo,
                qtdeps,
                foiVista ? "Sim" : "Não",
                getReviews()
        );
    }

}
