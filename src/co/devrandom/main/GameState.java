package co.devrandom.main;

import org.newdawn.slick.openal.Audio;

import co.devrandom.util.AudioLoader;

public class GameState {
	private static final GameState gameState = new GameState(State.PAUSED);
	
	public static final String NAME = "Iris";
	public static final String ASSET_PATH = "/assets/";
	public static final String FONT_PATH = ASSET_PATH + "fonts/";
	public static final String IMG_PATH = ASSET_PATH + "img/";
	public static final String AUDIO_PATH = ASSET_PATH + "audio/";
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final boolean ANTIALIAS = true;
	public static final int FPS = 60; 
	
	public static float SCALE = 100f;
	public static float TIME_STEP = 1f / 60f;
	public static int VELOCITY_ITERATIONS = 6;
	public static int POSITION_ITERATIONS = 2;
	
	public enum State {
		RUNNING,
		PAUSED,
		MAIN_MENU;
	}
	
	private static State currentState;
	
	private GameState(State initializeState) {
		currentState = initializeState;
	}
	
	public static GameState getInstance() {
		return gameState;
	}
	
	public static boolean isModelRunning() {
		return currentState == State.RUNNING;
	}
	
	public static void pauseUnpause() {
		if (currentState == State.PAUSED)
			currentState = State.RUNNING;
		else if (currentState == State.RUNNING)
			currentState = State.PAUSED;
	}
}
