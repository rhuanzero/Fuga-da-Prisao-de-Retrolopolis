import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Personagens implements Personagem{
    protected String nome;
    protected int idade;
    protected int vida; // caracteristica
    protected String historia;
    protected int bonusFerro = 0; // colocar no uml
    protected ArrayList<Item> inventario = new ArrayList<>();
    protected boolean pao = false;// colocar no uml
    protected boolean pedra = false;// colocar no uml
    protected boolean poderEspecial = true;//colocar no uml
    protected int danoTonico = 0;
    protected boolean poderEspecialAtivado = false;//colocar no uml

    public int getDanoTonico() {
        return danoTonico;
    }

    public void setDanoTonico(int danoTonico) {
        this.danoTonico = danoTonico;
    }


    private Random rand = new Random();
    private Scanner in = new Scanner(System.in);

    public Personagens(String nome, int idade, int vida, String historia){
        setNome(nome);
        setIdade(idade);
        setVida(vida);
        setHistoria(historia);
    }

    public void utilizarPoder(Obstaculos obstaculo){
        int dano;
        int dado = Personagem.jogarDado();
        System.out.println("\n" + "Número do dado: "  + dado);
        if(dado >= 15){
            dano = 10 + bonusFerro + danoTonico;
            System.out.println("Dano: " + dano + "\n");
            Main.ataque.tocarSom();
            Main.personagemPoder.tocarSom();
            Sons.intervalo(Main.isMag(this) ?2000:4000);
            Main.inimigorecebedano.tocarSom();

        }
        else if(dado >= 5 ){
            dano = 5 + bonusFerro + danoTonico;
            System.out.println("Dano: " + dano + "\n");
            Main.ataque.tocarSom();
            Main.personagemPoder.tocarSom();
            Sons.intervalo(Main.isMag(this) ?2000:4000);
            Main.inimigorecebedano.tocarSom();
        }
        else{
            if (!Main.isMag(this)){Main.personagemPoderErro.tocarSom();}
            dano = 0;
            System.out.println("Dano: " + dano + "\n");
        }
        System.out.println("O "+obstaculo.getNome()+" perdeu "+dano+" de vida!");
        obstaculo.setVida(obstaculo.getVida() - dano);
        if(obstaculo.getVida() <= 0){
            obstaculo.setVida(0);
        }
        if(!poderEspecial){
            poderEspecial = true;
        }
        if (obstaculo.getVida()<=0){System.out.println("O "+obstaculo.getNome()+" está morto!");}

    }
    public abstract boolean utilizarPoderEspecial(Obstaculos obstaculo);
    @Override
    public void acessarInventario(ArrayList inventario){ //retorna o item
        int indice;
        /*for(Object o : inventario){
            System.out.println(o);
        }*/
        indice = in.nextInt();
        System.out.println(inventario.get(indice));
        //item.usarItem(indice);
    }

    public void perdeuJogo(){
        if (this.vida <= 0){
            Main.fimdejogo.tocarSom();
            Sons.intervalo(300);
            Main.personagemMorte.tocarSom();
            Sons.intervalo(300);
            System.out.println("Infelizmente, a jornada chegou ao fim. Você foi levado para a solitária \n" +
                    "e perdeu a oportunidade de escapar. " +  "Mas não desista, sua luta não acaba aqui. \n" +
                    "Levante-se e tente novamente!");
            Sons.intervalo(16000);
            System.exit(0);
        }
    }

    public String menuPersonagem(){ // ADD NO UML
        return (nome+"\nVida:"+vida);
    }

    public String mostrarInventario(){ // ADD NO UML
        if (inventario.size() == 1){
            return "\n" + "1 - " + inventario.get(0).toString();
        }
        return "\n" + "1 - " + inventario.get(0).toString() + " \n2 - " + inventario.get(1).toString();
    }

    public boolean isMorto(){ // ADD no UML
        if (getVida()<=0){
            return true;
        }
        else {
            return false;
        }
    }

    public void isTonico(){
        boolean possuiTonico = false;
        for (Object item : inventario) {
            if (item instanceof Tonico) {
                possuiTonico = true;
                break;
            }
        }
        if (!possuiTonico) {
            danoTonico = 0;
        }
    }

    @Override
    public void adicionarItem(Item item){ //Criar classe Item
        this.inventario.add(item);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
            this.nome = nome;
        }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public ArrayList<Item> getInventario() {
        return inventario;
    }

    public void setInventario(ArrayList<Item> inventario) {
        this.inventario = inventario;
    }

    public int getBonusFerro() {
        return bonusFerro;
    }

    public void setBonusFerro(int bonusFerro) {
        this.bonusFerro = bonusFerro;
    }

    public boolean getPao() {
        return pao;
    }

    public void setPao(boolean pao) {
        this.pao = pao;
    }

    public void contarHistoria(){
        System.out.println(historia);
    }

    public boolean isPedra() {
        return pedra;
    }

    public void setPedra(boolean pedra) {
        this.pedra = pedra;
    }
    public boolean getPoderEspecialAtivado(){return poderEspecialAtivado;}

    public void setPoderEspecialAtivado(boolean poderEspecialAtivado) {
        this.poderEspecialAtivado = poderEspecialAtivado;
    }

    public void setPoderEspecial(boolean poderEspecial) {
        this.poderEspecial = poderEspecial;
    }

    public boolean isPoderEspecial() {
        return poderEspecial;
    }

    public abstract String recuperaPoder();


}
