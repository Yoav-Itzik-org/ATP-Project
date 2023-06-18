package View;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Music extends Thread{
    public AudioClip audio ;
    public Music(String songName) {
        audio = new AudioClip(new File(String.format("ATP-Project-PartC/resources/%s.mp3", songName)).toURI().toString());
        runMusic();
    }
    public void runMusic(){
        int playTimes = 999;
        audio.setVolume(0.05f);
        audio.setCycleCount(playTimes);
        audio.play();
    }
}
