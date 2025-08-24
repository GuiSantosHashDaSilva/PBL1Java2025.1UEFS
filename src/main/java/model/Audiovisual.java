package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Super classe para Filme e Série.
 * Essa classe é o segundo nível da cadeia de hieararquia.
 */
public abstract class Audiovisual extends Midia implements Serializable{
    public String elenco;
    private String titulooriginal;
    private String ondever;

    public Audiovisual(String titulo, String genero, String anosaiu, List<Review> reviews,String elenco, String titulooriginal, String ondever) {
        super(titulo, genero, anosaiu, reviews);
        this.elenco = elenco;
        this.titulooriginal = titulooriginal;
        this.ondever = ondever;
    }

    public String getElenco() { return elenco; }

    public void setElenco(String elenco) { this.elenco = elenco; }

    public String getTitulooriginal() { return titulooriginal; }

    public void setTitulooriginal(String titulooriginal) { this.titulooriginal = titulooriginal; }

    public String getOndever() { return ondever; }

    public void setOndever(String ondever) { this.ondever = ondever; }
}
