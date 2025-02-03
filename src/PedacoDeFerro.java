public class PedacoDeFerro extends Item {
    public PedacoDeFerro(){
        super("Pedaço de Ferro (Arma)", "Bônus de dano passivo +2", 2);
    }

    @Override
    public void adicionarItem(Personagens personagem, Item item){
        personagem.setBonusFerro(personagem.getBonusFerro() + 2);
        personagem.getInventario().add(item);
    }

    public void usarItem(Personagens personagem){
        System.out.println("O pedaco de ferro é um efeito passivo!");
    }

}
