package ufpelpoo.combate_poo;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;

public class Tabuleiro{

    private static Personagem tabuleiro[][] = new Personagem[5][5];
    private Personagem equipeAzul[];
    private Personagem equipeVermelho[];

    public Tabuleiro(){
        Random aleatorio =  new Random();
        int posicaoLago = aleatorio.nextInt(5);
        Personagem lago = new Personagem("lago", -1, 0);

        //tabuleiro = new Personagem[5][5];

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                tabuleiro[i][j] = null;
            }
        }
        tabuleiro[2][posicaoLago] = lago;

        equipeAzul = criarEquipe("azul");
        equipeVermelho = criarEquipe("vermelho");
    }


    private Personagem[] criarEquipe(String equipe){
        Personagem[] time = new Personagem[10];

        Bandeira bandeira = new Bandeira(equipe);
        Bomba bomba1 = new Bomba(equipe);
        Bomba bomba2 = new Bomba(equipe);
        Espiao espiao = new Espiao(equipe);
        Soldado soldado1 = new Soldado(equipe);
        Soldado soldado2 = new Soldado(equipe);
        Soldado soldado3 = new Soldado(equipe);
        CaboArmeiro cabo1 = new CaboArmeiro(equipe);
        CaboArmeiro cabo2 = new CaboArmeiro(equipe);
        Marechal marechal = new Marechal(equipe);

        time[0] = bandeira;
        time[1] = bomba1;
        time[2] = bomba2;
        time[3] = espiao;
        time[4] = soldado1;
        time[5] = soldado2;
        time[6] = soldado3;
        time[7] = cabo1;
        time[8] = cabo2;
        time[9] = marechal;

        return time;
    }

    public void posicionarAleatorio(String equipe){
        Random aleatorio = new Random();
        int linha, coluna, base;

        coluna = aleatorio.nextInt(5);
        if(equipe.equalsIgnoreCase("azul")){
            base = 0;
            posicionar(0, coluna, equipe, 0);
        }else{
            base = 3;
            posicionar(4, coluna, equipe, 0);
        }

        for(int i = 1; i < 10; i++){
            linha = aleatorio.nextInt(2) + base;
            coluna = aleatorio.nextInt(5);
            while(!posicionar(linha, coluna, equipe, i)){
                coluna = (coluna + 1)%5;
                if(coluna == 0){
                    if(linha == base){
                        linha++;
                    }else{
                        linha--;
                    }
                }
            }
        }
    }

    public String getEquipe(Personagem a){
        int i;
        String equipe = null;

        for(i=0;i<10;i++) {
            if (a == null) {
                equipe ="null";
            } else {
                if (equipeAzul[i] == a) {
                    equipe = "azul";
                    break;
                } else {
                    if (equipeVermelho[i] == a) {
                        equipe = "vermelho";
                        break;
                    }
                }
            }
        }
        return equipe;
    }

    public String getEquipeByRowColumn(int row, int column){
        return getEquipe(tabuleiro[row][column]);
    }

    public boolean posicionar(int linha, int coluna, String equipe, int indicePersonagem){
        if(equipe.equalsIgnoreCase("azul")){
            if(linha >= 0 && linha < 2 && coluna >= 0 && coluna < 5){
                if(tabuleiro[linha][coluna] == null){
                    tabuleiro[linha][coluna] = equipeAzul[indicePersonagem];
                    equipeAzul[indicePersonagem].setVivo(true);
                    equipeAzul[indicePersonagem].setVisibilidade(true);
                    return true;
                }
            }
        }else{
            if(equipe.equalsIgnoreCase("vermelho")){
                if(tabuleiro[linha][coluna] == null){
                    tabuleiro[linha][coluna] = equipeVermelho[indicePersonagem];
                    equipeVermelho[indicePersonagem].setVivo(true);
                    equipeVermelho[indicePersonagem].setVisibilidade(false);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean desposicionar(int linha, int coluna){
        if(tabuleiro[linha][coluna] != null){
            tabuleiro[linha][coluna].setVivo(false);
            tabuleiro[linha][coluna] = null;
            return true;
        }
        return false;
    }
    public boolean jogada(int linhaAtual, int colunaAtual, int novaLinha, int novaColuna){
        if(tabuleiro[linhaAtual][colunaAtual] != null){
            if(tabuleiro[linhaAtual][colunaAtual].isVivo()){
                if(tabuleiro[linhaAtual][colunaAtual].getMovimentacao() > 0){

                    if(validarMovimentacao(linhaAtual, colunaAtual, novaLinha, novaColuna)){
                        if(tabuleiro[novaLinha][novaColuna] != null){

                            tabuleiro[novaLinha][novaColuna].setVisibilidade(true);
                            tabuleiro[linhaAtual][colunaAtual].atacar(tabuleiro[novaLinha][novaColuna]);

                            if(!tabuleiro[novaLinha][novaColuna].isVivo()){
                                tabuleiro[novaLinha][novaColuna].setVisibilidade(false);
                                tabuleiro[novaLinha][novaColuna] = null;
                            }
                        }
                        if(tabuleiro[linhaAtual][colunaAtual].isVivo()){
                            tabuleiro[novaLinha][novaColuna] = tabuleiro[linhaAtual][colunaAtual];
                        }
                        tabuleiro[linhaAtual][colunaAtual] = null;
                        return true;
                    }

                }
            }
        }
        return false;
    }

    //Verificar se a peça possui movimento e se esta em uma posição que permite movimentar-se
    //A nova posição não pode ser igual a posição de origem
    //Considera-se que não irá receber valores que ultrapassem os limites da matriz
    private boolean validarMovimentacao(int linhaAtual, int colunaAtual, int novaLinha, int novaColuna){
        int[] movimentacoesValidas = this.verificarPosicoesValidas(linhaAtual, colunaAtual);
        if(linhaAtual == novaLinha && colunaAtual != novaColuna){

            if(novaColuna > colunaAtual){
                if(novaColuna - colunaAtual <= movimentacoesValidas[2]){
                    return true;
                }
            }else{
                if(colunaAtual - novaColuna <= movimentacoesValidas[3]){
                    return true;
                }
            }

        }else{

            if(novaColuna == colunaAtual && linhaAtual != novaLinha){

                if(novaLinha > linhaAtual){
                    if(novaLinha - linhaAtual <= movimentacoesValidas[0]){
                        return true;
                    }
                }else{
                    if(linhaAtual - novaLinha <= movimentacoesValidas[1]){
                        return true;
                    }
                }
            }

        }
        return false;
    }

    //Considera que sempre irá receber uma posição com um personagem vivo
    public int[] verificarPosicoesValidas(int linha, int coluna){
        int[] movimentacoes = {0, 0, 0, 0}; /*{cima, baixo, direita, esquerda}*/
        if(tabuleiro[linha][coluna].getMovimentacao() > 0){
            int[] controlador = {0, 0, 0, 0};

            for(int i = 1; i <= tabuleiro[linha][coluna].getMovimentacao(); i++){
                if(controlador[0] == 0 && linha+i < 5){
                    if(tabuleiro[linha+i][coluna] == null){
                        movimentacoes[0]++;
                    }else{
                        if(!tabuleiro[linha][coluna].getEquipe().equalsIgnoreCase(tabuleiro[linha+i][coluna].getEquipe()) && tabuleiro[linha+i][coluna].getNivel() != -1){
                            movimentacoes[0]++;
                        }
                        controlador[0]++;
                    }
                }
                if(controlador[1] == 0 && linha-i >= 0){
                    if(tabuleiro[linha-i][coluna] == null){
                        movimentacoes[1]++;
                    }else{
                        if(!tabuleiro[linha][coluna].getEquipe().equalsIgnoreCase(tabuleiro[linha-i][coluna].getEquipe()) && tabuleiro[linha-i][coluna].getNivel() != -1){
                            movimentacoes[1]++;
                        }
                        controlador[1]++;
                    }
                }

                if(controlador[2] == 0 && coluna+i < 5){
                    if(tabuleiro[linha][coluna+i] == null){
                        movimentacoes[2]++;
                    }else{
                        if(!tabuleiro[linha][coluna].getEquipe().equalsIgnoreCase(tabuleiro[linha][coluna+i].getEquipe()) && tabuleiro[linha][coluna+i].getNivel() != -1){
                            movimentacoes[2]++;
                        }
                        controlador[2]++;
                    }
                }

                if(controlador[3] == 0 && coluna-i >= 0){
                    if(tabuleiro[linha][coluna-i] == null){
                        movimentacoes[3]++;
                    }else{
                        if(!tabuleiro[linha][coluna].getEquipe().equalsIgnoreCase(tabuleiro[linha][coluna-i].getEquipe()) && tabuleiro[linha][coluna-i].getNivel() != -1){
                            movimentacoes[3]++;
                        }
                        controlador[3]++;
                    }
                }
            }
        }
        return movimentacoes;
    }

    public void imprimirTabuleiro(){
        for(int i = 4; i >= 0; i--){
            for(int j = 0; j < 5; j++){
                if(tabuleiro[i][j] == null){
                    System.out.printf("\tx");
                }else{
                    System.out.printf("\t%d", tabuleiro[i][j].getNivel());
                }
            }
            System.out.println(" ");
        }
    }

    public int dica(int coluna){
        int numeroBombas = 0;
        for(int i = 0; i < 5; i++){
            if(tabuleiro[i][coluna] == equipeVermelho[1] || tabuleiro[i][coluna] == equipeVermelho[2]){
                numeroBombas++;
            }
        }
        return numeroBombas;
    }

    public void criaTabuleiroAleatorio (){

        int[] posicoesValidas = {0, 0, 0, 0};
        posicionarAleatorio("azul");
        posicionarAleatorio("vermelho");

    }

    public int getNivelCoordenada(int linha, int coluna){
        try{
            return tabuleiro[linha][coluna].getNivel();
        }
        catch (NullPointerException e){ //retorna 99 para saber que são espaços vazios
            return 99;
        }
    }
    public void addImagem (GridPane grTabuleiro, int linIndex, int colIndex, String equipe, int nivelPersonagem) throws FileNotFoundException {
        String imagePath="/images/bandeira1.png"; //inicializando variavel
        ImageView imageView;

        switch(nivelPersonagem){
            case 0:
                if(equipe=="azul"){
                    imagePath="/images/bandeira1.png";
                }else{
                    if(equipe=="vermelhoCensurado"){
                        imagePath="/images/circulo.png";
                    }else{
                        imagePath="/images/bandeira.png";
                    }

                }
                break;

            case 1:
                if(equipe=="azul"){
                    imagePath="/images/espiao1.png";
                }else{
                    if(equipe=="vermelhoCensurado"){
                        imagePath="/images/circulo.png";
                    }else{
                        imagePath="/images/espiao.png";
                    }
                }
                break;

            case 3:
                if(equipe=="azul"){
                    imagePath="/images/infantaria.png";
                }else{
                    if(equipe=="vermelhoCensurado"){
                        imagePath="/images/circulo.png";
                    }else{
                        imagePath="/images/infantaria1.png";
                    }
                }
                break;
            case 2:
                if(equipe=="azul"){
                    imagePath="/images/soldado1.png";
                }else{
                    if(equipe=="vermelhoCensurado"){
                        imagePath="/images/circulo.png";
                    }else{
                        imagePath="/images/soldado.png";
                    }
                }
                break;
            case 10:
                if(equipe=="azul"){
                    imagePath="/images/chevron.png";
                }else{
                    if(equipe=="vermelhoCensurado"){
                        imagePath="/images/circulo.png";
                    }else{
                        imagePath="/images/chevron1.png";
                    }
                }
                break;
            case 11:
                if(equipe=="azul"){
                    imagePath="/images/bombear.png";
                }else{
                    if(equipe=="vermelhoCensurado"){
                        imagePath="/images/circulo.png";
                    }else{
                        imagePath="/images/bombear1.png";
                    }
                }
                break;
            case -1:
                imagePath="/images/inundar.png";
                break;
            case 99:
                imagePath="/images/fundo.png";
                break;

        }
        URL resource;
       resource = Tabuleiro.class.getResource(imagePath);

        String path = resource.toExternalForm();
        Image image = new Image(path);
        setImagem(grTabuleiro, image, linIndex, colIndex);
    }

    public void setImagem(GridPane grTabuleiro, Image image, int linIndex, int colIndex) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        grTabuleiro.add(imageView, colIndex, linIndex);//invertendo linha e coluna, pois no GridPane a ordem é [coluna][linha]
        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);
    }

    public void refreshTabuleiro(GridPane gridpane) throws FileNotFoundException {
        int i, j, nivel;
        ImageView imageview;
        for(i=0; i<5; i++) { //posiciona personagens azuis (jogador)
            for (j = 0; j < 5; j++) {
                if(tabuleiro[i][j]!=null) {
                    gridpane.getChildren().removeAll(getNodeByRowColumnIndex(gridpane,i, j));
                    nivel = tabuleiro[i][j].getNivel();
                    if (getEquipe(tabuleiro[i][j]) == "azul") {
                            addImagem(gridpane, i, j, "azul", nivel);
                    } else {
                        if (getEquipe(tabuleiro[i][j]) == "vermelho") {
                                if (tabuleiro[i][j].getVisibilidade()) {
                                    addImagem(gridpane, i, j, "vermelho", nivel);
                                } else {
                                    addImagem(gridpane, i, j, "vermelhoCensurado", nivel);
                                }

                        } else {
                            if (tabuleiro[i][j].getNivel() == -1) {
                                addImagem(gridpane, i, j, "lago", -1);
                            }
                        }
                    }
                }
                if(tabuleiro[i][j]==null){
                    gridpane.getChildren().removeAll(getNodeByRowColumnIndex(gridpane,i, j));
                    addImagem(gridpane, i, j, "vazio", 99);
                }
            }
        }
        gridpane.setGridLinesVisible(true);
    }

    public Node getNodeByRowColumnIndex(GridPane grTabuleiro, final int row, final int column) {
        int linIndex, colIndex;
        Node result = null;
        ObservableList<Node> childrens = grTabuleiro.getChildren();

        for(Node node : childrens) {
            try {
                colIndex = grTabuleiro.getColumnIndex(node);//pega a linha
            } catch (NullPointerException e) {
                colIndex = 0;
            }

            try {
                linIndex = grTabuleiro.getRowIndex(node);//pega a coluna
            } catch (NullPointerException e) {
                linIndex = 0;
            }
            if(linIndex == row && colIndex == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    public int getColumnByClickedNode(GridPane gridpane, Node clickedNode){
        int col;
        try {
            col = gridpane.getColumnIndex(clickedNode);//pega a coluna
        } catch (NullPointerException e) {
            col = 0;
        }
        return col;
    }
    public int getRowByClickedNode(GridPane gridpane, Node clickedNode){
        int lin;
        try {
            lin = gridpane.getRowIndex(clickedNode);//pega a linha
        } catch (NullPointerException e) {
            lin = 0;
        }

        return lin;
    }

    public void jogaAdversario(){
        Random random= new Random();
        int linhaAtual, colunaAtual, novaLinha, novaColuna;
        boolean jogou =false;
        do{
            linhaAtual= random.nextInt(5);
            colunaAtual= random.nextInt(5);
            novaLinha=  random.nextInt(5);
            novaColuna= random.nextInt(5);

            if(getEquipe(tabuleiro[linhaAtual][colunaAtual])=="vermelho" && getEquipe(tabuleiro[novaLinha][novaColuna])!="vermelho") {
                jogou = jogada(linhaAtual, colunaAtual, novaLinha, novaColuna);
            }
        }while (!jogou);

    }

    public boolean ganhou(String equipe){
        int pecasComMovimento = 0;
        if(equipe.equalsIgnoreCase("azul")){
            if(!equipeVermelho[0].isVivo()){
                return true;
            }
            for(int i = 3; i < 10; i++){
                if(equipeVermelho[i].isVivo()){
                    pecasComMovimento++;
                }
            }
            if(pecasComMovimento == 0){
                return true;
            }
        }else{
            if(equipe.equalsIgnoreCase("vermelho")){
                if(!equipeAzul[0].isVivo()){
                    return true;
                }
                for(int i = 3; i < 10; i++){
                    if(equipeAzul[i].isVivo()){
                        pecasComMovimento++;
                    }
                }
                if(pecasComMovimento == 0){
                    return true;
                }
            }
        }

        return false;
    }

}
