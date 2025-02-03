import javax.sound.sampled.*;
import java.io.File;

public class Sons {

    File arquivoSom;
    AudioInputStream audioStream;
    Clip clip;

    public Sons(String arquivo) {
        try {
            this.arquivoSom = new File(arquivo);
            this.audioStream = AudioSystem.getAudioInputStream(arquivoSom);
            this.clip = AudioSystem.getClip();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Formato de áudio não suportado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o áudio: " + e.getMessage());
        }
    }
    public void tocarSom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (audioStream != null) {
                        audioStream.close(); // Fecha o stream anterior, se existir
                    }
                    audioStream = AudioSystem.getAudioInputStream(arquivoSom);
                    if (!clip.isOpen()) { clip.open(audioStream);}
                    clip.stop();
                    clip.setFramePosition(0);
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                    clip.close();
                    if (clip.isOpen()) { clip.close();}
                } catch (Exception e) {
                    System.out.println("Erro ao tocar o som: " + e.getMessage());
                }
            }
        }).start();
    }

    public void tocarEmLoop() {
        new Thread(() -> {
            try {
                if (audioStream != null) {
                    audioStream.close(); // Fecha o stream anterior, se existir
                }
                audioStream = AudioSystem.getAudioInputStream(arquivoSom);
                if (!clip.isOpen()) {
                    clip.open(audioStream); // Abre o clip com o áudio
                }
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Configura o loop infinito
            } catch (Exception e) {
                System.out.println("Erro ao tocar o som em loop: " + e.getMessage());
            }
        }).start();
    }



    public static void intervalo(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pararSom() {
        clip.stop();
        clip.close();
    }
}


