package ufpelpoo.combate_poo;
public class Personagem {
    protected String equipe;
    protected int nivel; /*bandeira = 0, bomba = 11, Espião = 1, Soldado = 2, Cabo = 3*, Marechal = 10*/
    protected int movimentacao;
    protected boolean visibilidade;
    protected boolean vivo;

    public Personagem() {

    }

    public Personagem(String equipe, int nivel, int movimentacao) {
        this.equipe = equipe;
        this.nivel = nivel;
        this.movimentacao = movimentacao;
        this.visibilidade = false;
        this.vivo = false;
    }

    public void atacar(Personagem defensor){
        if(this.nivel > defensor.nivel){
            defensor.setVivo(false);
        }else if(this.nivel < defensor.nivel){
            this.setVivo(false);
        }else{
            defensor.setVivo(false);
            this.setVivo(false);
        }
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public String getEquipe() {
        return equipe;
    }

    public int getNivel() {
        return nivel;
    }

    public int getMovimentacao(){
        return movimentacao;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVisibilidade(boolean visibilidade) {
        this.visibilidade = visibilidade;
    }
    public boolean getVisibilidade(){
        return this.visibilidade;
    }

}