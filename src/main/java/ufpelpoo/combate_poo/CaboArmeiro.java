package ufpelpoo.combate_poo;

public class CaboArmeiro extends Personagem{
    public CaboArmeiro(String equipe){
        super(equipe, 3, 1);
    }

    @Override
    public void atacar(Personagem defensor){
        if(defensor.nivel == 11){
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
