import java.util.ArrayList;

public interface Personagem {
    void contarHistoria();
    void utilizarPoder(Obstaculos obstaculo);
    boolean utilizarPoderEspecial(Obstaculos obstaculo);
    void adicionarItem(Item item);
    void acessarInventario(ArrayList<Item> inventario);
    static int jogarDado(){
        int max = 20, min = 1;
        return ((int) (Math.random() * (max - min + 1)) + min);
    }
    static String dadoJogado(int dado){
        return "Dado jogado: "+ dado;
    }
}
