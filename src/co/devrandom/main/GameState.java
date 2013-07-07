package co.devrandom.main;

import org.jbox2d.common.Vec2;


public class GameState {
	private static final GameState gameState = new GameState(State.MAIN_MENU);
	
	/*
	 * File path settings
	 */
	public static final String NAME = "Gravy";
	public static final String ASSET_PATH = "assets/";
	public static final String FONT_PATH = ASSET_PATH + "fonts/";
	public static final String IMG_PATH = ASSET_PATH + "img/";
	public static final String AUDIO_PATH = ASSET_PATH + "audio/";
	public static final String LEVEL_PATH = ASSET_PATH + "levels/";
	
	/*
	 * Game window settings
	 */
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 700;
	public static final boolean ANTIALIAS = true;
	public static final int FPS = 60; 
	
	/*
	 * Level loading settings
	 */
	public static final float LEVEL_SCALE = .1f;
	
	/*
	 * jBox2D settings
	 */
	public static float SCALE = 2000f;
	public static float TIME_STEP = 1f / 60f;
	public static int VELOCITY_ITERATIONS = 6;
	public static int POSITION_ITERATIONS = 2;
	public static final Vec2 DEFAULT_GRAVITY = new Vec2(0.0f, 0.0f);
	public static final float DEFAULT_LINEAR_DAMPING = 4f;
	public static final float DEFAULT_ANGULAR_DAMPING = 4f;

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
	
	public static boolean isMainMenu() {
		return currentState == State.MAIN_MENU;
	}
	
	public static boolean isModelRunning() {
		return currentState == State.RUNNING;
	}
	
	public static void startGame() {
		currentState = State.RUNNING;
	}
	
	public static void pauseUnpause() {
		if (currentState == State.PAUSED)
			currentState = State.RUNNING;
		else if (currentState == State.RUNNING)
			currentState = State.PAUSED;
	}
	
	public static boolean isPaused() {
		return currentState == State.PAUSED;
	}
}
