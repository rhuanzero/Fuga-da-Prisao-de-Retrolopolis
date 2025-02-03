import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.io.IOException;

// Alguns textos foram inspirados pelo chatgpt do sout
public class Main {
    public static Sons magnolioSelecao = null;
    public static Sons eveSelecao = null;
    public static Sons personagemPoder = null;
    public static Sons personagemPoderErro = null;
    public static Sons personagemPoderEspecial = null;
    public static Sons personagemRecebeDanoEspecial = null;
    public static Sons personagemRecuperaPoder = null;
    public static Sons personagemMorte = null;
    public static Sons personagemRecebeDano = null;
    public static Sons ataque = null;
    public static Sons inimigomorre = null;
    public static Sons inimigorecebedano = null;
    public static Sons fimdejogo = new Sons("assets//fimdejogo.wav");
    // Frase final - Eve e Magnolio
    public static Sons frasefinalMag = new Sons("assets//frasefinalmageve.wav");
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("Ranking.txt", true);
        String ranking;
        Instant inicio = Instant.now();

        Scanner in = new Scanner(System.in);
        String escolha = "";
        boolean vez = true;
        int i = 0;

        //Sons
        Sons somSelecao = new Sons("assets/seleçaoSound.wav");
        somSelecao.tocarEmLoop();


        // Selecao magnolio
        int selecaoDado = Personagem.jogarDado();
        if (selecaoDado >= 15) {
            magnolioSelecao = new Sons("assets/magnolio/selecaomagnolio1.wav");
        } else if (selecaoDado >= 10) {
            magnolioSelecao = new Sons("assets/magnolio/selecaomagnolio2.wav");
        } else if (selecaoDado >= 5) {
            magnolioSelecao = new Sons("assets/magnolio/selecaomagnolio3.wav");
        } else {
            magnolioSelecao = new Sons("assets/magnolio/selecaomagnolio4.wav");
        }

        // Selecao eve
        selecaoDado = Personagem.jogarDado();
        if (selecaoDado >= 15) {eveSelecao = new Sons("assets/eve/selecaoeve1.wav");}
        else if (selecaoDado >= 10){ eveSelecao = new Sons("assets/eve/selecaoeve2.wav");}
        else if (selecaoDado >= 5){ eveSelecao = new Sons("assets/eve/selecaoeve3.wav");}
        else { eveSelecao = new Sons("assets/eve/selecaoeve4.wav");}

        // Sons misc
        Sons transicao = new Sons("assets/andando.wav");
        Sons pegandoitem = new Sons("assets/pegandoitem.wav");
        Sons ambienteObs1 = new Sons("assets/ambienteObs1.wav");
        Sons ambienteObs2 = new Sons("assets/ambienteObs2.wav");
        Sons ambienteObs3 = new Sons("assets/ambienteObs3.wav");
        Sons caminhoBom = new Sons("assets/caminhobom.wav");


        //Personagens
        Personagens PersonagemEscolhido = null; // Fonte da Dica de Null: chatgpt
        PersonagemUm Magnolio = new PersonagemUm();
        PersonagemDois Eve = new PersonagemDois();
        //Itens
        GarrafaDeAgua garrafa = new GarrafaDeAgua();
        PedacoDeFerro pedaco = new PedacoDeFerro();
        RoubarChave guardaChave = new RoubarChave();
        EnfrentarGuardas guarda1 = new EnfrentarGuardas("Guarda 1");
        EnfrentarGuardas guarda2 = new EnfrentarGuardas("Guarda 2");
        Tonico tonico = new Tonico();
        Pao pao = new Pao();
        //Obstaculos
        EnfrentarDiretorEFugir diretor = new EnfrentarDiretorEFugir("Diretor-Chefe");
        boolean passouObstaculo = false;


        System.out.println("Boas-Vindas ao Jogo de RPG de Técnicas de Programação!\nAbaixo temos dois incriveis personagens para sua aventura!");
        System.out.println(Magnolio);
        Magnolio.contarHistoria();
        Sons.intervalo(4000);
        System.out.println(Eve);
        Eve.contarHistoria();
        Sons.intervalo(4000);


