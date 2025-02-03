public abstract class Obstaculos implements Obstaculo {
    protected int vida;
    protected int danobase = 5;
    protected String nome;

    public Obstaculos(){
        vida = 10;
    }

    public abstract void interageComPersonagem(Personagens personagem, int bonus);

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getDanobase() {
        return danobase;
    }

    public void setDanobase(int danobase) {
        this.danobase = danobase;
    }

    public String toString(){
        return "\n" + nome + "\nVida: " + getVida();
    }
}