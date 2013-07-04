package co.devrandom.main;

public class GameState {
	private static final GameState gameState = new GameState(State.MAIN_MENU);
	
	public static final String NAME = "Iris";
	public static final String ASSET_PATH = "/assets/";
	public static final String FONT_PATH = ASSET_PATH + "fonts/";
	public static final String IMG_PATH = ASSET_PATH + "img/";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final boolean ANTIALIAS = true;
	
	private enum State {
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
}
