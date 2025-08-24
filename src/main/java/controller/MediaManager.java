package controller;

import java.time.LocalDate;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.io.IOException;

import model.*;




/**
 * O conector entre os Models e o View.
 * Essa classe realiza as funções do projeto e faz associação dos métodos solicitados na View e os Modelos relacionados.
 */
public class MediaManager {
    public List<Midia> midias;


    public MediaManager() {
        midias = new ArrayList<>();
    }

    public void adicionarMidia(Midia midia) {
        midias.add(midia);
    }

    public void removerMidia(Midia midia) {
        midias.remove(midia);
    }

    public List<Midia> listarMidiasManejadas() {
        return new ArrayList<>(midias);
    }


    /**
     * Essa função realiza a verificação se um objeto foi visto antes que uma "review" possa ser escrita.
     * @param midia mídia na qual a review vai ser adicionada.
     * @param review review a ser adicionada.
     * @return Retorna true se a review foi adicionada, false caso contrário.
     */
    public boolean adicionarReview(Midia midia, Review review) {
        if (midia instanceof Livro livro) {
            if (livro.isFoiVista()) {
                livro.adicionarReview(review);
                livro.atualizarAvaliacaoMedia();
                return true;
            } else {
                System.out.println("Você precisa ler o livro antes de adicionar uma review.");
                return false;
            }
        } else if (midia instanceof Filme filme) {
            if (filme.isFoiVista()) {
                filme.adicionarReview(review);
                filme.atualizarAvaliacaoMedia();
                return true;
            } else {
                System.out.println("Você precisa assistir ao filme antes de adicionar uma review.");
                return false;
            }
        } else {
            System.out.println("Esta mídia não suporta reviews diretamente.");
            return false;
        }
    }

    /**
     * Essa função atribuí uma temporada a uma série.
     * @param serie série a receber temporada.
     * @param temporada temporada a ser atribuída a uma série.
     */
    public void adicionarTemporada(Serie serie, Temporada temporada) {
        serie.adicionarTemporada(temporada);
    }

    /**
     * Essa função adiciona uma "review" a uma temporada específica de uma série.
     * @param serie série a qual a temporada pertence.
     * @param temporada temporada que vai receber a "review".
     * @param review review a ser atribuída.
     * @return Retorna true se a review foi adicionada, false caso contrário.
     */
    public boolean adicionarReviewTemporada(Serie serie, Temporada temporada, Review review) {
        if (temporada.isFoiVista()) {
            temporada.adicionarReview(review);
            serie.atualizarAvaliacaoMedia();
            return true;
        } else {
            System.out.println("Você precisa assistir à temporada antes de adicionar uma review.");
            return false;
        }
    }

    /**
     * Essa função serializa a lista "midias" num fluxo de bytes armazenado no "Arquivo_Midias.txt" preservando o conteúdo dela na memória permanente da máquina.
     * @throws IOException Dispara uma mensagem no caso de um erro de entrada e saida.
     * @throws ClassNotFoundException Dispara uma mensagem no caso de que o arquivo de permanência não seja encontrado.
     */
    public void salvarMidia()
            throws IOException, ClassNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("Arquivo_Midias.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
        oos.writeObject(midias);
        oos.flush();
        oos.close();
    }

    /**
     * Essa é a função de desserialização que tem o objetivo de carregar os dados armazenados ná máquina de volta para o código.
     * @throws IOException Dispara uma mensagem no caso de um erro de entrada e saida.
     * @throws ClassNotFoundException Dispara uma mensagem no caso de que o arquivo de permanência não seja encontrado.
     */
    public void carregarMidia()
            throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("Arquivo_Midias.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        midias = (List<Midia>) objectInputStream.readObject();
        objectInputStream.close();
    }

