package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class TelaPrincipalController implements Initializable {

    private final MediaManager manager;

    public TelaPrincipalController(MediaManager manager) {
        this.manager = manager;
    }



    private Stage stage;
    private Scene scene;
    private Parent root;
    public Temporada temporadaSelecionada;
    public Livro livroSelecionado;
    public Filme filmeSelecionado;
    public Serie serieSelecionada;


    @FXML
    public Pane mainPane;
    @FXML
    public TabPane mainTabPane;
    @FXML
    public TextField caixaBusca;
    @FXML
    public Button botaoBusca;
    @FXML
    public Label labelErroBusca;
    @FXML
    public Label labelNaoTemMidia;
    @FXML
    public ListView<Livro> listViewLivros;
    @FXML
    public ListView<Filme> listViewFilmes;
    @FXML
    public ListView<Serie> listViewSeries;
    @FXML
    public Tab tabLivros, tabFilmes, tabSeries;
    @FXML
    public Label labelErroFiltro;
    @FXML
    public TextField caixaFiltroGenero, caixaFiltroAno;
    @FXML
    public Button botaoFiltro;
    @FXML
    public CheckBox checkOrdem;
    @FXML
    public Button botaoMudarCena;
    @FXML
    public AnchorPane anchorPaneLivro;
    @FXML
    public Label detalheTituloLivro, detalheGeneroLivro, detalheAnoLivro, detalheAutor, detalheEditora, detalheIsbn, detalheTemExemplar, detalheNotaMediaLivro;
    @FXML
    public CheckBox detalheFoiVistoLivro;
    @FXML
    public ListView<Review> listViewReviewsLivro, listViewReviewsFilme, listViewReviewsTemporada;
    @FXML
    public Button botaoAddReview;
    @FXML
    public Button botaoVoltarLivro;
    @FXML
    public TextField caixaComentario;
    @FXML
    public Slider sliderNota;
    @FXML
    public Pane paneReview;
    @FXML
    public AnchorPane detalhesPaneFilme;
    @FXML
    public Label detalheTituloFilme, detalheGeneroFilme, detalheAnoFilme, detalheTituloOriginalFilme, detalheDiretor, detalheDuracao, detalheRoteirista,detalheElencoFilme, detalheOndeVerFilme, detalheNotaMediaFilme;
    @FXML
    public Button botaoVoltarFilme;
    @FXML
    public CheckBox detalheFoiVistoFilme;
    @FXML
    public AnchorPane detalhesPaneSerie, detalhesPaneTemporada;
    @FXML
    public Label detalheTituloSerie, detalheGeneroSerie, detalheAnoSerie, detalheTituloOriginalSerie, detalheOndeVerSerie, detalheAnoFinalSerie, detalheElencoSerie, detalheNotaMediaSerie,detalheTituloTemporada, detalheAnoTemporada, detalheQtdEp;
    @FXML
    public CheckBox detalheFoiVistoTemporada;
    @FXML
    public ListView<Temporada> listViewTemporadas;
    @FXML
    public Button botaoIrParaAddTemporada;
    @FXML
    public Label erroMarcarVista;


    /**
     * Navega para a tela de adicionar temporada, passando a série atualmente selecionada.
     * Esta função é acionada pelo botão "Adicionar Temporada" no painel de detalhes da série.
     * @param e O evento de ação do clique do botão.
     * @throws IOException Se o arquivo FXML 'AdicionarMidia.fxml' não puder ser carregado.
     */
    public void irParaAddTemporada(ActionEvent e) throws IOException {
        // Verifica se uma série foi realmente selecionada
        if (serieSelecionada == null) {
            System.out.println("Nenhuma série selecionada para adicionar temporada.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFx/AdicionarMidia.fxml"));

        loader.setControllerFactory(controllerClass -> new AdicionarMidiaController(this.manager));

        Parent root = loader.load();

        // Pega a instância do controlador da tela que foi carregada
        AdicionarMidiaController adicionarController = loader.getController();
        // Passa a série selecionada para o outro controlador
        adicionarController.initData(this.serieSelecionada);

        // Troca a cena
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exibe os detalhes de um Livro selecionado no painel de detalhes correspondente.
     * Se o livro for nulo, o painel é escondido.
     * @param livro O objeto Livro cujos detalhes serão exibidos.
     */
    public void exibirDetalhesLivro(Livro livro) {

        if (livro != null) {
            // Preenche os labels com os dados do objeto livro
            detalheTituloLivro.setText(livro.getTitulo());
            detalheAutor.setText("Autor: " + livro.getAutor());
            detalheEditora.setText("Editora: " + livro.getEditora());
            detalheIsbn.setText("ISBN: " + livro.getIsbn());
            detalheAnoLivro.setText("Lançamento: " + livro.getAnosaiu());
            detalheGeneroLivro.setText("Gênero: " + livro.getGenero());
            detalheTemExemplar.setText("Tem Exemplar: " + (livro.isTemcopia() ? "Sim" : "Não"));
            detalheNotaMediaLivro.setText("Nota Média: " + livro.getAvaliacaoMedia());
            detalheFoiVistoLivro.setSelected(livro.isFoiVista());
            listViewReviewsLivro.getItems().clear();
            listViewReviewsLivro.getItems().addAll(livro.getReviews());


            anchorPaneLivro.setVisible(true);
            paneReview.setVisible(true);
        } else {
            anchorPaneLivro.setVisible(false);
            paneReview.setVisible(false);
        }
    }

    /**
     * Exibe os detalhes de um Filme selecionado no painel de detalhes correspondente.
     * Se o filme for nulo, o painel é escondido.
     * @param filme O objeto Filme cujos detalhes serão exibidos.
     */
    public void exibirDetalhesFilme(Filme filme) {
        if (filme != null) {
            // Preenche os labels com os dados do objeto filme
            detalheTituloFilme.setText(filme.getTitulo());
            detalheTituloOriginalFilme.setText("Título Original: " + filme.getTitulooriginal());
            detalheDiretor.setText("Diretor: " + filme.getDiretor());
            detalheDuracao.setText("Duração: " + filme.getDuracao());
            detalheRoteirista.setText("Roteirista: " + filme.getRoteiro());
            detalheElencoFilme.setText("Elenco: " + filme.getElenco());
            detalheAnoFilme.setText("Lançamento: " + filme.getAnosaiu());
            detalheGeneroFilme.setText("Gênero: " + filme.getGenero());
            detalheOndeVerFilme.setText("Onde Ver: " + filme.getOndever());
            detalheFoiVistoFilme.setSelected(filme.isFoiVista());
            detalheNotaMediaFilme.setText("Nota Média: " + filme.getAvaliacaoMedia());
            detalheFoiVistoFilme.setSelected(filme.isFoiVista());

            // Limpa e preenche a lista de reviews do filme
            listViewReviewsFilme.getItems().clear();
            listViewReviewsFilme.getItems().addAll(filme.getReviews());

            // Torna o painel de detalhes visível
            detalhesPaneFilme.setVisible(true);
            paneReview.setVisible(true);
        } else {
            detalhesPaneFilme.setVisible(false);
            paneReview.setVisible(false);
        }
    }

    /**
     * Exibe os detalhes de uma Série selecionada no painel de detalhes correspondente.
     * Também preenche a lista de temporadas da série. Se a série for nula, os painéis são escondidos.
     * @param serie O objeto Serie cujos detalhes serão exibidos.
     */
    public void exibirDetalhesSerie(Serie serie) {
        if (serie != null) {
            detalheTituloSerie.setText(serie.getTitulo());
            detalheAnoSerie.setText("Lançamento: " + serie.getAnosaiu());
            detalheAnoFinalSerie.setText("Final: " + serie.getAnofinal());
            detalheElencoSerie.setText("Elenco: " + serie.getElenco());
            detalheGeneroSerie.setText("Gênero: " + serie.getGenero());
            detalheNotaMediaSerie.setText("Nota Média: " + serie.getAvaliacaoMedia());
            detalheTituloOriginalSerie.setText("Titulo Original: " + serie.getTitulooriginal());
            detalheOndeVerSerie.setText("Onde Ver: " + serie.getOndever());

            //Limpa a lista de temporadas antes de adicionar as novas
            listViewTemporadas.getItems().clear();
            listViewTemporadas.getItems().addAll(serie.getTemporadas());

            detalhesPaneSerie.setVisible(true);
            detalhesPaneTemporada.setVisible(false);

        } else {
            detalhesPaneSerie.setVisible(false);
            detalhesPaneTemporada.setVisible(false);
        }
    }

    /**
     * Exibe os detalhes de uma Temporada selecionada no painel de detalhes correspondente.
     * Se a temporada for nula, o painel é escondido.
     * @param temporada O objeto Temporada cujos detalhes serão exibidos.
     */
    private void exibirDetalhesTemporada(Temporada temporada) {
        if (temporada != null) {
            detalheTituloTemporada.setText(temporada.getNumero());
            detalheAnoTemporada.setText("Ano de Lançamento: " + temporada.getAnotempo());
            detalheQtdEp.setText("Quantidade de Episódios: " + temporada.getQtdeps());
            detalheFoiVistoTemporada.setSelected(temporada.isFoiVista());
            listViewReviewsTemporada.getItems().clear();
            listViewReviewsTemporada.getItems().addAll(temporada.getReviews());
            detalhesPaneTemporada.setVisible(true);
            paneReview.setVisible(true);
        } else {
            detalhesPaneTemporada.setVisible(false);
            paneReview.setVisible(false);
        }
    }

    /**
     * Esconde todos os painéis de detalhes. Acionado por botões "Voltar" ou ao trocar de aba.
     * @param event O evento de ação do clique do botão.
     */
    public void esconderDetalhes(ActionEvent event) {
        anchorPaneLivro.setVisible(false);
        detalhesPaneFilme.setVisible(false);
        detalhesPaneSerie.setVisible(false);
        paneReview.setVisible(false);
    }

    /**
     * Esconde o painel de detalhes específico da temporada.
     * @param event O evento de ação do clique do botão.
     */
    public void esconderDetalhesTemporada(ActionEvent event) {
        detalhesPaneTemporada.setVisible(false);
        paneReview.setVisible(false);
    }

    /**
     * Adiciona uma nova review à mídia ou temporada selecionada.
     * Lê os dados dos campos de input, valida, e chama o manager para adicionar a review.
     * Atualiza a interface para mostrar a nova review e a nova nota média.
     * Exibe um erro se a mídia/temporada não estiver marcada como vista.
     * @param event O evento de ação do clique do botão "Adicionar Review".
     */
    public void addReview(ActionEvent event) throws IOException, ClassNotFoundException {
        erroMarcarVista.setVisible(false);

        String comentario = caixaComentario.getText();
        if (comentario.isEmpty()){
            System.out.println("O campo de comentário não pode estar vazio.");
            return;
        }
        double nota = sliderNota.getValue();
        Review novaReview = new Review(nota, LocalDate.now(), comentario);

        boolean sucesso = false;

        // Lógica para adicionar review a Livro
        if (livroSelecionado != null) {
            sucesso = manager.adicionarReview(livroSelecionado, novaReview);
            if (sucesso) {
                listViewReviewsLivro.getItems().clear();
                listViewReviewsLivro.getItems().addAll(livroSelecionado.getReviews());
                detalheNotaMediaLivro.setText("Nota Média: " + String.format("%.2f", livroSelecionado.getAvaliacaoMedia()));
            }

            // Lógica para adicionar review a Filme
        } else if (filmeSelecionado != null) {
            sucesso = manager.adicionarReview(filmeSelecionado, novaReview);
            if (sucesso) {
                listViewReviewsFilme.getItems().clear();
                listViewReviewsFilme.getItems().addAll(filmeSelecionado.getReviews());
                detalheNotaMediaFilme.setText("Nota Média: " + String.format("%.2f", filmeSelecionado.getAvaliacaoMedia()));
            }

            // Lógica para adicionar review a uma Temporada
        } else if (serieSelecionada != null && temporadaSelecionada != null) {
            sucesso = manager.adicionarReviewTemporada(serieSelecionada, temporadaSelecionada, novaReview);
            if (sucesso) {
                listViewReviewsTemporada.getItems().clear();
                listViewReviewsTemporada.getItems().addAll(temporadaSelecionada.getReviews());
                detalheNotaMediaSerie.setText("Nota Média: " + String.format("%.2f", serieSelecionada.getAvaliacaoMedia()));
            }
        } else {
            System.out.println("Nenhuma mídia ou temporada selecionada para adicionar review.");
            return;
        }

        // Se a operação falhou porque a mídia não foi vista, mostra o erro
        if (!sucesso) {
            erroMarcarVista.setVisible(true);
        } else {
            // Se foi sucesso, limpa os campos de input
            caixaComentario.clear();
            sliderNota.setValue(3);
        }
    }

    /**
     * Marca o Livro ou Filme atualmente selecionado como "visto/lido".
     * @param event O evento de ação do clique do botão.
     */
    public void marcarVisto(ActionEvent event) throws IOException, ClassNotFoundException {
        Midia midiaSelecionada = null;

        // Verifica qual tipo de mídia está selecionada
        if (livroSelecionado != null) {
            midiaSelecionada = livroSelecionado;
            detalheFoiVistoLivro.setSelected(true); // Atualiza a interface
        } else if (filmeSelecionado != null) {
            midiaSelecionada = filmeSelecionado;
            detalheFoiVistoFilme.setSelected(true); // Atualiza a interface
        }

        if (midiaSelecionada != null) {
            manager.marcarComoVista(midiaSelecionada);
            System.out.println("Midia marcada como vista");
        } else {
            System.out.println("Nenhuma mídia (livro ou filme) selecionada.");
        }
    }

    /**
     * Marca a Temporada atualmente selecionada como "vista".
     * @param event O evento de ação do clique do botão.
     */
    public void marcarTemporadaVista(ActionEvent event) throws IOException {
        // Verifica se uma temporada foi realmente selecionada na lista
        if (this.temporadaSelecionada != null) {
            manager.marcarTemporadaComoVista(this.temporadaSelecionada);
            // Atualiza o CheckBox na tela
            detalheFoiVistoTemporada.setSelected(true);
        } else {
            System.out.println("Nenhuma temporada selecionada para marcar como vista.");
        }
    }

    /**
     * Navega para a tela de Adicionar Mídia em seu estado padrão.
     * @param e O evento de ação do clique do botão principal "Adicionar Mídia".
     * @throws IOException Se o arquivo FXML não puder ser carregado.
     */
    public void mudarTelaParaAdd(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFx/AdicionarMidia.fxml"));

        loader.setControllerFactory(controllerClass -> new AdicionarMidiaController(this.manager));

        root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}

    /**
     * Realiza uma busca na lista de mídias com base no termo inserido na caixa de busca principal.
     * Atualiza a ListView da aba ativa para exibir apenas os resultados da busca.
     * @param event O evento de ação do clique do botão "Buscar".
     */
    public void buscarMidia(ActionEvent event) {
        String termo = caixaBusca.getText();

        if (termo == null || termo.isEmpty()) {
            atualizarListaVisivel();
            return;
        }

        Tab tabAtiva = mainTabPane.getSelectionModel().getSelectedItem();
        if (tabAtiva == null) {
            return;
        }

        int tipo = 0;
        if (tabAtiva.getId().equals("tabLivros")) {
            tipo = 1;
        } else if (tabAtiva.getId().equals("tabFilmes")) {
            tipo = 2;
        } else if (tabAtiva.getId().equals("tabSeries")) {
            tipo = 3;
        }


        List<Midia> resultadosDaBusca = manager.buscarPorTitulo(tipo, termo);

        listViewLivros.getItems().clear();
        listViewFilmes.getItems().clear();
        listViewSeries.getItems().clear();

        labelNaoTemMidia.setVisible(false);
        if (resultadosDaBusca.isEmpty()) {
            labelNaoTemMidia.setText("Nenhuma mídia encontrada.");
            labelNaoTemMidia.setVisible(true);
        }

        for (Midia item : resultadosDaBusca) {
            if (item instanceof Livro) {
                listViewLivros.getItems().add((Livro) item);
            } else if (item instanceof Filme) {
                listViewFilmes.getItems().add((Filme) item);
            } else if (item instanceof Serie) {
                listViewSeries.getItems().add((Serie) item);
            }
        }
        caixaBusca.clear();

        System.out.println("--- FIM DA BUSCA ---\n");
    }

    /**
     * Aciona a atualização da lista visível com base nos filtros de gênero, ano e ordem.
     * @param event O evento de ação do clique do botão "Filtrar".
     */
    public void enviarFiltroOrdem(ActionEvent event) {
        atualizarListaVisivel();
    }

    /**
     * Função que atualiza a ListView visível na tela.
     * Lê os valores dos filtros, chama o manager para obter a lista filtrada e ordenada de mídias,
     * e então popula a ListView com os resultados.
     */
    private void atualizarListaVisivel() {
        String genero = caixaFiltroGenero.getText();
        String ano = caixaFiltroAno.getText();
        int ordem = checkOrdem.isSelected() ? 1 : 2;
        labelErroFiltro.setVisible(false);

        // Determina qual aba está ativa
        Tab tabAtiva = mainTabPane.getSelectionModel().getSelectedItem();
        if (tabAtiva == null) { return;}

        ListView<String> listaAtiva = null;

        int tabPaneId = 0;
        if (tabAtiva.getId().equals("tabLivros")) {
            tabPaneId = 1;
        } else if (tabAtiva.getId().equals("tabFilmes")) {
            tabPaneId = 2;
        } else if (tabAtiva.getId().equals("tabSeries")) {
            tabPaneId = 3;
        }


        // Busca os dados no manager usando os filtros e a aba ativa
        List<Midia> itensEncontrados = manager.listarMidias(genero, ano, ordem, tabPaneId);

        listViewLivros.getItems().clear();
        listViewFilmes.getItems().clear();
        listViewSeries.getItems().clear();

        if (itensEncontrados.isEmpty()) {
            labelErroFiltro.setVisible(true);
        }

        for (Midia item : itensEncontrados) {
            if (item instanceof Livro) {
                listViewLivros.getItems().add((Livro) item);
            } else if (item instanceof Filme) {
                listViewFilmes.getItems().add((Filme) item);
            } else if (item instanceof Serie) {
                listViewSeries.getItems().add((Serie) item);
            }
        }


        System.out.println("Lista da aba '" + tabAtiva.getText() + "' atualizada com " + itensEncontrados.size() + " itens.");
    }

    /**
     * Função executada automaticamente pelo JavaFX após o carregamento do FXML.
     * Configura o estado inicial da tela, incluindo os listeners para as ListViews e o TabPane.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Esconde todos os painéis de detalhes no início
        anchorPaneLivro.setVisible(false);
        detalhesPaneFilme.setVisible(false);
        detalhesPaneSerie.setVisible(false);
        detalhesPaneTemporada.setVisible(false);

        // Configuração para livros
        listViewLivros.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Livro livro, boolean empty) {
                super.updateItem(livro, empty);
                setText(empty || livro == null ? null : MediaManager.exibirMidiaComNota(livro));
            }
        });
        listViewLivros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.livroSelecionado = newValue;
            this.filmeSelecionado = null;
            this.serieSelecionada = null;
            exibirDetalhesLivro(newValue);
        });

        // Configuração para filmes
        listViewFilmes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Filme filme, boolean empty) {
                super.updateItem(filme, empty);
                setText(empty || filme == null ? null : MediaManager.exibirMidiaComNota(filme));
            }
        });
        listViewFilmes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.filmeSelecionado = newValue;
            this.livroSelecionado = null;
            this.serieSelecionada = null;
            exibirDetalhesFilme(newValue);
        });

        // Configuração para séries
        listViewSeries.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Serie serie, boolean empty) {
                super.updateItem(serie, empty);
                setText(empty || serie == null ? null : MediaManager.exibirMidiaComNota(serie));
            }
        });
        listViewSeries.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.serieSelecionada = newValue;
            this.livroSelecionado = null;
            this.filmeSelecionado = null;
            exibirDetalhesSerie(newValue);
        });

        // Configuração para a lista de temporadas
        listViewTemporadas.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Temporada temporada, boolean empty) {
                super.updateItem(temporada, empty);
                setText(empty || temporada == null ? null : String.format("Temporada %s (%s) - %s episódios", temporada.getNumero(), temporada.getAnotempo(), temporada.getQtdeps()));
            }
        });
        listViewTemporadas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.temporadaSelecionada = newValue;
            exibirDetalhesTemporada(newValue);
        });

        // Configuração do listener do tabpane
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            esconderDetalhes(null);
            atualizarListaVisivel();
        });

        // Carga inicial dos dados
        atualizarListaVisivel();
    }

}