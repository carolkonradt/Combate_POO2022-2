package ufpelpoo.combate_poo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class JogoAleatorioController extends AleatorioController {

    @FXML
    private GridPane grTabuleiro;

    @FXML
    private Button btDica;

    @FXML
    private Button btSair;

    private int dicaFlag; //0=não solicitou dica; 1=solicitou dica (máximo duas)
    private int contDica;
    private int gridFlag; //uso para ver se estou selecionando uma peça
                            //0 = não há peça selecionada (seleciona a peça)
                            //1 = há peça selecionada (faz a jogada)

    private int linSelecionada, colSelecionada, linJogada, colJogada;

    public void initialize() throws FileNotFoundException {
        int i, j;
        dicaFlag=0;
        contDica=0;

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

    @FXML
    void onDicaButtonClick(ActionEvent event) {
        dicaFlag=1; //próximo click no gridpane revelará a dica
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

    @FXML
    void onGridClick(MouseEvent event) throws FileNotFoundException {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if(dicaFlag==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DICA");
            if(contDica<2){
                int colIndex, numBombas;
                colIndex= tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);
                numBombas= tabuleiro.dica(colIndex);
                if(numBombas>0){
                    alert.setHeaderText("Há bombas nessa coluna!");
                }else{
                    alert.setHeaderText("Não há bombas nessa coluna!");
                }
                dicaFlag=0;
                contDica++;
            }else{
                alert.setHeaderText("Você não possui mais dicas!");
            }
            alert.showAndWait();
        }else{

            if(gridFlag==0){ //seleciona a linha e coluna do personagem que fará a jogada
                linSelecionada= tabuleiro.getRowByClickedNode(grTabuleiro, clickedNode);
                colSelecionada= tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);
                gridFlag=1;
            }
            else{
                linJogada= tabuleiro.getRowByClickedNode(grTabuleiro, clickedNode);
                colJogada= tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);
                boolean jogou = tabuleiro.jogada(linSelecionada, colSelecionada, linJogada, colJogada);
                tabuleiro.imprimirTabuleiro();

                if(jogou){
                    System.out.println("\n");
                    tabuleiro.imprimirTabuleiro();
                    tabuleiro.refreshTabuleiro(grTabuleiro);
                }
                gridFlag=0;
                System.out.println(linJogada+" " +colJogada);
            }
        }

    }
    //tabuleiro.imprimirTabuleiro();

}
