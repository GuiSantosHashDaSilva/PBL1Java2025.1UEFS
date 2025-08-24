package view;

import controller.MediaManager;
import controller.TelaPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;

public class TelaPrincipalApplication extends Application {
    private static final MediaManager manager = new MediaManager();


    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {

        try{
            manager.carregarMidia();
            manager.recalcularTodasAvaliacoes();
            System.out.println("Arquivo de midia carregado com sucesso\n");
        } catch (IOException e){
            System.out.println("Arquivo de midia não encontrado, iniciando com novo arquivo\n");
            manager.salvarMidia();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(TelaPrincipalApplication.class.getResource("/ViewFx/TelaPrincipal.fxml"));

        fxmlLoader.setControllerFactory(controllerClass -> new TelaPrincipalController(manager));


        Scene scene = new Scene(fxmlLoader.load(), 950, 645);
        Image icon = new Image("file:src/iconediario.png");
        stage.getIcons().add(icon);
        stage.setTitle("Diário Cultural");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
