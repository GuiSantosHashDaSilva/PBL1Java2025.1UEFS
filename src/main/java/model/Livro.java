package model;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Clase que modelo objetos livro.
 */
public class Livro extends Midia implements Serializable{
    private String autor;
    private String editora;
    private String isbn;
    private boolean temcopia;
    private boolean foiVista;
    private double avaliacaoMedia;

    public Livro(String titulo, String genero, String anosaiu, List<Review> reviews,String autor, String editora, String isbn, boolean temcopia) {
        super(titulo, genero, anosaiu, reviews);
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.temcopia = temcopia;
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

    public String getAutor() { return autor; }

    public void setAutor(String autor) { this.autor = autor; }

    public String getEditora() { return editora; }

    public void setEditora(String editora) { this.editora = editora; }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public boolean isTemcopia() { return temcopia; }

    public void setTemcopia(boolean temcopia) { this.temcopia = temcopia; }

    public List<Review> getReviews() {
        return reviews;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

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

    @Override
    public String toString() {
        return String.format(
                "Livro: %s\nAutor: %s\nEditora: %s\nISBN: %s\nGênero: %s\nAno de lançamento: %d\nTem exemplar: %s\nFoi lido: %s\nReviews: %s",
                getTitulo(),
                autor,
                editora,
                isbn,
                getGenero(),
                getAnosaiu(),
                temcopia ? "Sim" : "Não",
                foiVista ? "Sim" : "Não",
                getReviews()
        );
    }

}
