public class PersonagemUm extends Personagens {


    public PersonagemUm(){
        super("Magnólio", 36, 15, "Um engenheiro mecatrônico que foi preso após se \n" +
                                                    "recusar a construir uma arma que seria usada \n" +
                                                    "para exterminar Vaporlópolis, uma cidade rival de Retrolópolis. \n" +
                                                    "Agora, ele vive para conseguir tirar esses governantes do poder.\n");
    }

    public String toString(){
        return "Personagem 1\n"+ "Nome: "+ nome+"\n"+ "Idade: " + idade +"\n" + "Característica(vida): "+ vida +"\n";
    }
    Sons ganharPoderEspecial = new Sons("assets//ganharPoderEspecial.wav");
    public boolean utilizarPoderEspecial(Obstaculos obstaculo) {
        if (poderEspecial) {
            System.out.println("O vapor escapa do seu braço com toda pressão e você corre em direção ao inimigo disparando um poderoso golpe!");
            Sons.intervalo(2000);
            int dano;
            int dado = Personagem.jogarDado();
            System.out.println("\n" + "Número do dado: "  + dado);
            if (dado >= 15) {
                dano = ((10 + bonusFerro) * 2);
                System.out.println("Dano: " + dano);
                Main.personagemPoderEspecial.tocarSom();
                Sons.intervalo(4000);
                Main.inimigorecebedano.tocarSom();
                Sons.intervalo(2000);
            } else {
                dano = ((5 + bonusFerro) * 2);
                System.out.println("Dano: " + dano);
                Main.personagemPoderEspecial.tocarSom();
                Sons.intervalo(4000);
                Main.inimigorecebedano.tocarSom();
                Sons.intervalo(2000);
            }
            obstaculo.setVida(obstaculo.getVida() - dano);
            if (obstaculo.getVida() <= 0) {
                obstaculo.setVida(0);
            }
            System.out.println("O "+obstaculo.getNome()+" perdeu "+dano+" de vida!");
            Sons.intervalo(2000);
            poderEspecial = false;
            return true;
        }
        if (obstaculo.getVida()<=0){System.out.println("O "+obstaculo.getNome()+" está morto!");}
            System.out.println("Seu poder especial está desativado por 1 turno, porque você ja usou!");
            return false;

    }

    public String recuperaPoder(){
        ganharPoderEspecial.tocarSom();
        return ("Com o som metálico das engrenagens se ajustando, você prende o braço mecânico ao seu antebraço.\n" +
                "O vapor escapa com um assobio enquanto o mecanismo ganha vida, \n" +
                "que dobra sua força ao ser ativada. Os guardas avançam com suas armas em mãos, mas você não recua.\n" +
                "Cada passo seu ecoa pelo corredor, um engenheiro contra um exército, lutando por sua liberdade.");
    }

}


