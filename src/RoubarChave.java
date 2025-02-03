public class RoubarChave extends Obstaculos{
    private int vida = 0;

    public void interageComPersonagem(Personagens personagem, int bonus){
        personagem.setVida(personagem.getVida() - (super.getDanobase() + bonus));
        Main.ataque.tocarSom();
        Sons.intervalo(200);
        };


    public void prisaoObstaculo(){

    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}