        do { // Escolha de personagem
            try {
                escolha = "";
                System.out.println("Qual personagem voce deseja escolher?\n1 - Magnólio\n2 - Eve");
                escolha = in.nextLine();

                switch (VerificadorEscolha.VerificarEscolha(escolha, "2")) {
                    case "1":
                        PersonagemEscolhido = Magnolio;
                        System.out.println("Magnólio foi escolhido!");
                        magnolioSelecao.tocarSom();
                        break;

                    case "2":
                        PersonagemEscolhido = Eve;
                        System.out.println("Eve foi escolhida!");
                        eveSelecao.tocarSom();
                        break;
                }

            } catch (EscolhaInvalException e) {
                System.out.println(e.getMessage());
            }
        } while (!Objects.equals(escolha, "1") && !Objects.equals(escolha, "2"));

        // Sons dos personagens


        Main.embaralharFalas(PersonagemEscolhido); // embaralha e define as falas


        System.out.println("Começo do jogo!");
        System.out.println("Você está na cela da prisão e precisa fugir para salvar Retropólis!\n");
        Sons.intervalo(isMag(PersonagemEscolhido) ?4000:6000);

        // Escolha de itens no inventário
        Set<String> itensEscolhidos = new HashSet<>(); // Foi feito anteriormente com ArrayList, mas pela dificuldade de selecionar dois itens de forma adequada foi utilizado o hashmap sugerido pelo chatgpt
        Map<String, Item> mapaItens = new HashMap<>();
        mapaItens.put("1", garrafa);
        mapaItens.put("2", pedaco);
        mapaItens.put("3", tonico);
        mapaItens.put("4", pao);

        while (itensEscolhidos.size() < 2) {
            try {
                System.out.println("Quais itens deseja adicionar ao seu inventário antes de começar o jogo? (Escolha até 2 itens)");

                // Exibe apenas os itens ainda não escolhidos
                for (Map.Entry<String, Item> entry : mapaItens.entrySet()) {
                    if (!itensEscolhidos.contains(entry.getKey())) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());

                    }
                }

                escolha = in.nextLine();
                String itemEscolhido = VerificadorEscolha.VerificarEscolha(escolha, "4");

