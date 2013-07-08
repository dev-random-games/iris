package co.devrandom.assets.audio;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.util.ResourceLoader;

import co.devrandom.main.GameState;

public class AudioLoader {

	public static Audio loadOGG(String fileName) {
		try {
			return org.newdawn.slick.openal.AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(GameState.AUDIO_PATH + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}
