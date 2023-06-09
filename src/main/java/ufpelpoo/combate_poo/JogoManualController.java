package ufpelpoo.combate_poo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;


public class JogoManualController extends ManualController {

    @FXML
    private GridPane grTabuleiro;

    @FXML
    private Button btDica;

    @FXML
    private Button btSair;
    private static boolean ganhouVermelho;
    private static boolean ganhouAzul;

    private int dicaFlag; //0=não solicitou dica; 1=solicitou dica (máximo duas)
    private int contDica;
    private int gridFlag; //uso para ver se estou selecionando uma peça
    //0 = não há peça selecionada (seleciona a peça)
    //1 = há peça selecionada (faz a jogada)

    private int linSelecionada, colSelecionada, linJogada, colJogada;

    public boolean getGanhouVermelho(){
        return ganhouVermelho;
    }
    public static boolean getGanhouAzul(){
        return ganhouAzul;
    }
    public void setGanhouVermelho(boolean ganhouVermelho){
        this.ganhouVermelho=ganhouVermelho;
    }

    public void setGanhouAzul(boolean ganhouAzul){
        this.ganhouAzul=ganhouAzul;
    }

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
    void onGridClick(MouseEvent event) throws IOException {
        Node clickedNode = event.getPickResult().getIntersectedNode();


        if (dicaFlag == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DICA");
            if (contDica < 2) {
                int colIndex, numBombas;
                colIndex = tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);
                numBombas = tabuleiro.dica(colIndex);
                if (numBombas > 0) {
                    alert.setHeaderText("Há bombas nessa coluna!");
                } else {
                    alert.setHeaderText("Não há bombas nessa coluna!");
                }
                dicaFlag = 0;
                contDica++;
            } else {
                alert.setHeaderText("Você não possui mais dicas!");
            }
            alert.showAndWait();
        } else {

            if (gridFlag == 0) { //seleciona a linha e coluna do personagem que fará a jogada
                linSelecionada = tabuleiro.getRowByClickedNode(grTabuleiro, clickedNode);
                colSelecionada = tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);
                if(tabuleiro.getEquipeByRowColumn(linSelecionada, colSelecionada)=="azul")
                    gridFlag = 1;



            } else {
                linJogada = tabuleiro.getRowByClickedNode(grTabuleiro, clickedNode);
                colJogada = tabuleiro.getColumnByClickedNode(grTabuleiro, clickedNode);
                boolean jogou = tabuleiro.jogada(linSelecionada, colSelecionada, linJogada, colJogada);

                if (jogou == true) {
                    //tabuleiro.imprimirTabuleiro();
                    tabuleiro.refreshTabuleiro(grTabuleiro);
                    setGanhouAzul(tabuleiro.ganhou(("azul")));
                    if (ganhouAzul == false) {
                        tabuleiro.jogaAdversario();
                        tabuleiro.refreshTabuleiro(grTabuleiro);
                        setGanhouVermelho(tabuleiro.ganhou(("vermelho")));
                    }
                }
                gridFlag = 0;
                grTabuleiro.setGridLinesVisible(true);

                if (ganhouAzul == true) {
                    terminaJogo("azul", event);
                } else {
                    if (ganhouVermelho == true) {
                        terminaJogo("vermelho", event);
                    }
                }
            }
        }

    }
    public void terminaJogo(String equipe, MouseEvent event) throws IOException {
        if(equipe=="azul")
            setGanhouAzul(true);
        else
            setGanhouVermelho(true);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FimManualScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("COMBATE");
        stage.setScene(new Scene(fxmlLoader.load(), 480, 270));
        stage.setResizable(false);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide(); //Esconde stage anterior
}

}