                if (!itensEscolhidos.contains(itemEscolhido)) {
                    Item item = mapaItens.get(itemEscolhido);
                    if (item != null) {
                        item.adicionarItem(PersonagemEscolhido, item);
                        itensEscolhidos.add(itemEscolhido);
                        System.out.println(item + " foi escolhido e adicionado ao seu inventário!\n");
                        pegandoitem.tocarSom();
                        Sons.intervalo(2000);

                    } else {
                        throw new EscolhaInvalException("Item inválido!");
                    }
                } else {
                    System.out.println("Você já escolheu esse item! Por favor, escolha outro.");
                    Sons.intervalo(2000);
                }
            } catch (EscolhaInvalException e) {
                System.out.println(e.getMessage());
            }
        }
        somSelecao.pararSom();
        System.out.println("Você começa a observar o dia a dia do guarda, e nota que ha três possibilidades interessantes \n" +
                "para tentar fugir, qual voce escolherá?, dependendo da escolha a punição será severa...");
        ambienteObs1.tocarEmLoop();
        Sons.intervalo(4000);

        do { // Obstaculo 1 - Roubar Chave do guarda
            try {
                Main.embaralharFalas(PersonagemEscolhido);
                System.out.println("\n" + PersonagemEscolhido.menuPersonagem() + "\n\n" +
                        "1-Distrair o guarda e pegar a chave (Jogar uma pedra em uma posiçao longe) - Risco baixo\n" +
                        "2-Tentar pegar chave do guarda enquanto ele está dormindo - Risco médio\n" +
                        "3-Tentar se esconder perto da porta da cela e pegar a chave quando o guarda passar próximo - Risco Alto");
                escolha = in.nextLine();
                int dado = Personagem.jogarDado();
                switch (VerificadorEscolha.VerificarEscolha(escolha, "3")) {
                    case "1":
                        if (dado >= 7) { //1-Distrair o guarda e pegar a chave (Jogar uma pedra em uma posiçao longe) - Risco baixo
                            System.out.println(Personagem.dadoJogado(dado));
                            System.out.println("Você conseguiu distrair o guarda, pegar a chave aguardou o momento certo que pudesse usá-la para fugir.\n");
                            passouObstaculo = true;
                            Sons.intervalo(2000);
                        } else {
                            System.out.println(Personagem.dadoJogado(dado));
                            System.out.println("O guarda notou sua tentativa de distração e te atacou!(-9 PV)\n");
                            personagemRecebeDano.tocarSom();
                            guardaChave.interageComPersonagem(PersonagemEscolhido, 4);
                            Sons.intervalo(2000);
                            //personagemRecebeDano.pararSom();
                        }
                        break;

                    case "2":
                        if (dado >= 10) {
                            System.out.println(Personagem.dadoJogado(dado));
                            System.out.println("Você conseguiu pegar a chave enquanto o guarda dormia e se preparou para usá-la no momento certo.\n");
                            passouObstaculo = true;
                            Sons.intervalo(2000);
                        } else {
                            System.out.println(Personagem.dadoJogado(dado));
                            System.out.println("O guarda acordou antes que você conseguisse pegar a chave e te atacou!(-7 PV)\n");
                            personagemRecebeDano.tocarSom();
                            guardaChave.interageComPersonagem(PersonagemEscolhido, 2);
                            Sons.intervalo(2000);
                            // personagemRecebeDano.pararSom();
                        }
                        break;

                    case "3":
                        if (dado >= 12) {
                            System.out.println(Personagem.dadoJogado(dado));
                            System.out.println("Você conseguiu se esconder e pegou a chave quando o guarda passou próximo, sem ser percebido.\n");
                            passouObstaculo = true;
                            Sons.intervalo(2000);

                        } else {
                            System.out.println(Personagem.dadoJogado(dado));
                            System.out.println("O guarda notou sua presença enquanto você tentava se esconder e reagiu rapidamente!(-5 PV)\n");
                            personagemRecebeDano.tocarSom();
                            guardaChave.interageComPersonagem(PersonagemEscolhido, 0);
                            Sons.intervalo(2000);
                            // personagemRecebeDano.pararSom();
                        }
                        break;
                }
            } catch (EscolhaInvalException e) {
                System.out.println(e.getMessage());
            }
        } while (!PersonagemEscolhido.isMorto() && !passouObstaculo);
        if (PersonagemEscolhido.isMorto()) {

        }
        PersonagemEscolhido.perdeuJogo();
        ambienteObs1.pararSom();

        System.out.println("Você recupera seus pertences que estavam no armázem!");
        aumentarLvl(PersonagemEscolhido, 25);
        Sons.intervalo(4000);
        transicao.tocarSom();
        Sons.intervalo(3000);
        System.out.println(PersonagemEscolhido.recuperaPoder() + "\nPoder básico e Poder especial desbloqueados!\n");
        personagemRecuperaPoder.tocarSom();
        Sons.intervalo(isMag(PersonagemEscolhido) ?4000:5000);
        passouObstaculo = false;
        ambienteObs2.tocarEmLoop();
        System.out.println("Após recuperar suas armas, você encontra dois guardas no corredor e a única alternativa é enfrentar eles para escapar!" +
                "\nLute!\n");
        Sons.intervalo(2000);

        do {// Obstaculo 2 - Enfrentar os guardas
            if (vez) {
                if (PersonagemEscolhido.poderEspecialAtivado) {
                        PersonagemEscolhido.poderEspecialAtivado = false;
                }
                if (PersonagemEscolhido.getPao()) {
                    PersonagemEscolhido.setPao(false);
                }
                try {
                    Main.embaralharFalas(PersonagemEscolhido);
                    if (Main.isMag(PersonagemEscolhido)) {
                        System.out.println(PersonagemEscolhido.getNome()
                                + (guarda1.getVida() > 0 ? " | " + guarda1.getNome() : "")
                                + (guarda2.getVida() > 0 ? " | " + guarda2.getNome() : ""));
                    }else {
                        System.out.println(PersonagemEscolhido.getNome()
                                + (guarda1.getVida() > 0 ? "      | " + guarda1.getNome() : "")
                                + (guarda2.getVida() > 0 ? " | " + guarda2.getNome() : ""));
                    }

                    System.out.println("Vida: " + Integer.toString(PersonagemEscolhido.getVida()) +

                            "" + (guarda1.getVida() > 0 ? " | Vida: " + Integer.toString(guarda1.getVida()) : "") +

                            "" + (guarda2.getVida() > 0 ? " | Vida: " + Integer.toString(guarda2.getVida()) : ""));


                    System.out.println("1 - Atacar primeiro guarda" + "\n2 - Atacar segundo guarda" + "\n3 - Abrir inventário");
                    escolha = "";
                    escolha = in.nextLine();
                    switch (VerificadorEscolha.VerificarEscolha(escolha, "3")) {
                        case "1":
                            try {
                                if (guarda1.getVida() == 0) {
                                    System.out.println("Guarda 1 está morto! Ataque outro guarda!\n");
                                    Sons.intervalo(2000);
                                } else {
                                    do {
                                        System.out.println("1 - Utilizar poder" + "\n2 - Utilizar poder especial" + "\n3 - Voltar");
                                        escolha = in.nextLine();
                                        switch (VerificadorEscolha.VerificarEscolha(escolha, "3")) {
                                            case "1" -> {
                                                Main.embaralharFalas(PersonagemEscolhido);
                                                PersonagemEscolhido.utilizarPoder(guarda1);
                                                personagemPoder.tocarSom();
                                                vez = false;
                                                Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                                                if (guarda1.getVida() <= 0) {
                                                    System.out.println(guarda1.getNome()+" Morreu!");
                                                    Main.inimigomorre.tocarSom();}
                                            }
                                            case "2" -> {
                                                Main.embaralharFalas(PersonagemEscolhido);
                                                if (PersonagemEscolhido.utilizarPoderEspecial(guarda1)) {
                                                    vez = false;
                                                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                                                    if (guarda1.getVida() <= 0){
                                                        System.out.println(guarda1.getNome()+" Morreu!");
                                                        Main.inimigomorre.tocarSom();}
                                                }
                                            }
                                            case "3" -> {
                                            }
                                        }

                                    } while (!Objects.equals(escolha, "1") && !Objects.equals(escolha, "2") && guarda1.getVida() == 0);
                                }
                            } catch (EscolhaInvalException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "2":
                            try {
                                if (guarda2.getVida() == 0) {
                                    System.out.println("Guarda 2 está morto! Ataque outro guarda!\n");
                                } else {
                                    do {
                                        System.out.println("1 - Utilizar poder" + "\n2 - Utilizar poder especial" + "\n3 - Voltar");
                                        escolha = in.nextLine();
                                        switch (VerificadorEscolha.VerificarEscolha(escolha, "3")) {
                                            case "1" -> {
                                                Main.embaralharFalas(PersonagemEscolhido);
                                                PersonagemEscolhido.utilizarPoder(guarda2);
                                                vez = false;
                                                Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                                                if (guarda2.getVida() <= 0){
                                                    System.out.println(guarda2.getNome()+" Morreu!");
                                                    Main.inimigomorre.tocarSom();}

                                            }
                                            case "2" -> {
                                                Main.embaralharFalas(PersonagemEscolhido);
                                                if (PersonagemEscolhido.utilizarPoderEspecial(guarda2)) {
                                                    vez = false;
                                                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                                                    if (guarda2.getVida() <= 0){
                                                        System.out.println(guarda2.getNome()+" Morreu!");
                                                        Main.inimigomorre.tocarSom();
                                                    }
                                                }
                                            }
                                            case "3" -> {
                                            }
                                        }
                                    } while (!Objects.equals(escolha, "1") && !Objects.equals(escolha, "2") && !Objects.equals(escolha, "3") && guarda2.getVida() == 0);
                                }
                            } catch (EscolhaInvalException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "3":
                            if (!PersonagemEscolhido.inventario.isEmpty()) {
                                System.out.println("\n" + PersonagemEscolhido.menuPersonagem() + PersonagemEscolhido.mostrarInventario());
                                System.out.println("3 - Voltar");
                                try {
                                    do {
                                        escolha = in.nextLine();
                                        String s = VerificadorEscolha.VerificarEscolha(escolha, "3");
                                        switch (s) {
                                            case "1" -> {
                                                if (PersonagemEscolhido.getInventario().getFirst() instanceof PedacoDeFerro) {
                                                    System.out.println("Não é possivel utilizar o Pedaço de ferro, seu efeito é passivo!");
                                                    Sons.intervalo(2000);
                                                }
                                                else if(PersonagemEscolhido.inventario.getFirst() == null){
                                                    System.out.println("Escreva um valor válido");
                                                }
                                                else {
                                                    PersonagemEscolhido.inventario.getFirst().usarItem(PersonagemEscolhido);
                                                    System.out.println("Você usou " + PersonagemEscolhido.inventario.getFirst());
                                                    PersonagemEscolhido.inventario.removeFirst();
                                                    vez = false;
                                                }
                                            }
                                            case "2" -> {
                                                try {
                                                    if (PersonagemEscolhido.getInventario().size() == 2) {
                                                        if (PersonagemEscolhido.getInventario().get(1) instanceof PedacoDeFerro) {
                                                            System.out.println("Não é possivel utilizar o Pedaço de ferro, seu efeito é passivo!");
                                                            Sons.intervalo(2000);
                                                        } else {
                                                            PersonagemEscolhido.inventario.get(1).usarItem(PersonagemEscolhido);
                                                            System.out.println("Você usou " + PersonagemEscolhido.inventario.get(1));
                                                            PersonagemEscolhido.inventario.remove(1);
                                                            vez = false;
                                                        }
                                                    } else {
                                                        throw new EscolhaInvalException("Não há item nesse espaço");
                                                    }
                                                } catch (EscolhaInvalException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            }

                                            case "3" -> {
                                            }
                                        }
                                        System.out.println();
                                    } while (!Objects.equals(escolha, "1") && !Objects.equals(escolha, "2") && !Objects.equals(escolha, "3"));
                                } catch (EscolhaInvalException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else System.out.println("O inventário está vazio!");
                            break;
                    }
                } catch (EscolhaInvalException e) {
                    System.out.println(e.getMessage());
                }
                escolha = "";
            } else {
                if (guarda1.getVida() > 0) {
                    Main.embaralharFalas(PersonagemEscolhido);
                    System.out.println("Agora é a vez do Guarda 1 atacar!");
                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                    personagemRecebeDano.tocarSom();
                    guarda1.interageComPersonagem(PersonagemEscolhido, 2);
                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                    //personagemRecebeDano.pararSom();
                }
                if (guarda2.getVida() > 0) {
                    Main.embaralharFalas(PersonagemEscolhido);
                    System.out.println("Agora é a vez do Guarda 2 atacar!");
                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                    personagemRecebeDano.tocarSom();
                    guarda2.interageComPersonagem(PersonagemEscolhido, 2);
                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);
                    //personagemRecebeDano.pararSom();
                }
                vez = true;
            }
            if (guarda1.getVida() == 0 && guarda2.getVida() == 0) {
                //Main.inimigomorre.pararSom();
                Main.embaralharFalas(PersonagemEscolhido);
                System.out.println("Os dois guardas foram mortos!");
                Main.inimigomorre.tocarSom();
                Sons.intervalo(2000);
                passouObstaculo = true;
                vez = true;
            }

        } while (!PersonagemEscolhido.isMorto() && !passouObstaculo);
        if (PersonagemEscolhido.isMorto()) {
            personagemMorte.tocarSom();
        }
        Sons.intervalo(300);
        PersonagemEscolhido.perdeuJogo();
        ambienteObs2.pararSom();
        transicao.tocarSom();
        Sons.intervalo(4000);
        aumentarLvl(PersonagemEscolhido, 35);
        Sons.intervalo(4000);
        System.out.println("Você corre pelo corredor ansiando pela fuga. Mas, infelizmente, quando você menos espera o Diretor da prisão aparece na sua frente...");
        System.out.println("Você não tem mais opção do que fazer. A única fuga: o esgoto da prisão está atrás dele. A única maneira é dete-lo!");
        System.out.println("Este é seu último e maior desafio, siga em frente, jovem padawan...");
        System.out.println("Lute!\n");
        Sons.intervalo(4000);
        ambienteObs3.tocarEmLoop();
        passouObstaculo = false;
        PersonagemEscolhido.isTonico(); // verifica o valor do tonico e coloca para 0 se ele foi utilizado

        do {// Obstaculo 3 - Enfrentar o diretor
            if (vez) {
                if (PersonagemEscolhido.poderEspecialAtivado) {
                    PersonagemEscolhido.setPoderEspecialAtivado(false);
                }

                if (PersonagemEscolhido.getPao()) {
                    PersonagemEscolhido.setPao(false);
                }
                try {
                    Main.embaralharFalas(PersonagemEscolhido);
                    System.out.println(PersonagemEscolhido.getNome()
                            + (+diretor.getVida() > 0 ? " | " + diretor.getNome() : ""));
                    System.out.println("Vida: " + PersonagemEscolhido.getVida()
                            + (+diretor.getVida() > 0 ? " | Vida: " + diretor.getVida() : ""));
                    System.out.println("1 - Atacar Diretor-Chefe" + "\n2 - Abrir inventário");
                    escolha = "";
                    escolha = in.nextLine();
                    switch (VerificadorEscolha.VerificarEscolha(escolha, "2")) {
                        case "1":
                            try {
                                if (diretor.getVida() == 0) {
                                    System.out.println("Diretor-Chefe está morto!\n");
                                } else {
                                    do {
                                        System.out.println("1 - Utilizar poder" + "\n2 - Utilizar poder especial" + "\n3 - Voltar");
                                        escolha = in.nextLine();
                                        switch (VerificadorEscolha.VerificarEscolha(escolha, "3")) {
                                            case "1" -> {
                                                Main.embaralharFalas(PersonagemEscolhido);
                                                PersonagemEscolhido.utilizarPoder(diretor);
                                                vez = false;
                                                Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);

                                            }
                                            case "2" -> {
                                                Main.embaralharFalas(PersonagemEscolhido);
                                                if (PersonagemEscolhido.utilizarPoderEspecial(diretor)) {

                                                    vez = false;
                                                    Sons.intervalo(isMag(PersonagemEscolhido) ?2000:4000);

                                                }
                                            }
                                            case "3" -> {
                                            }
                                        }

                                    } while (!Objects.equals(escolha, "1") && !Objects.equals(escolha, "2") && !Objects.equals(escolha, "3") && (diretor.getVida() == 0));
                                }
                            } catch (EscolhaInvalException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "2":
                            if (!PersonagemEscolhido.inventario.isEmpty()) {
                                System.out.println("\n" + PersonagemEscolhido.menuPersonagem() + PersonagemEscolhido.mostrarInventario());
                                System.out.println("3 - Voltar");
                                try {
                                    do {
                                        escolha = in.nextLine();
                                        String s = VerificadorEscolha.VerificarEscolha(escolha, "3");
                                        switch (s) {
                                            case "1" -> {
                                                if (PersonagemEscolhido.inventario.getFirst() instanceof PedacoDeFerro) {
                                                    System.out.println("Não é possivel utilizar o Pedaço de ferro, seu efeito é passivo!");
                                                    Sons.intervalo(2000);
                                                }
                                                else {
                                                    System.out.println("Você usou " + PersonagemEscolhido.inventario.getFirst());
                                                    PersonagemEscolhido.inventario.getFirst().usarItem(PersonagemEscolhido);
                                                    PersonagemEscolhido.inventario.removeFirst();
                                                    vez = false;
                                                }
                                            }
                                                case "2" -> {
                                                    try {
                                                        if (PersonagemEscolhido.getInventario().size() == 2) {
                                                            if (PersonagemEscolhido.getInventario().get(1) instanceof PedacoDeFerro) {
                                                                System.out.println("Não é possivel utilizar o Pedaço de ferro, seu efeito é passivo!");
                                                                Sons.intervalo(2000);
                                                            } else {
                                                                PersonagemEscolhido.inventario.get(1).usarItem(PersonagemEscolhido);
                                                                System.out.println("Você usou " + PersonagemEscolhido.inventario.get(1));
                                                                PersonagemEscolhido.inventario.remove(1);
                                                                vez = false;
                                                            }
                                                        } else {
                                                            throw new EscolhaInvalException("Não há item nesse espaço");
                                                        }
                                                    } catch (EscolhaInvalException e) {
                                                        System.out.println(e.getMessage());
                                                    }
                                                }
                                            case "3" -> {
                                            }
                                        }
                                    } while (!Objects.equals(escolha, "1") && !Objects.equals(escolha, "2") && !Objects.equals(escolha, "3"));
                                } catch (EscolhaInvalException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else System.out.println("O inventário está vazio!");
                            break;
                    }
                } catch (EscolhaInvalException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Main.embaralharFalas(PersonagemEscolhido);
                if (diretor.getVida() > 0) {
                    Main.embaralharFalas(PersonagemEscolhido);
                    System.out.println("Agora é a vez do Diretor-Chefe atacar!");
                    Sons.intervalo(2000);
                    personagemRecebeDano.tocarSom();
                    diretor.interageComPersonagem(PersonagemEscolhido, 3);
                    Sons.intervalo(2000);
                }
                vez = true;
            }
            if (diretor.getVida() <= 0) {
                System.out.println("O Diretor-Chefe foi morto!");
                passouObstaculo = true;
            }

        } while (!PersonagemEscolhido.isMorto() && !passouObstaculo);
        ambienteObs3.pararSom();

        PersonagemEscolhido.perdeuJogo();

        transicao.tocarSom();
        Sons.intervalo(3000);
        Main.finalbom();

        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        long segundos = duracao.toSecondsPart();
        long minutos = duracao.toMinutesPart();
        long horas = duracao.toHours();
        System.out.println("\nEscreva seu nome para guardar no ranking: ");
        ranking = in.nextLine();

        if (!(PersonagemEscolhido.isMorto())) {
            writer.write(ranking +
                    "\nPersonagem escolhido: " + PersonagemEscolhido.getNome() +
                    "\nVida final: " + PersonagemEscolhido.getVida() +
                    "\nTempo total: " + horas + "h" + minutos + "m" + segundos + "s\n");
            writer.close();
        }
    }





    public static void embaralharFalas(Personagens personagem){
        assert personagem != null;
        int selecaoDado = Personagem.jogarDado();
        // Ataque
        selecaoDado = Personagem.jogarDado();
        if (selecaoDado >= 12) {
            ataque = new Sons("assets//ataque1.wav");
        } else if (selecaoDado >= 6) {
            ataque = new Sons("assets//ataque2.wav");
        } else {
            ataque = new Sons("assets//ataque3.wav");
        }

        // Inimigo recebe dano
        selecaoDado = Personagem.jogarDado();
        if (selecaoDado >= 15) {
            inimigorecebedano = new Sons("assets/inimigo/inimigoreceberdano1.wav");
        } else if (selecaoDado >= 10) {
            inimigorecebedano = new Sons("assets/inimigo/inimigoreceberdano2.wav");
        } else if (selecaoDado >= 5) {
            inimigorecebedano = new Sons("assets/inimigo/inimigoreceberdano3.wav");
        } else {
            inimigorecebedano = new Sons("assets/inimigo/inimigoreceberdano4.wav");
        }

        // Morte dos guardas
        selecaoDado = Personagem.jogarDado();
        if (selecaoDado >= 15) {
            inimigomorre = new Sons("assets/inimigo/inimigomorrer1.wav");
        } else if (selecaoDado >= 10) {
            inimigomorre = new Sons("assets/inimigo/inimigomorrer2.wav");
        } else if (selecaoDado >= 5) {
            inimigomorre = new Sons("assets/inimigo/inimigomorrer3.wav");
        } else {
            inimigomorre = new Sons("assets/inimigo/inimigomorrer4.wav");
        }

        if (personagem.getNome().equalsIgnoreCase("Magnólio")) {
            // Poder - Magnolio
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado <= 10) {
                personagemPoder = new Sons("assets/magnolio/poder1.wav");
            } else {
                personagemPoder = new Sons("assets/magnolio/poder2.wav");
            }

            // Poder Especial - Magnolio
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado >= 12) {
                personagemPoderEspecial = new Sons("assets/magnolio/poderespecial1.wav");
            } else if (selecaoDado >= 6) {
                personagemPoderEspecial = new Sons("assets/magnolio/poderespecial2.wav");
            } else {
                personagemPoderEspecial = new Sons("assets/magnolio/poderespecial3.wav");
            }

            // Recebe dano - Magnolio
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado >= 15) {
                personagemRecebeDano = new Sons("assets/magnolio/recebedano1.wav");
            } else if (selecaoDado >= 10) {
                personagemRecebeDano = new Sons("assets/magnolio/recebedano2.wav");
            } else if (selecaoDado >= 5) {
                personagemRecebeDano = new Sons("assets/magnolio/recebedano3.wav");
            } else {
                personagemRecebeDano = new Sons("assets/magnolio/recebedano4.wav");
            }

            // Recuperar Poder - Magnolio
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado >= 12) {
                personagemRecuperaPoder = new Sons("assets/magnolio/recuperarpoder1.wav");
            } else if (selecaoDado >= 6) {
                personagemRecuperaPoder = new Sons("assets/magnolio/recuperarpoder2.wav");
            } else {
                personagemRecuperaPoder = new Sons("assets/magnolio/recuperarpoder3.wav");
            }

            // Morte Magnolio
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado >= 15) {
                personagemMorte = new Sons("assets/magnolio/morrer1.wav");
            } else if (selecaoDado >= 10) {
                personagemMorte = new Sons("assets/magnolio/morrer2.wav");
            } else if (selecaoDado >= 5) {
                personagemMorte = new Sons("assets/magnolio/morrer3.wav");
            } else {
                personagemMorte = new Sons("assets/magnolio/morrer4.wav");
            }

            // Frase final - Magnolio
            Sons frasefinalMag = new Sons("assets/magnolio/frasefinal.wav");
        }

        else { // Sons eve

            // Poder - Eve
            personagemPoder = new Sons("assets/eve/poder1.wav");
            personagemPoderErro = new Sons("assets/eve/poder2.wav");



            // Poder Especial - Eve
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado > 10) {
                personagemPoderEspecial = new Sons("assets/eve/poderespecial1.wav");
            } else{
                personagemPoderEspecial = new Sons("assets/eve/poderespecial2.wav");
            }

            // Recebe dano - Eve
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado >= 10) {
                personagemRecebeDano = new Sons("assets/eve/recebedano1.wav");
            }
            else {
                personagemRecebeDano = new Sons("assets/eve/recebedano3.wav");
            }

                personagemRecebeDanoEspecial = new Sons("assets/eve/recebedano2.wav");


            // Recuperar Poder - Eve
            personagemRecuperaPoder = new Sons("assets/eve/ganharpoderespecialeve.wav");


            // Morte Eve
            selecaoDado = Personagem.jogarDado();
            if (selecaoDado > 10) {
                personagemMorte = new Sons("assets/eve/morrer1.wav");
            } else{
                personagemMorte = new Sons("assets/eve/morrer2.wav");
            }


        }
    }


    public static void finalbom(){
        System.out.println("Magnolio e Eve se encontram na saída da prisão!, ambos conseguem fugir e\n" +
                "vão em busca de desmascarar as mentiras contadas por Vaporlópolis");
        Main.frasefinalMag.tocarSom();
        Sons.intervalo(6000);
        System.out.println("Creditos das Vozes:\nMagnolio - Luís Felipe Lopes\nEve - Manuela Gonçalves Lopes\nClank - Rhuan Soares Ramos");
        System.out.println("Autores do código:\nRhuan Soares Ramos\nThauan Fabrício da Rocha");
        System.out.println("Obrigado por jogar nosso jogo!\nAté mais!");
        Sons.intervalo(10000);
    }

    public static boolean isMag(Personagens personagem){
        if (personagem.getNome().equalsIgnoreCase("Magnólio")) {return true;}
        return false;
    }

    public static void aumentarLvl(Personagens personagem, int vida){
        if (isMag(personagem)){
            personagem.setVida(vida);
            System.out.println(personagem.getNome() +" subiu de nível!\nSua vida agora é "+personagem.getVida());

        }else {
            personagem.setVida(vida);
            System.out.println(personagem.getNome() + " subiu de nível!\nSua vida agora é "+personagem.getVida());

        }
    }













}







