package co.devrandom.main;

public class GameState {
	private static final GameState gameState = new GameState(State.MAIN_MENU);
	
	public static final String NAME = "Iris";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;	
	
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
