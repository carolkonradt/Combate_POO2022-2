package ufpelpoo.combate_poo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class FimAleatorioSceneController extends JogoAleatorioController {

    @FXML
    private Button btNovoJogo;

    @FXML
    private Button btReiniciar;

    @FXML
    private Button btSair;

    @FXML
    private Label lbNovamente;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private DialogPane dPane;
    @FXML
    private GridPane grBox;

    public void initialize(){
        Label label = null;

        if(JogoAleatorioController.getGanhouAzul()==true){
            label= new Label("Parabéns, você ganhou! :)");

        }else{
            if (JogoAleatorioController.getGanhouVermelho()==true)
                label = new Label("Que pena, você perdeu! :(");
        }
        label.setFont(new Font("Consolas", 20));
        grBox.add(label, 0, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.CENTER);
    }

    @FXML
    void onNovoJogoClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("InicioScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("COMBATE");
        stage.setScene(new Scene(fxmlLoader.load(), 960, 540));
        stage.setResizable(false);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide(); //Esconde stage anterior
    }

    @FXML
    void onReiniciarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AleatorioScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("COMBATE");
        stage.setScene(new Scene(fxmlLoader.load(), 960, 540));
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide(); //Esconde a scene anterior
    }

    @FXML
    void onSairClick(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}