package org.example.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer extends Thread {
    private Clip clip;
    private boolean running = true;

    public void run() {
        try {
            File audioFile = new File("background.wav"); // 프로젝트 실행 경로에 wav 파일 위치
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            while (running) {
                Thread.sleep(100);
            }

            clip.stop();
            clip.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        running = false;
    }
}
