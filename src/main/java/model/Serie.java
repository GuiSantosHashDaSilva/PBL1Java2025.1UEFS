package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe modelo para os objetos Serie do programa
 */
public class Serie extends Audiovisual implements Serializable{
    private String anofinal;
    private List<Temporada> temporadas = new ArrayList<>();
    private double avaliacaoMedia;

    public Serie(String titulo, String genero, String anosaiu, List<Review> reviews,String elenco, String titulooriginal, String ondever, String anofinal) {
        super(titulo, genero, anosaiu, reviews ,elenco, titulooriginal, ondever);
        this.anofinal = anofinal;
    }

    public void adicionarTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public String getAnofinal() { return anofinal; }

    public void setAnofinal(String anofinal) { this.anofinal = anofinal; }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void atualizarAvaliacaoMedia() {
        int totalNotas = 0;
        int totalReviews = 0;

        for (Temporada temporada : temporadas) {
            for (Review review : temporada.getReviews()) {
                totalNotas += review.getNota();
                totalReviews++;
            }
        }

        if (totalReviews > 0) {
            avaliacaoMedia = (double) totalNotas / totalReviews;
        } else {
            avaliacaoMedia = 0.0;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Série: %s\nGênero: %s\nAno de lançamento: %s\nElenco: %s\nTitulo original: %s\nOnde ver: %s\nAno final: %s\nNota média: %.2f\nTemporadas: %s",
                getTitulo(),
                getGenero(),
                getAnosaiu(),
                getElenco(),
                getTitulooriginal(),
                getOndever(),
                anofinal,
                avaliacaoMedia,
                getTemporadas()
        );
    }

}
