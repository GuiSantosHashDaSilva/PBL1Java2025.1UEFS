package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdicionarMidiaController {

    private final MediaManager manager;
    private Serie serieAlvo;

    public AdicionarMidiaController(MediaManager manager) {
        this.manager = manager;
    }


    @FXML
    public Button botaoVoltarCena, botaoAdicionarMidia;
    @FXML
    public Tab tabAddLivro, tabAddFilme, tabAddSerie;
    @FXML
    public TextField caixaElencoSerie, caixaTituloOriginalSerie, caixaOndeViuSerie, caixaTituloTemporada;
    @FXML
    public DatePicker dateAnoFinal;
    @FXML
    public DatePicker dateAnoTemporada;
    @FXML
    public TextField caixaQtdEpTemporada;
    @FXML
    public Button botaoAdicionarTemporada;
    @FXML
    public Label labelErroCampoVazio;
    @FXML
    public TextField caixaTituloMidia;
    @FXML
    public TextField caixaGeneroMidia;
    @FXML
    public DatePicker datePickerAnoMidia;
    @FXML
    public TabPane tabPaneAddMidia;
    @FXML
    public TextField caixaElencoFilme;
    @FXML
    public TextField caixaAutor;
    @FXML
    public TextField caixaEditor;
    @FXML
    public TextField caixaIsbn;
    @FXML
    public CheckBox checkExemplar;
    @FXML
    public TextField caixaTituloOriginalFilme;
    @FXML
    public TextField caixaOndeViuFilme;
    @FXML
    public TextField caixaDuracaoFilme;
    @FXML
    public TextField caixaDirecaoFilme;
    @FXML
    public TextField caixaRoteiristaFilme;
    @FXML
    public AnchorPane paneAddTemporada;
    @FXML
    public Label labelSerieAlvoTemporada;


    /**
     * Configura a tela para o modo "Adicionar Temporada".
     * Essa função é chamada a partir da tela principal para passar a série específica
     * onde uma nova temporada será adicionada.
     * @param serie O objeto Serie que receberá a nova temporada.
     */
    public void initData(Serie serie) {
        this.serieAlvo = serie;
        tabPaneAddMidia.setDisable(true);

        // Atualiza o título da tela para dar contexto ao usuário
        labelSerieAlvoTemporada.setText("Adicionando temporada para: " + serie.getTitulo());
        paneAddTemporada.setVisible(true);
    }

    /**
     * Navega de volta para a tela principal da aplicação.
     * Garante que a instância do MediaManager seja passada para o TelaPrincipalController.
     * @param e O evento de ação do clique do botão "Voltar".
     * @throws IOException Se o arquivo FXML da tela principal não puder ser carregado.
     */
    public void mudarTelaParaPrincipal(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFx/TelaPrincipal.fxml"));

        loader.setControllerFactory(controllerClass -> new TelaPrincipalController(this.manager));

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adiciona uma nova mídia (Livro, Filme ou Série) na coleção.
     * Valida se todos os campos necessários estão preenchidos antes de criar o objeto.
     * Se a validação falhar, exibe uma mensagem de erro. Se for bem-sucedido,
     * salva os dados e retorna à tela principal.
     * @param e O evento de ação do clique do botão "Adicionar Mídia".
     */
    public void adicionarMidia(ActionEvent e)
            throws IOException, ClassNotFoundException {

        labelErroCampoVazio.setVisible(false);

        Tab tabAtiva = tabPaneAddMidia.getSelectionModel().getSelectedItem();


        // Verifica se os campos compartilhados por todas as mídias estão preenchidos
        if (caixaTituloMidia.getText().isEmpty() ||
                caixaGeneroMidia.getText().isEmpty() ||
                datePickerAnoMidia.getValue() == null) {

            labelErroCampoVazio.setVisible(true);
            return;
        }

        // Pega os valores dos campos comuns após a validação
        String titulo = caixaTituloMidia.getText();
        String genero = caixaGeneroMidia.getText();
        LocalDate ano = datePickerAnoMidia.getValue();
        String anoFormatado = ano.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Review> reviews = new ArrayList<>();

        if (tabAtiva.getId().equals("tabAddLivro")) {
            if (caixaAutor.getText().isEmpty() || caixaEditor.getText().isEmpty() || caixaIsbn.getText().isEmpty()) {
                labelErroCampoVazio.setVisible(true);
                return;
            }
            String autor = caixaAutor.getText();
            String editor = caixaEditor.getText();
            String isbn = caixaIsbn.getText();
            boolean exemplar = checkExemplar.isSelected();
            Livro livroNovo = new Livro(titulo, genero, anoFormatado, reviews, autor, editor, isbn, exemplar);
            manager.adicionarMidia(livroNovo);

        } else if (tabAtiva.getId().equals("tabAddFilme")) {
            if (caixaElencoFilme.getText().isEmpty() || caixaTituloOriginalFilme.getText().isEmpty() ||
                    caixaOndeViuFilme.getText().isEmpty() || caixaDuracaoFilme.getText().isEmpty() ||
                    caixaDirecaoFilme.getText().isEmpty() || caixaRoteiristaFilme.getText().isEmpty()) {

                labelErroCampoVazio.setVisible(true);
                return;
            }
            String elenco = caixaElencoFilme.getText();
            String tituloOriginal = caixaTituloOriginalFilme.getText();
            String ondeViuFilme = caixaOndeViuFilme.getText();
            String duracao = caixaDuracaoFilme.getText();
            String direcao = caixaDirecaoFilme.getText();
            String roteirista = caixaRoteiristaFilme.getText();
            Filme filmeNovo = new Filme(titulo, genero, anoFormatado, reviews, elenco, tituloOriginal, ondeViuFilme, duracao, direcao, roteirista);
            manager.adicionarMidia(filmeNovo);

        } else if (tabAtiva.getId().equals("tabAddSerie")) {
            if (caixaElencoSerie.getText().isEmpty() || caixaTituloOriginalSerie.getText().isEmpty() ||
                    caixaOndeViuSerie.getText().isEmpty() || dateAnoFinal.getValue() == null) {

                labelErroCampoVazio.setVisible(true);
                return;
            }
            String elenco = caixaElencoSerie.getText();
            String tituloOriginal = caixaTituloOriginalSerie.getText();
            String ondeViuSerie = caixaOndeViuSerie.getText();
            LocalDate anoSerie = dateAnoFinal.getValue();
            String anoSerieFormatado = anoSerie.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Serie serieNova = new Serie(titulo, genero, anoFormatado, reviews, elenco, tituloOriginal, ondeViuSerie, anoSerieFormatado);
            manager.adicionarMidia(serieNova);
        }

        manager.salvarMidia();
        System.out.println("Mídia adicionada com sucesso!");

        // Limpa todos os campos do formulário
        caixaTituloMidia.clear();
        caixaGeneroMidia.clear();
        datePickerAnoMidia.setValue(null);
        caixaAutor.clear();
        caixaEditor.clear();
        caixaIsbn.clear();
        checkExemplar.setSelected(false);
        caixaElencoFilme.clear();
        caixaTituloOriginalFilme.clear();
        caixaOndeViuFilme.clear();
        caixaDuracaoFilme.clear();
        caixaDirecaoFilme.clear();
        caixaRoteiristaFilme.clear();
        caixaElencoSerie.clear();
        caixaTituloOriginalSerie.clear();
        caixaOndeViuSerie.clear();
        dateAnoFinal.setValue(null);

        // Volta para a tela principal
        mudarTelaParaPrincipal(e);
    }

    /**
     * Adiciona uma nova temporada à série que foi passada pela tela principal.
     * Essa função só é funcional quando a tela está no modo "Adicionar Temporada".
     * Valida os campos, cria a temporada, a adiciona na série alvo e retorna à tela principal.
     * @param e O evento de ação do clique do botão "Adicionar Temporada".
     * @throws IOException Se ocorrer um erro ao navegar de volta para a tela principal.
     */
    public void adicionarTemporada(ActionEvent e) throws IOException {
        // Verifica se a serieAlvo foi definida
        if (this.serieAlvo == null) {
            System.out.println("Erro: Nenhuma série de alvo definida para adicionar a temporada.");
            return;
        }

        try {
            String numero = caixaTituloTemporada.getText();
            LocalDate anoTemp = dateAnoTemporada.getValue();
            String anoTempFormatado = anoTemp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String qtdEp = caixaQtdEpTemporada.getText();

            // Verifica se os campos não estão vazios
            if(numero.isEmpty() || anoTempFormatado.isEmpty() || qtdEp.isEmpty()){
                labelErroCampoVazio.setVisible(true);
                return;
            }

            Temporada temporadaNova = new Temporada(numero, anoTempFormatado, qtdEp);

            // Chama a função do manager com a série alvo e a nova temporada
            manager.adicionarTemporada(this.serieAlvo, temporadaNova);
            manager.salvarMidia();

            System.out.println("Temporada " + numero + " adicionada com sucesso à série " + serieAlvo.getTitulo());

            // Volta para a tela principal para ver o resultado
            paneAddTemporada.setVisible(false);
            mudarTelaParaPrincipal(e);

        } catch (NullPointerException | IOException | ClassNotFoundException erro) {
            labelErroCampoVazio.setVisible(true);
            System.out.println("Erro ao adicionar temporada: " + erro.getMessage());
        }
    }
}
