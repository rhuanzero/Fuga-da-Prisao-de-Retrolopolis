public class Tonico extends Item{
    private int dano = 5;
    public Tonico(){
        super("Bebida desconhecida (Consumivel)", "(?????)", 10);
    }

    public void adicionarItem(Personagens personagem, Item item){
        personagem.getInventario().add(item);
    }

    public void usarItem(Personagens personagem){
        super.usarItem(personagem);
        personagem.setDanoTonico(dano);
    }




}