    /**
     * Lista as mídias com base nos filtros e ordenação selecionados.
     * Cada tipo de mídia é adicionada na sua respetiva lista e lida na ordem e filtragem selecionadas
     * @param generoFiltro Filtro de gênero.
     * @param anoFiltro Filtro de ano.
     * @param ordem ordenação selecionada.
     * @param tabPane aba selecionada.
     */
    public List<Midia> listarMidias(String generoFiltro, String anoFiltro, int ordem, int tabPane) {
        System.out.println("\nIniciando listarMidias para tabPane: " + tabPane + " ---");

        // verifica o conteúdo total antes dos filtros
        long totalLivros = listarMidiasManejadas().stream().filter(m -> m instanceof Livro).count();
        long totalFilmes = listarMidiasManejadas().stream().filter(m -> m instanceof Filme).count();
        long totalSeries = listarMidiasManejadas().stream().filter(m -> m instanceof Serie).count();
        System.out.println("Conteúdo total na memória: " + totalLivros + " livros, " + totalFilmes + " filmes, " + totalSeries + " séries.");

        List<Midia> midiasFiltradas = new ArrayList<>();
        for (Midia midia : listarMidiasManejadas()) {
            boolean generoOk = generoFiltro == null || generoFiltro.isEmpty() || midia.getGenero().toLowerCase().contains(generoFiltro.toLowerCase());
            boolean anoOk = anoFiltro == null || anoFiltro.isEmpty() || midia.getAnosaiu().toLowerCase().contains(anoFiltro.toLowerCase());

            if (generoOk && anoOk) {
                midiasFiltradas.add(midia);
            }
        }
        System.out.println("Após filtros de texto (gênero/ano), restaram " + midiasFiltradas.size() + " mídias.");

        List<Midia> listaParaExibir = new ArrayList<>();
        if (tabPane == 1) {
            for (Midia midia : midiasFiltradas) {
                if (midia instanceof Livro) {
                    listaParaExibir.add(midia);
                }
            }
        } else if (tabPane == 2) {
            for (Midia midia : midiasFiltradas) {
                if (midia instanceof Filme) {
                    listaParaExibir.add(midia);
                }
            }
        } else if (tabPane == 3) {
            for (Midia midia : midiasFiltradas) {
                if (midia instanceof Serie) {
                    listaParaExibir.add(midia);
                }
            }
        }
        System.out.println("Após filtro de tipo (instanceof), a lista para exibir tem " + listaParaExibir.size() + " itens.");

        try {
            if (ordem == 1) {
                listaParaExibir.sort(Comparator.comparingDouble(MediaManager::obterNotaMedia).reversed());
            } else if (ordem == 2) {
                listaParaExibir.sort(Comparator.comparingDouble(MediaManager::obterNotaMedia));
            }
            System.out.println("Após a ordenação, a lista para exibir tem " + listaParaExibir.size() + " itens.");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante a ordenação!");
            e.printStackTrace();
        }


        System.out.println("A lista final formatada tem " + listaParaExibir.size() + " strings.");
        System.out.println("Finalizando listarMidias ---");

        return listaParaExibir;
    }

    /**
     * Função utilizada para obter as notas médias das mídias solicitadas.
     * @param midia mídia que é desejado ver a média da nota.
     * @return Média da nota da mídia solicitada.
     */
    public static double obterNotaMedia(Midia midia) {
        if (midia instanceof Livro livro) {
            return livro.getAvaliacaoMedia();
        } else if (midia instanceof Filme filme) {
            return filme.getAvaliacaoMedia();
        } else if (midia instanceof Serie serie) {
            return serie.getAvaliacaoMedia();
        }
        return 0.0;
    }

    /**
     * Exibe a mídia solicitada junto ao seu gênero e nota.
     * @param midia mídia a ser exibida.
     */
    public static String exibirMidiaComNota(Midia midia) {
        double nota = obterNotaMedia(midia);
        String tituloExibido = midia.getTitulo();
        String generoExibido = midia.getGenero();
        String anoExibido = midia.getAnosaiu();

        return String.format("- %s (%s) | Gênero: %s | Nota média: %.2f%n",
                tituloExibido, anoExibido, generoExibido, nota);
    }

