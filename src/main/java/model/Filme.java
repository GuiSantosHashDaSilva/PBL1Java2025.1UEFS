package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que modelo para os objetos filme.
 */
public class Filme extends Audiovisual implements Serializable {
    private String duracao;
    private String diretor;
    private String roteirista;
    private boolean foiVista;

    private double avaliacaoMedia;

    public Filme(String titulo, String genero, String anosaiu, List<Review> reviews,String elenco, String titulooriginal, String ondever, String duracao, String diretor, String roteirista) {
        super(titulo, genero, anosaiu, reviews, elenco, titulooriginal, ondever);
        this.duracao = duracao;
        this.diretor = diretor;
        this.roteirista = roteirista;
    }

    public boolean isFoiVista() {
        return foiVista;
    }

    public void setFoiVista(boolean foiVista) {
        this.foiVista = foiVista;
    }

    public void adicionarReview(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviews() {return reviews;}

    public String getDuracao() { return duracao; }

    public void setDuracao(String duracao) { this.duracao = duracao; }

    public String getDiretor() { return diretor; }

    public void setDiretor(String diretor) { this.diretor = diretor; }

    public String getRoteiro() { return roteirista; }

    public void setRoteiro(String roteiro) { this.roteirista = roteiro; }

    public double getAvaliacaoMedia() { return avaliacaoMedia; }

    public void atualizarAvaliacaoMedia() {
        int totalNotas = 0;
        int totalReviews = 0;

        for (Review review : getReviews()) {
            totalNotas += review.getNota();
            totalReviews++;
        }


        if (totalReviews > 0) {
            avaliacaoMedia = (double) totalNotas / totalReviews;
        } else {
            avaliacaoMedia = 0.0;
        }
    }
    //onde duração diretor roteirista elenco review
    @Override
    public String toString() {
        return String.format(
                "Filme: %s\nTitulo original: %s\nDiretor: %s\nRoteirista: %s\nDuração: %s\nElenco: %s\nGênero: %s\nAno de lançamento: %s\nFoi assistido: %s\nOnde viu: %s\nReviews: %s",
                getTitulo(),
                getTitulooriginal(),
                diretor,
                roteirista,
                duracao,
                elenco,
                getGenero(),
                getAnosaiu(),
                foiVista ? "Sim" : "Não",
                getOndever(),
                getReviews()
        );
    }

}
