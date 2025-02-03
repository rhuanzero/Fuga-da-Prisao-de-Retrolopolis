import java.util.ArrayList;
import java.util.Scanner;

public abstract class Item {
    protected String nome;
    protected String descricao;
    protected int quantidade;
    protected int efeito;
    //ArrayList<Item> inventario = new ArrayList<>(); Já possui isso na classe personagens
    //PersonagemUm personagem = new PersonagemUm("Magnólio", 36, 20, "pica", inventario);
    Scanner in = new Scanner(System.in);
    
    public Item(String nome, String descricao, int efeito) {
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
    }


    public void usarItem(Personagens personagem){
        if(!(personagem.isPoderEspecial())){
            personagem.setPoderEspecial(true);
        }
    }


    public String toString() {
        return nome + " - " + descricao;
    }

    public abstract void adicionarItem(Personagens personagemEscolhido, Item item);


    public int getEfeito() {
        return efeito;
    }

    public void setEfeito(int efeito) {
        this.efeito = efeito;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
