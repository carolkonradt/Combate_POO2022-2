package ufpelpoo.combate_poo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;


public class JogoManualController extends ManualController {

    @FXML
    private Button btDica;

    @FXML
    private Button btSair;

    @FXML
    private GridPane grTabuleiro;

    @FXML
    void onDicaButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja sair?");
        alert.setContentText("Ao sair, você perderá seu progresso.");
    }

    @FXML
    void onSairButtonClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja sair?");
        alert.setContentText("Ao sair, você perderá seu progresso.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            alert.close();
        }
    }

    public void initialize() throws FileNotFoundException {
        int i, j;

        for(i=0; i<2; i++) { //posiciona personagens azuis (jogador)
            for (j = 0; j < 5; j++) {
                tabuleiro.addImagem(grTabuleiro, i, j, "azul", tabuleiro.getNivelCoordenada(i, j));
            }
        }
        for(i=0; i<5; i++){ // inserindo o lago
            if(tabuleiro.getNivelCoordenada(2, i)==-1){
                tabuleiro.addImagem(grTabuleiro, 2, i, "lago", -1);
            }
        }

        for(i=3; i<5; i++){ //posiciona personagens vermelhos (computador)
            for(j=0; j<5; j++){
                tabuleiro.addImagem(grTabuleiro, i, j, "vermelhoCensurado", tabuleiro.getNivelCoordenada(i, j));
            }
        }
    }
}