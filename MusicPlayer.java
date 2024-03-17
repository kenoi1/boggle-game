/**
 * Music Player
 * Input any filename and play music.
 */

package boggle;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MusicPlayer {
	String fileName = "";
	Clip clip;
	
	MusicPlayer (String fileName) {
		this.fileName = fileName;
		initiateFile(fileName);
	}
	
	public void initiateFile (String fileName) {
		try {
            File musicPath = new File(fileName);
             if(musicPath.exists()){ 
                     AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                     clip = AudioSystem.getClip();
                     clip.open(audioInput);
              }
		} 
		catch (Exception ex) {
              ex.printStackTrace();
        }
	}
	public void play () {
		clip.close();
		initiateFile(fileName);
		clip.start();
	}
	public void stop() {
        clip.close();
        clip.stop();
    }
}
