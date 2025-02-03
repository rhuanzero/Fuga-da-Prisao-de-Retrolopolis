public class EnfrentarDiretorEFugir extends Obstaculos{


    public EnfrentarDiretorEFugir(String nome){
        this.vida = 25;
        setNome(nome);
    }

    public void interageComPersonagem(Personagens personagem, int bonus) {

            if (!personagem.getPao()) {
                int dano;
                int dado = Personagem.jogarDado();
                System.out.println("Número do dado: " + dado);
                if (dado == 20) {
                    dano = ((super.getDanobase() * 3) + bonus);
                    System.out.println("O " + nome + " deu " + dano + " de dano");
                } else if (dado >= 15) {
                    dano = super.getDanobase() + bonus;
                    System.out.println("O " + nome + " deu " + dano + " de dano");
                } else if (dado > 5) {
                    dano = super.getDanobase();
                    System.out.println("O " + nome + " deu " + dano + " de dano");
                } else {
                    dano = 1;
                    System.out.println("O " + nome + " deu " + dano + " de dano");
                }
                if(personagem.poderEspecialAtivado){
                    dano = dano/2;
                    if(dano > 0) {
                        vida = vida - dano;
                        System.out.println("O " + nome + " recebeu " + dano + " de dano\n");
                    }
                }
                System.out.println(personagem.getNome() + " recebe " + dano + " de dano!");
                personagem.setVida(personagem.getVida() - dano);
                Main.ataque.tocarSom();
                Sons.intervalo(300);
            } else {
                System.out.println("Você está imune ao ataque\n");
            }
    }


    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}
