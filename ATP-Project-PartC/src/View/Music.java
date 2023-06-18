package View;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music extends Thread{
    public Music(String s) {
        super(s);
        int playTimes = 1;
        AudioClip audio = new AudioClip(new File(String.format("ATP-Project-PartC/resources/%s.mp3", s)).toURI().toString());
        audio.setVolume(0.5f);
        audio.setCycleCount(playTimes);
        audio.play();
    }
}
