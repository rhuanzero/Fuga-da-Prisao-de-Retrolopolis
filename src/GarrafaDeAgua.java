public class GarrafaDeAgua extends Item {
    public GarrafaDeAgua() {
        super("Garrafa de água (Consumível)","Recupera +4 de vida ao consumir", 4);
    }
    Sons beberagua = new Sons("assets/beberagua.wav");

    public void adicionarItem(Personagens personagem, Item item){
        personagem.getInventario().add(item);
    }

    public void usarItem(Personagens personagem){
        super.usarItem(personagem);
        beberagua.tocarSom();
        personagem.setVida(personagem.getVida()+4);
        //personagem.getInventario().removeIf(Item -> Item instanceof GarrafaDeAgua); // Fonte de Dica do removeIf: chatgpt
    }

}
