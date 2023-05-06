package ufpelpoo.combate_poo;
public class Espiao extends Personagem{
    public Espiao(String equipe){
        super(equipe, 1, 1);
    }

    @Override
    public void atacar(Personagem defensor){
        if(defensor.nivel == 10){
            defensor.setVivo(false);
        }else{
            if(this.nivel > defensor.nivel){
                defensor.setVivo(false);
            }else if(this.nivel < defensor.nivel){
                this.setVivo(false);
            }else{
                defensor.setVivo(false);
                this.setVivo(false);
            }
        }
    }
}