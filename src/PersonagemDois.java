public class PersonagemDois extends Personagens {

    public PersonagemDois(){
        super("Eve", 27,15 ,"Uma engenheira mecatrônica renomada, acompanhada de sua armadura protetora, Clank, está cansada da corrupção em Retrolópolis \n" +
                        "e do descaso com a população. Determinada a revelar as verdades sobre o governo da cidade, ela decide agir. \n" +
                        "No entanto, as autoridades descobrem seu plano por meio de um informante, e ela acaba sendo presa. \n" +
                        "Agora, ela pretende fugir da prisão e espalhar a verdade sobre Retrolópolis.\n"
        );
    }

    public String toString(){
        return "Personagem 2\n"+ "Nome: "+ nome+"\n"+ "Idade: " + idade +"\n" + "Característica(vida): "+ vida +"\n";
    }


    public boolean utilizarPoderEspecial(Obstaculos obstaculo) {
        if(poderEspecial) {
            poderEspecialAtivado = true;
            System.out.println("O modo defensivo foi ativado! Um campo energético se intensifica e cria vida ao seu redor!");
            System.out.println("Todo dano é diminuido pela metade e o inimigo recebe uma pequena fração de dano ao atacar!");
            Main.personagemPoderEspecial.tocarSom();
            Sons.intervalo(3500);
            poderEspecial = false;
            return true;
        }
        System.out.println("Seu poder especial está desativado por 1 turno, porque você ja usou!");
        return false;
    }

    public String recuperaPoder(){
        return ("Você reencontra Clank! sua armadura com uma personalidade um pouco duvidosa, podendo agora ativar o modo defensivo. O campo energético ao seu redor se intensifica,\n" +
                "dobrando sua resistência. Os guardas se aproximam, confiantes em sua superioridade numérica, mas eles não conhecem sua determinação.\n" +
                "Cada movimento seu é calculado, e cada passo é uma declaração de que você não será detida.");
    }
}
