package View;
import javafx.scene.media.AudioClip;
import java.io.File;

public class Music extends Thread{
    String name;
    public AudioClip audio;
    public Music(String s, boolean stop) {
        name = s;
        stopMusic();
        runMusic();
    }
    public void runMusic(){
        int playTimes = 1;
        audio = new AudioClip(new File(String.format("ATP-Project-PartC/resources/%s.mp3", name)).toURI().toString());
        audio.setVolume(0.5f);
        audio.setCycleCount(playTimes);
        audio.play();
    }
    public void stopMusic(){
        if (audio != null) {
            audio.stop();
        }
    }
}