    /**
     * Marca uma mídia como vista para que uma "review" possa ser feita.
     * @param midia midia a ser marcada como vista.
     */
    public void marcarComoVista(Midia midia) throws IOException, ClassNotFoundException {
        if (midia instanceof Livro livro) {
            livro.setFoiVista(true);
            salvarMidia();
            System.out.println("Livro '" + livro.getTitulo() + "' marcado como lido.");
        } else if (midia instanceof Filme filme) {
            filme.setFoiVista(true);
            salvarMidia();
            System.out.println("Filme '" + filme.getTitulo() + "' marcado como assistido.");
        } else {
            System.out.println("Esta mídia não pode ser marcada como vista diretamente.");
        }
    }

    /**
     * Marca uma temporada de uma série como vista para que uma "review" possa ser feita.
     * @param temporada temporada a ser marcada como vista.
     */
    public void marcarTemporadaComoVista(Temporada temporada) {
        if (temporada != null) {
            temporada.setFoiVista(true);
            try {
                salvarMidia();
                System.out.println("Temporada " + temporada.getNumero() + " marcada como assistida.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao salvar a mídia após marcar temporada como vista.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Recalcula a avaliação média de todas as mídias na lista.
     * Essencial para garantir que as notas estejam atualizadas ao carregar os dados.
     */
    public void recalcularTodasAvaliacoes() {
        for (Midia midia : this.midias) {
            if (midia instanceof Livro livro) {
                livro.atualizarAvaliacaoMedia();
            } else if (midia instanceof Filme filme) {
                filme.atualizarAvaliacaoMedia();
            } else if (midia instanceof Serie serie) {
                serie.atualizarAvaliacaoMedia();
            }
        }
        System.out.println("Notas médias de todas as mídias foram recalculadas.");
    }

    /**
     * O buscador geral de mídias.
     * Essa função permite buscar mídias por diversos parâmetros.
     * @param tipo tipo de mídia a ser buscada.
     * @param termo termo sendo buscado.
     */
    public List<Midia> buscarPorTitulo(int tipo, String termo) {

        final String termoLowerCase = termo.toLowerCase();
        List<Midia> resultados;

        if (tipo == 1) { // Livros
            resultados = listarMidiasManejadas().stream()
                    .filter(m -> m instanceof Livro)
                    .map(m -> (Livro) m)
                    .filter(l ->
                            l.getTitulo().toLowerCase().contains(termoLowerCase) ||
                                    l.getAutor().toLowerCase().contains(termoLowerCase) ||
                                    l.getIsbn().toLowerCase().contains(termoLowerCase) ||
                                    l.getGenero().toLowerCase().contains(termoLowerCase) ||
                                    l.getAnosaiu().contains(termoLowerCase)
                    )
                    .collect(Collectors.toList());
        } else if (tipo == 2) { // Filmes
            resultados = listarMidiasManejadas().stream()
                    .filter(m -> m instanceof Filme)
                    .map(m -> (Filme) m)
                    .filter(f ->
                            f.getTitulo().toLowerCase().contains(termoLowerCase) ||
                                    f.getDiretor().toLowerCase().contains(termoLowerCase) ||
                                    f.getGenero().toLowerCase().contains(termoLowerCase) ||
                                    f.getAnosaiu().contains(termoLowerCase) ||
                                    f.getElenco().toLowerCase().contains(termoLowerCase)
                    )
                    .collect(Collectors.toList());
        } else { // Séries (tipo == 3)
            resultados = listarMidiasManejadas().stream()
                    .filter(m -> m instanceof Serie)
                    .filter(s -> s.getTitulo().toLowerCase().contains(termoLowerCase))
                    .collect(Collectors.toList());
        }


        return resultados;
    }


}
