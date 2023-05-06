package ufpelpoo.combate_poo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AleatorioController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btVoltar;

    @FXML
    private Button btDebug;

    @FXML
    private Button btJogar;


    @FXML
    private GridPane grTabuleiro;

    private InputStream stream;
    private static Image image;
    private int debug; //0= visivel, 1 invisivel
    private int matTabuleiro[][];
    static Tabuleiro tabuleiro;


    public void initialize() throws FileNotFoundException {
        int i, j;
        debug = 0;

        // cria tabuleiro já no tabuleiro controller
        tabuleiro = new Tabuleiro();

        tabuleiro.criaTabuleiroAleatorio();
        tabuleiro.imprimirTabuleiro();

        for (i = 0; i < 2; i++) { //posiciona personagens azuis (jogador)
            for (j = 0; j < 5; j++) {
                tabuleiro.addImagem(grTabuleiro, i, j, "azul", tabuleiro.getNivelCoordenada(i, j));
            }
        }

        for (i = 0; i < 5; i++) { // inserindo o lago
            if (tabuleiro.getNivelCoordenada(2, i)==-1) {
                tabuleiro.addImagem(grTabuleiro, 2, i, "lago", -1);
            }
        }

        for (i = 3; i < 5; i++) { //posiciona personagens vermelhos (computador)
            for (j = 0; j < 5; j++) {
                tabuleiro.addImagem(grTabuleiro, i, j, "vermelhoCensurado", tabuleiro.getNivelCoordenada(i, j));

            }
        }
    }

    @FXML
    void onVoltarButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("InicioScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 540);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onDebugButtonClick(ActionEvent event) throws FileNotFoundException {
        int i, j;

        if(debug ==0){
            for (i = 3; i < 5; i++) { //mostra personagens vermelhos (computador)
                for (j = 0; j < 5; j++) {
                    grTabuleiro.getChildren().removeAll(tabuleiro.getNodeByRowColumnIndex(grTabuleiro, i, j));
                    tabuleiro.addImagem(grTabuleiro, i, j, "vermelho", tabuleiro.getNivelCoordenada(i, j));
                }
            }
            debug=1;
        }else{
            for (i = 3; i < 5; i++) { //mostra personagens vermelhos (computador)
                for (j = 0; j < 5; j++) {
                    grTabuleiro.getChildren().removeAll(tabuleiro.getNodeByRowColumnIndex(grTabuleiro, i, j));
                    tabuleiro.addImagem(grTabuleiro, i, j, "vermelhoCensurado", tabuleiro.getNivelCoordenada(i, j));
                }
            }
            debug=0;
        }

    }

    @FXML
    void onJogarButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("JogoScene.fxml"));
            Stage stage = new Stage();
            stage.setTitle("COMBATE");
            stage.setScene(new Scene(fxmlLoader.load(), 960, 540));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide(); //Esconde stage anterior
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


