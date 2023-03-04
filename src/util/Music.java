package util;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*
source
How to add audio on JSwing in Java
https://www.codespeedy.com/how-to-add-audio-on-jswing-in-java/#:~:text=In%20the%20class%2C%20we%20will,the%20clip%20from%20the%20Audiosystem.
 */
public class Music {
    Clip clip;
    AudioInputStream sound;
    private static GameUtil gameUtil= new GameUtil();
    public void setFile(String soundFileName) {
        try {
            File file = new File(gameUtil.getMusic(soundFileName));
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            System.out.println(soundFileName);
        }
    }
    public void play() {
        clip.start();
    }
    public void stop() throws IOException {
        sound.close();
        clip.close();
        clip.stop();
    }
}
