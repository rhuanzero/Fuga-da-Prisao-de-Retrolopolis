import com.sun.source.tree.InstanceOfTree;

import java.util.ArrayList;

public class Pao extends Item{

    public Pao(){
        super("Pao (Consumível)", "Te deixa imune ao proximo ataque!", 0);
    }
    @Override
    public void adicionarItem(Personagens personagem, Item item){
        personagem.getInventario().add(item);
    }
    Sons comerpao = new Sons("assets/comerpao.wav");

    public void usarItem(Personagens personagem){
        super.usarItem(personagem);
        comerpao.tocarSom();
        personagem.setPao(true);
    }

    public Sons getComerpao() {
        return comerpao;
    }

    public void setComerpao(Sons comerpao) {
        this.comerpao = comerpao;
    }

    @Override
    public String toString() {
        return super.toString();
    }



}
