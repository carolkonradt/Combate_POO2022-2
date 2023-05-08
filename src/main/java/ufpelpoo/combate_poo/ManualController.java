package ufpelpoo.combate_poo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.stage.Stage;

public class ManualController {

    int codImage=99; //bandeira = 0, bomba = 11, Espião = 1, Soldado = 2, Cabo = 3*, Marechal = 10, nenhum = 99
    //uso para identificar qual imagem foi clicada por último
    int matTabuleiro[][];
    int i, j, bomba = 0, bandeira = 0, soldado = 0, cabo = 0, espiao = 0, marechal = 0;
    int indicePersonagem;
    int[] posicoesValidas = {0, 0, 0, 0};
    InputStream stream;
    Image image;
    private Stage stage;
    private Scene scene;
    private Parent root;
    static Tabuleiro tabuleiro;
    int debug;



    @FXML
    private Button btDebug;

    @FXML
    private Button btJogar;

    @FXML
    private Button btVoltar;

    @FXML
    private GridPane grTabuleiro;

    @FXML
    private ImageView imgBandeira;

    @FXML
    private ImageView imgBomba;

    @FXML
    private ImageView imgCaboArmeiro;

    @FXML
    private ImageView imgEspiao;

    @FXML
    private ImageView imgMarechal;

    @FXML
    private ImageView imgSoldado;

    @FXML
    private ImageView img04;

    public void initialize() throws FileNotFoundException {
        debug=0;
        tabuleiro = new Tabuleiro();
        tabuleiro.posicionarAleatorio("vermelho");

        for (i = 3; i < 5; i++) { //posiciona personagens vermelhos (computador)
            for (j = 0; j < 5; j++) {
               tabuleiro.addImagem(grTabuleiro, i, j, "vermelhoCensurado", tabuleiro.getNivelCoordenada(i, j));
            }
        }
        for (j = 0; j < 5; j++) {
            if(tabuleiro.getNivelCoordenada(2, j)==-1)
                tabuleiro.addImagem(grTabuleiro, 2, j, "lago", tabuleiro.getNivelCoordenada(2, j));
        }
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
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("JogoManualScene.fxml"));
            Stage stage = new Stage();
            stage.setTitle("COMBATE");
            stage.setScene(new Scene(fxmlLoader.load(), 960, 540));
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Esconde a scene anterior
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onVoltarButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("InicioScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 540);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onBandeiraClick(MouseEvent event) {
        codImage = 0;
    }

    @FXML
    void onBombaClick(MouseEvent event) {
        codImage = 11;
    }


    @FXML
    void onCaboArmeiroClick(MouseEvent event) {
        codImage = 3;

    }


    @FXML
    void onEspiaoClick(MouseEvent event) {
        codImage = 1;
    }


    @FXML
    void onMarechalClick(MouseEvent event) {
        codImage = 10;
    }

    @FXML
    void onSoldadoClick(MouseEvent event) {
        codImage = 2;
    }


    @FXML
    void onGridClick(MouseEvent event) throws IOException {
        int colIndex, linIndex;
        Node clickedNode = event.getPickResult().getIntersectedNode();
        /*try {
            colIndex = GridPane.getColumnIndex(clickedNode);//pega a linha
        } catch (NullPointerException e) {
            colIndex = 0;
        }*/
        colIndex= tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);

        /*try {
            linIndex = GridPane.getRowIndex(clickedNode);//pega a coluna
        } catch (NullPointerException e) {
            linIndex = 0;
        }*/
        linIndex= tabuleiro.getRowByClickedNode(grTabuleiro, clickedNode);
        //grTabuleiro.getChildren().removeAll(clickedNode);
        setImageTabuleiro(grTabuleiro, linIndex, colIndex, codImage);

    }

    private void setImageTabuleiro (GridPane grTabuleiro, int linIndex, int colIndex, int codImage) throws FileNotFoundException {
        if (codImage != 99) {
            if (codImage == 0) {
                if(linIndex==0){
                    if (bandeira < 1) {
                        indicePersonagem = 0;
                        if (tabuleiro.posicionar(linIndex, colIndex, "azul", codImage) == true) {
                            tabuleiro.posicionar(linIndex, colIndex, "azul", codImage);
                            tabuleiro.addImagem(grTabuleiro, linIndex, colIndex, "azul", codImage);
                        }
                        bandeira++;
                    }
                }

            }

            if (codImage == 11) {
                if (bomba < 2) {
                    if (bomba == 0) {
                        indicePersonagem = 1;
                    } else {
                        indicePersonagem = 2;
                    }

                    if (tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem) == true) {
                        tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem);
                        tabuleiro.addImagem(grTabuleiro, linIndex, colIndex, "azul", codImage);
                    }
                }
                bomba++;
            }

            if (codImage == 1) {
                if (espiao < 1) {
                    indicePersonagem = 3;

                    if (tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem) == true) {
                        tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem);
                        tabuleiro.addImagem(grTabuleiro, linIndex, colIndex, "azul", codImage);
                    }
                }
                espiao++;
            }

            if (codImage == 2) {
                if (soldado < 3) {
                    if(soldado==0){
                        indicePersonagem=4;
                    }else{
                        if(soldado==1){
                            indicePersonagem=5;
                        }else{
                            indicePersonagem=6;
                        }
                    }
                    if (tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem) == true) {
                        tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem);
                        tabuleiro.addImagem(grTabuleiro, linIndex, colIndex, "azul", codImage);
                    }
                }
                soldado++;
            }

            if (codImage == 3) {
                if (cabo < 2) {
                    if(cabo==0){
                        indicePersonagem=7;
                    }else{
                        indicePersonagem=8;
                    }

                    if (tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem) == true) {
                        tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem);
                        tabuleiro.addImagem(grTabuleiro, linIndex, colIndex, "azul", codImage);
                    }
                }
                cabo++;
            }
            if (codImage == 10) {
                if (marechal < 1) {
                    indicePersonagem=9;

                    if (tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem) == true) {
                        tabuleiro.posicionar(linIndex, colIndex, "azul", indicePersonagem);
                        tabuleiro.addImagem(grTabuleiro, linIndex, colIndex, "azul", codImage);
                    }
                }
                marechal++;
            }
        }
        //tabuleiro.imprimirTabuleiro();
    }


}


